package com.udacity.jwdnd.course1.cloudstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long userId;

    private String userName;

    private String salt;

    private String password;

    private String firstName;

    private String lastName;
}
