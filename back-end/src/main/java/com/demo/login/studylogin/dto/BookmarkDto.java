package com.demo.login.studylogin.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {

    private Long bookmarkId;
    private Long userNo;
    private Long postNo;
    private String title;
}
