package com.likelion.springpractice.domain.auth.service;

import com.likelion.springpractice.domain.auth.dto.request.LoginRequest;
import com.likelion.springpractice.domain.auth.dto.response.LoginResponse;
import com.likelion.springpractice.domain.auth.exception.AuthErrorCode;
import com.likelion.springpractice.domain.auth.mapper.AuthMapper;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import com.likelion.springpractice.global.jwt.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final AuthMapper authMapper;
  private final JwtProvider jwtProvider;

  @Transactional
  public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {

    User user = userRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
            loginRequest.getPassword());

    // 인증 처리
    authenticationManager.authenticate(authenticationToken);

    // 액세스 토큰 및 리프레시 토큰 발급
    String accessToken = jwtProvider.createAccessToken(user.getEmail());
    String refreshToken = jwtProvider.createRefreshToken(user.getEmail(),
        UUID.randomUUID().toString());

    // 리프레시 토큰 저장
    user.createRefreshToken(refreshToken);

    // Access Token의 만료 시간을 가져옴
    Long expirationTime = jwtProvider.getExpiration(accessToken);

    // 로그인 성공 로깅
    log.info("로그인 성공: {}", user.getEmail());

    Cookie cookie = new Cookie("refreshToken", refreshToken);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60 * 24 * 7);

    response.addCookie(cookie);

    return authMapper.toLoginResponse(user, accessToken, expirationTime);
  }

  @Transactional
  public void logout(String refreshToken, HttpServletResponse response) {

    if (refreshToken != null && jwtProvider.validateToken(refreshToken)) {
      User user = userRepository.findByRefreshToken(refreshToken)
          .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

      user.removeRefreshToken();
      userRepository.save(user);
    }

    // 쿠키에서도 삭제
    Cookie cookie = new Cookie("refreshToken", null);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(0);

    response.addCookie(cookie);
  }

  @Transactional
  public String makeAccessToken(String refreshToken) {

    // 리프레시 토큰 유효성 검사
    if (refreshToken == null || !jwtProvider.validateToken(refreshToken)) {
      throw new CustomException(AuthErrorCode.REFRESH_TOKEN_REQUIRED);
    }

    // DB에서 리프레시 토큰을 가진 사용자 조회
    User user = userRepository.findByRefreshToken(refreshToken)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 액세스 토큰 발급
    return jwtProvider.createAccessToken(user.getEmail());
  }
}
