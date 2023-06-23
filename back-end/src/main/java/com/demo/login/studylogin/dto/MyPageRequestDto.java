package com.demo.login.studylogin.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageRequestDto {
    private String userNick;
    private String userInfo;
    private String userPicStoredFileName;
}
