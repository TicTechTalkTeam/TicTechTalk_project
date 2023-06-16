package com.demo.login.studylogin.configuration;

import com.demo.login.studylogin.Utils.JwtTokenUtil;
import com.demo.login.studylogin.common.ResponseVO;
import com.demo.login.studylogin.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    //리퀘스트 필터로 모든 리퀘스트는 이 필터를 지나감. 여기서 토큰이 검증되지 않으면 에러를 반환.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getServletPath();

            if (path.startsWith("/users/reissue")) {
                filterChain.doFilter(request, response);
            } else {
                String accessToken = jwtTokenUtil.resolveToken(request);
                boolean isTokenValid = jwtTokenUtil.validateToken(accessToken, request);

                if (StringUtils.hasText(accessToken) && isTokenValid) {
                    System.out.println(accessToken);
                    this.setAuthentication(accessToken);
                }

                filterChain.doFilter(request, response);
            }

        } catch (ExpiredJwtException e) {
            ResponseVO responseVO = ResponseVO.builder()
                    .status(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getHttpStatus())
                    .message(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getMessage())
                    .code(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getCode())
                    .build();

            response.setStatus(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED.getHttpStatus().value());
            response.getWriter().write(new ObjectMapper().writeValueAsString(responseVO));
            response.getWriter().flush();
        }

    }
    // SecurityContext에 Authentication 저장
    private void setAuthentication(String token) {
        Authentication authentication = jwtTokenUtil.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
//        log.info("authorization:{}", authorization);
//
//        //token 안 보내면 block
//        //authorization이 Bearer로 시작하고 한 칸 띄고 토큰이 와야만 허용이 된다.
//        if(authorization == null || !authorization.startsWith("Bearer ")) {
//            log.error("authorization을 잘못 보냈습니다.");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //token 꺼내기
//        //token을 빈 칸으로 나누고 첫번째가 token이어야 한다.
//        String token = authorization.split(" ")[1];
//
//        //token이 만료되었는지 여부 확인
//        if(JwtTokenUtil.isExpired(token, secretKey)) {
//            log.error("토큰이 만료되었습니다.");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//
//        //UserEmail Token에서 꺼내기
//        String userEmail = JwtTokenUtil.getUserEmail(token, secretKey);
//        log.info("userEmail:{}", userEmail);
//
//        //권한 부여
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(userEmail, null, List.of(new SimpleGrantedAuthority("USER")));
//        //Detail 넣기
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        filterChain.doFilter(request, response);
//    }



