package com.likelion.springpractice.domain.mypage.controller;

import com.likelion.springpractice.domain.mypage.dto.response.MyPageResponse;
import com.likelion.springpractice.domain.mypage.service.MyPageService;
import com.likelion.springpractice.global.common.ApiResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@SecurityRequirement(name = "bearerAuth")
public class MyPageController {

  private final MyPageService myPageService;

  @Operation(summary = "마이페이지 통합 조회")
  @GetMapping("/me")
  public ApiResponse<MyPageResponse> getMyPage(
      @AuthenticationPrincipal CustomUserDetails user) {

    MyPageResponse res = myPageService.getMyPage(user.getUserId());
    return ApiResponse.ok("마이페이지 조회 성공", res);
  }
}