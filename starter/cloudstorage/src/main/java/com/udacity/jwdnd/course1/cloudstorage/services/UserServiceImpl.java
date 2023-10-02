package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.util.RegexValidationUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final HashService hashService;

    private final UserMapper userMapper;

    @Override
    public int createUser(User userInfo) throws InternalException {
        // 1. Validate the input
        if (userInfo == null) {
            throw new InternalException("Invalid user info");
        }

        if (userInfo.getPassword() == null || userInfo.getPassword().isEmpty()) {
            throw new InternalException("Invalid password");
        }

        if (userInfo.getUserName() == null || userInfo.getUserName().isEmpty()) {
            throw new InternalException("Invalid username");
        }

        if (!RegexValidationUtil.isValidPassword(userInfo.getPassword())) {
            throw new InternalException("Password must be minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character in #?!@$%^&*");
        }

        if (ObjectUtils.isEmpty(userInfo.getUserName().trim())) {
            throw new InternalException("Username is required");
        }

        // Check if the username is already exists in system
        Optional<User> isUsernameAvailable = userMapper.getUserByUsername(userInfo.getUserName());
        if (isUsernameAvailable.isEmpty()) {
            // 2. Save the user
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            User user = User.builder()
                    .userName(userInfo.getUserName().trim())
                    .firstName(userInfo.getFirstName().trim())
                    .lastName(userInfo.getLastName().trim())
                    .salt(Base64.getEncoder().encodeToString(salt).trim())
                    .password(hashService.getHashedValue(userInfo.getPassword().trim(), Base64.getEncoder().encodeToString(salt).trim()))
                    .build();
            return userMapper.createUser(user);
        } else {
            throw new InternalException(String.format("The username '%s' already exists.", userInfo.getUserName()));
        }
    }
}