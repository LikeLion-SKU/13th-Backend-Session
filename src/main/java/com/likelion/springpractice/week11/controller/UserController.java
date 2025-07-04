package com.likelion.springpractice.week11.controller;

import com.likelion.springpractice.week11.dto.request.UserRegisterRequestDto;
import com.likelion.springpractice.week11.dto.request.UserLoginRequestDto;
import com.likelion.springpractice.week11.dto.response.UserLoginResponseDto;
import com.likelion.springpractice.week11.dto.response.UserResponseDto;
import com.likelion.springpractice.week11.security.CustomUserDetails;
import com.likelion.springpractice.week11.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User 관리 API")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterRequestDto request) {
        userService.registerUser(request);
        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto request) {
        return ResponseEntity.ok(userService.login(request));
    }

    // 마이페이지 - 내 정보 조회 (JWT 필요)
    @Operation(summary = "내 정보 조회", security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userService.getUserProfile(userDetails.getId()));
    }
}