package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;

import java.util.List;

public interface CredentialService {
    void createOrUpdateCredential(Credentials credential) throws InternalException;

    List<Credentials> getAllCredentials() throws InternalException;

    void deleteCredential(Long credentialId) throws InternalException;
}
