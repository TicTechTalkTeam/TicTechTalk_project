package com.demo.login.studylogin.controller;


import com.demo.login.studylogin.Utils.JwtTokenUtil;
import com.demo.login.studylogin.domain.members.User;
import com.demo.login.studylogin.dto.MyPageRequestDto;
import com.demo.login.studylogin.service.MyPageService;
import com.demo.login.studylogin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final JwtTokenUtil jwtTokenUtil;
    private final MyPageService myPageService;


    @GetMapping("/info")
    public User getMyPage(HttpServletRequest request, Model model) {
        //토큰에서 userNo를 추출
        User user = myPageService.fetchUserInformation(request);
        model.addAttribute("user", user);
        return user;
    }

    //유저 정보 업데이트
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody MyPageRequestDto myPageRequestDto) {
        return myPageService.editMyPage(myPageRequestDto);
    }

    //내 글 보기
    @GetMapping("/mypost")
    public ResponseEntity<?> myPost() {
        return myPageService.myPost();
    }

    @PostMapping("/bookmark")
    public ResponseEntity<?> addBookmark(@RequestBody Map<String, Long> postNo) {
        return myPageService.addBookmark(postNo);
    }


    //북마크 리스트
    @GetMapping("/bookmark")
    public ResponseEntity<?> bookmark() {
        return myPageService.bookmark();
    }


}
