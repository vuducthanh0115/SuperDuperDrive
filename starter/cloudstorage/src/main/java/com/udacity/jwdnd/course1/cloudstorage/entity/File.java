package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class File {

    private Long fileId;

    private String fileName;

    private String contentType;

    private Long fileSize;

    private Long userId;

    private byte[] fileData;

}
