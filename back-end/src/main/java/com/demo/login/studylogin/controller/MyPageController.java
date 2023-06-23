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
    private final UserService  userService;
    private final MyPageService myPageService;

    //사용자 정보 리턴해주는 로직입니다. 참고해서 새로 만드세요!
    @GetMapping("/info")
    public User getMyPage(HttpServletRequest request, Model model) {
        //토큰에서 userNo를 추출
        Long userNo = jwtTokenUtil.getUserNoFromToken(request);
        System.out.println(userNo);
        if (userNo == null) {
            throw new RuntimeException("해당하는 사용자가 없습니다(토큰에서 userNo 추출 실패).");
        }

        //userNo를 이용해서 사용자 정보를 가져옴
        User user = userService.fetchUserInformation(userNo);

        model.addAttribute("user", user);

        //user 정보 반환
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
