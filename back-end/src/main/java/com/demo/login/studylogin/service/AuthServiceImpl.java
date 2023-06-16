package com.demo.login.studylogin.service;

import com.demo.login.studylogin.Utils.JwtTokenUtil;
import com.demo.login.studylogin.domain.members.RefreshToken;
import com.demo.login.studylogin.domain.members.User;
import com.demo.login.studylogin.dto.TokenDto;
import com.demo.login.studylogin.exception.AppException;
import com.demo.login.studylogin.exception.ErrorCode;
import com.demo.login.studylogin.repository.RefreshTokenRepository;
import com.demo.login.studylogin.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    //토큰 재발급 로직

    @Value("${jwt.secret}")
    private String secretKey;

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Override
    public User getAuth(HttpServletRequest request) {
        String token = jwtTokenUtil.resolveToken(request);

        if(StringUtils.hasText(token)) {
            Authentication authentication = jwtTokenUtil.getAuthentication(token);
            return (User) authentication.getPrincipal();
        }else  {
            return null;
        }
    }

    @Override
    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {
        try {
//            //액세스 토큰 받아와서 정보 추출
//            String token = jwtTokenUtil.resolveToken(request);
//            HashMap<String, String> payloadMap = JwtUtil.getPayloadByToken(token);
//            String userNo = payloadMap.get("userNo");
//
//            //DB에서 리프레시 토큰이 존재하는지 확인
//            Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(Long.valueOf(userNo));
//            refreshToken.orElseThrow(
//                    () -> new AppException(ErrorCode.UNAUTHORIZED,  "리프레시 토큰이 존재하지 않습니다.")
//            );

            // 엑세스 토큰안에 담긴 정보를 이용해 DB상에 실제 사용자가 존재하는지 확인
            String accessToken = jwtTokenUtil.resolveToken(request);
            System.out.println("reissue 서버 시작");

            User user = userRepository.findByUserEmail(jwtTokenUtil.getClaimsUserEmail(accessToken)).get();
            if (null == user) {
                throw new AppException(ErrorCode.USEREMAIL_NOT_FOUND, "사용자를 찾을 수 없습니다.");
//                return ResponseEntity.ok("USER_NOT_FOUND");

            }

            // 리프레시 토큰 유효성 검증
            RefreshToken refreshToken = jwtTokenUtil.isPresentRefreshToken(user);
            if (!refreshToken.getRefresh_token().equals(request.getHeader("RefreshToken"))) {
                throw new AppException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED, "토큰이 유효하지 않습니다.");
            }

            // 위 과정을 다 통과하면 새로운 토큰을 만들어 response에 담아 보냄
            TokenDto tokenDto = jwtTokenUtil.generateTokenDto(user);
            refreshToken.updateToken(tokenDto.getRefreshToken());
            // 리프레시 토큰도 새로 발급해 DB에 저장
            jwtTokenUtil.tokenToHeaders(tokenDto, response);
            System.out.println("재발급 완료");
            return ResponseEntity.ok().body("토큰 재발급이 완료되었습니다.");


        } catch(ExpiredJwtException e) {
            throw new AppException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED, "");
        }
    }

//    @Override
//    public UserDto reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
//            String token = jwtTokenUtil.resolveToken(request);
//            HashMap<String, String> payloadMap = JwtUtil.getPayloadByToken(token);
//            String userNo = payloadMap.get("sub");
//
//            Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(Long.valueOf(userNo));
//            refreshToken.orElseThrow(
//                    () -> new AppException(ErrorCode.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다.")
//            );
//
//            boolean isTokenValid = jwtTokenUtil.validateToken(refreshToken.get().getRefresh_token(), request);
//
//            if(isTokenValid) {
//                Optional<User> user = userRepository.findById(Long.valueOf(userNo));
//
//                if(user.isPresent()) {
//                    //create new Access Token and Refresh Token using the createAccessToken() and RefreshToken() in JwtTokenUtil
//
//                    //save the new Refresh Token to the RefreshTokenRepository
//
//                    //return a build to UserDto with the new accessToken
//                    Method createAccessTokenMethod = jwtTokenUtil.getClass().getDeclaredMethod(
//                            "createAccessToken", Long.class, String.class, String.class, String.class, long.class
//                    );
//                    createAccessTokenMethod.setAccessible(true);
//                    String newAccessToken = (String) createAccessTokenMethod.invoke(
//                            jwtTokenUtil, user.get().getUserNo(), user.get().getUserEmail(),
//                            user.get().getUserNick(), secretKey, accessTokenExpireTimeMs
//                    );
//
//                    Method createRefreshTokenMethod = jwtTokenUtil.getClass().getDeclaredMethod(
//                            "createRefreshToken", Long.class, String.class, String.class, String.class, long.class
//                    );
//                    createRefreshTokenMethod.setAccessible(true);
//                    String newRefreshToken = (String) createRefreshTokenMethod.invoke(
//                            jwtTokenUtil, user.get().getUserNo(), user.get().getUserEmail(),
//                            user.get().getUserNick(), secretKey, refreshTokenExpireTimeMs
//                    );
//
//                    // Save the new Refresh Token to the RefreshTokenRepository
//                    refreshToken.get().setRefreshToken(newRefreshToken);
//                    refreshTokenRepository.save(refreshToken.get());
//
//                    return UserDto.builder()
//                            .accessToken(newAccessToken)
//                            .user(user.get())
//                            .build();
//                }
//            }
//
//        }catch (ExpiredJwtException e) {
//            throw new AppException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED, "리프레시 토큰이 만료되었습니다.");
//        } catch (InvocationTargetException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }
}
