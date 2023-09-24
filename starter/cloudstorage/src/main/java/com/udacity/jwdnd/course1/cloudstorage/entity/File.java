package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class File {

    private Long fileId;

    private String fileName;

    private String contentType;

    private Long fileSize;

    private Long userId;

    private byte[] fileData;

}
