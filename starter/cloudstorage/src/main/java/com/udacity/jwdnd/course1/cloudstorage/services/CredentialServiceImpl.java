package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class CredentialServiceImpl implements CredentialService{

    private final CredentialMapper credentialMapper;

    private final EncryptionService encryptionService;

    private final CommonService commonService;

    @Override
    public void createOrUpdateCredential(Credentials credentialInfo) throws InternalException {

        Credentials credential = credentialMapper.getCredentialByUsernameAndUrl(credentialInfo.getUserName(), credentialInfo.getUrl());

        // 1. Validate the input data
        if (credentialInfo.getUserName() == null || credentialInfo.getPassword() == null || credentialInfo.getUrl() == null) {
            throw new InternalException("Username, password, url can not be null");
        }

        if (credential != null) {
            throw new InternalException(credentialInfo.getUserName() +" corresponding to" + credentialInfo.getUrl() + "already exists.");
        }

        // Encode password
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String passwordEncode = encryptionService.encryptValue(credentialInfo.getPassword(),Base64.getEncoder().encodeToString(key).trim());

        // Set value for Credential
        Credentials credentialSaveOrUpdate = Credentials.builder()
                .credentialId(credentialInfo.getCredentialId())
                .userName(credentialInfo.getUserName())
                .password(passwordEncode)
                .url(credentialInfo.getUrl())
                .key(Base64.getEncoder().encodeToString(key).trim())
                .userId(commonService.getUserByUsername().getUserId())
                .build();

        if (credentialInfo.getCredentialId() == null) {
            // 2. Save the credential
            credentialMapper.addCredential(credentialSaveOrUpdate);
        } else {
            // 3. Update the credential
            credentialMapper.updateCredential(credentialSaveOrUpdate);
        }
    }

    @Override
    public List<Credentials> getAllCredentials() throws InternalException {
        return credentialMapper.getAllCredentials(commonService.getUserByUsername().getUserId());
    }

    @Override
    public void deleteCredential(Long credentialId) throws InternalException {
        if (credentialMapper.getCredential(credentialId, commonService.getUserByUsername().getUserId()) != null) {
            credentialMapper.deleteCredential(credentialId, commonService.getUserByUsername().getUserId());
        } else {
            throw new InternalException("The" + credentialId + ", " + commonService.getUserByUsername().getUserId() + "not exists.");
        }
    }
}
