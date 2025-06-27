package com.likelion.springpractice.domain.badge.controller;

import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.service.BadgeService;
import com.likelion.springpractice.global.common.ApiResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/badges")
@SecurityRequirement(name = "bearerAuth")
public class BadgeController {

  private final BadgeService badgeService;

  // 내 배지 목록 조회
  @GetMapping("/my")
  public ApiResponse<List<BadgeResponse>> getMyBadges(
      @AuthenticationPrincipal @NotNull CustomUserDetails user) {

    List<BadgeResponse> list = badgeService.getMyBadges(user.getUserId());
    return ApiResponse.ok("내 배지 목록 조회 성공", list);
  }
}