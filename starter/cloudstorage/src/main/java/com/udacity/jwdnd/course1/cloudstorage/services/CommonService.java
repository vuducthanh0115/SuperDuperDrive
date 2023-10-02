package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommonService {

    private final UserMapper userMapper;

    public User getUserByUsername() throws InternalException {

        // Get information of the currently logged-in user in context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getName() == null || authentication.getName().isEmpty()) {
            throw new InternalException("The user does not exist.");
        }

        Optional<User> user = userMapper.getUserByUsername(authentication.getName());

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new InternalException("The user does not exist.");
        }
    }
}
