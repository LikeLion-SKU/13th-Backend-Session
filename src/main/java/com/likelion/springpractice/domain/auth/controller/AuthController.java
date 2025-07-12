package com.likelion.springpractice.domain.auth.controller;

import com.likelion.springpractice.domain.auth.dto.request.LoginRequest;
import com.likelion.springpractice.domain.auth.dto.response.LoginResponse;
import com.likelion.springpractice.domain.auth.service.AuthService;
import com.likelion.springpractice.global.Response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "사용자 인증/인가 관련 API")
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "사용자 로그인", description = "시용자 로그인을 위한 API")
  @PostMapping("/login")
  public ResponseEntity<BaseResponse<LoginResponse>> login
      (@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {

    // Repository 접근 로직은 Service로 이동
    LoginResponse loginResponse = authService.login(loginRequest, response);

    return ResponseEntity.ok(BaseResponse.success("로그인에 성공했습니다.", loginResponse));
  }

  @Operation(summary = "로그아웃", description = "리프레시 토큰을 사용한 사용자 로그아웃 API")
  @PostMapping("/logout")
  public ResponseEntity<BaseResponse<Void>> logout(
      // 요청에 포함된 쿠키 중 이름이 refreshToken인 값을 가져와서 저장 (쿠키에서 특정 이름의 값을 꺼내기!)
      @CookieValue(value = "refreshToken", required = false) String refreshToken,
      HttpServletResponse response) {

    authService.logout(refreshToken, response);

    return ResponseEntity.ok(BaseResponse.success("로그아웃에 성공했습니다.", null));
  }

  @Operation(summary = "액세스 토큰 재발급", description = "리프레시 토큰을 사용한 액세스 토큰 재발급 API")
  @PostMapping("/refresh")
  public ResponseEntity<BaseResponse<String>> refreshToken(
      @CookieValue(value = "refreshToken", required = false) String refreshToken,
      HttpServletResponse response) {

    String newAccessToken = authService.makeAccessToken(refreshToken);

    return ResponseEntity.ok(BaseResponse.success("엑세스 토큰 재발급에 성공했습니다.", newAccessToken));
  }
}
