package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private Long userId;

    private String userName;

    private String salt;

    private String password;

    private String firstName;

    private String lastName;
}
