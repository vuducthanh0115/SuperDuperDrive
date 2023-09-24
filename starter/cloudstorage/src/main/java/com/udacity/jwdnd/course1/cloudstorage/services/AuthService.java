package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.exception.AuthException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthService implements AuthenticationProvider {

    private final UserMapper userMapper;

    private final HashService hashService;

    public AuthService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    /**
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> user = userMapper.getUserByUsername(username);
        if (user.isPresent()) {
            String hashedPassword = hashService.getHashedValue(password, user.get().getSalt());
            if (user.get().getPassword().equals(hashedPassword)) {
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, password, new ArrayList<>());
                return new UsernamePasswordAuthenticationToken(userDetails, password, new ArrayList<>());
            }
        }
        throw new AuthException("Login failed");
    }

    /**
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
