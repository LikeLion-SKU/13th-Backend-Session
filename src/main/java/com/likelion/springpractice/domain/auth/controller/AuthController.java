package com.likelion.springpractice.domain.auth.controller;

import com.likelion.springpractice.domain.auth.dto.request.LoginRequest;
import com.likelion.springpractice.domain.auth.dto.response.LoginResponse;
import com.likelion.springpractice.domain.auth.service.AuthService;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.Response.BaseResponse;
import com.likelion.springpractice.global.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auths")
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final UserRepository userRepository;

  @Operation(summary = "사용자 로그인", description = "시용자 로그인을 위한 API")
  @PostMapping("/login")
  public ResponseEntity<BaseResponse<LoginResponse>> login
      (@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
    LoginResponse loginResponse = authService.login(loginRequest);

    // refreshToken 가져오기
    String refreshToken = userRepository.findByUsername(loginRequest.getUsername())
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND))
        .getRefreshToken();

    // Set-Cookie 실행 (HttpOnly + Secure)
    Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
    refreshTokenCookie.setHttpOnly(true);

    // refreshTokenCookie.setSecure(true): HTTPS일 때만
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7일

    response.addCookie(refreshTokenCookie);

    return ResponseEntity.ok(BaseResponse.success("로그인에 성공했습니다.", loginResponse));
  }

  // 인증 인가와 관련된 endpoint 필요: 로그아웃, refresh로 acccess 발급 등
}
