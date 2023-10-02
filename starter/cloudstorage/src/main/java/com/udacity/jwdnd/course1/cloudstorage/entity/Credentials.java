package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Credentials {

    private Long credentialId;

    private String url;

    private String userName;

    private String key;

    private String password;

    private Long userId;

}
