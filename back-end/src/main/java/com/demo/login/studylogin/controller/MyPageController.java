package com.demo.login.studylogin.controller;

import com.demo.login.studylogin.Utils.JwtTokenUtil;
import com.demo.login.studylogin.domain.members.User;
import com.demo.login.studylogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService  userService;

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

}
