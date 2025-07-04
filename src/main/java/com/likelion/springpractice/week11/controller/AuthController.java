package com.likelion.springpractice.week11.controller;

import com.likelion.springpractice.domain.auth.service.AuthService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.week11.dto.request.UserLoginRequestDto;
import com.likelion.springpractice.week11.dto.response.UserLoginResponseDto;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.global.exception.CustomException;
import com.likelion.springpractice.week11.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auths")
@Tag(name = "auth", description = "Auth 관리 API")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @Operation(summary = "사용자 로그인", description = "사용자 로그인을 위한 API")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<UserLoginResponseDto>> login(
            @RequestBody @Valid UserLoginRequestDto loginRequest,
            HttpServletResponse response
    ) {
        UserLoginResponseDto loginResponse = authService.login(loginRequest);

        // refreshToken 가져오기
        String refreshToken = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND))
                .getRefreshToken();

        // Set-Cookie 설정 (HttpOnly + Secure)
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS 사용 시 true
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7일

        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(BaseResponse.success("로그인에 성공했습니다.", loginResponse));
    }
}