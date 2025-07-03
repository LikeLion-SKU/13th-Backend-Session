package com.likelion.springpractice.domain.badge.controller;

import com.likelion.springpractice.domain.badge.dto.response.BadgeUserResponse;
import com.likelion.springpractice.domain.badge.service.BadgeService;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "badge", description = "배찌 관련 API")
public class BadgeController {

  private final BadgeService badgeService;

  // 사용자별 배찌 조회
  @Operation(summary = "사용자별 배찌 목록 조회", description = "사용자가 보유한 배찌 조회 API")
  @GetMapping("/user")
  public ResponseEntity<BaseResponse<List<BadgeUserResponse>>> getUserBadges(
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    User user = userDetails.getUser();
    List<BadgeUserResponse> badgeUserList = badgeService.getBadgesByUser(user);
    return ResponseEntity.ok(BaseResponse.success("사용자별 베찌 목록 조회 성공", badgeUserList));
  }
}
