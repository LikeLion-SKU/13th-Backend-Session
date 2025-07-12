package com.likelion.springpractice.domain.auth.controller;

import com.likelion.springpractice.domain.auth.dto.request.LoginRequest;
import com.likelion.springpractice.domain.auth.dto.response.LoginResponse;
import com.likelion.springpractice.domain.auth.service.AuthService;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auths")
@Tag(name = "Auth", description = "Auth 관리 API")
public class AuthController {

  private final AuthService authService;
  private final UserRepository userRepository;

  @Operation(summary = "사용자 로그인", description = "사용자 로그인을 위한 API")
  @PostMapping("/login")  // api/auths/login
  public ResponseEntity<BaseResponse<LoginResponse>> login(
      @RequestBody @Valid LoginRequest loginRequest,
      HttpServletResponse response) {  // @RequestBody : 요청 JSON을 LoginRequest 객체와 자동 매핑
    LoginResponse loginResponse = authService.login(loginRequest);  // 로그인 요청 처리

    // 사용자 Email로 DB에서 다시 사용자 조회 후 User 엔티티에서 refreshToken 가져오기
    String refreshToken = userRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND))
        .getRefreshToken();

    // Set-Cookie 설정 (HttpOnly + Secure) 
    // 가져온 refreshToken 쿠키에 저장
    Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
    refreshTokenCookie.setHttpOnly(true);
    //refreshTokenCookie.setSecure(true);  // HTTPS일 때만
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7);  // 예: 7일 refresh Token 만료기간

    response.addCookie(refreshTokenCookie);

    // 요청에 대한 응답 반환
    return ResponseEntity.ok(BaseResponse.success("로그인에 성공했습니다.", loginResponse));
  }
}