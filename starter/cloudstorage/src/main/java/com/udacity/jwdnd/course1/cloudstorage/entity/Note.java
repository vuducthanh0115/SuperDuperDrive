package com.udacity.jwdnd.course1.cloudstorage.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Note {

    private Long noteId;

    private String noteTitle;

    private String noteDescription;

    private Long userId;
}
