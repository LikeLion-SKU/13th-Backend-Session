package com.likelion.springpractice.domain.BadgeHistory.controller;

import com.likelion.springpractice.domain.BadgeHistory.dto.response.BadgeHistoryResponse;
import com.likelion.springpractice.domain.BadgeHistory.service.BadgeHistoryService;
import com.likelion.springpractice.global.Response.BaseResponse;
import com.likelion.springpractice.global.Security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/badge-histories")
@Tag(name = "BadgeHistory", description = "사용자 먹방배지 관련 API")
public class BadgeHistoryController {

  public final BadgeHistoryService badgeHistoryService;

  @Operation(summary = "먹방배지 목록 조회 API", description = "자신의 먹방배지 목록을 조회하기 위한 API")
  @GetMapping("/")
  public ResponseEntity<BaseResponse<List<BadgeHistoryResponse>>> getAllBadges(
      @Parameter(hidden = true)
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    List<BadgeHistoryResponse> responses = badgeHistoryService.getAllBadges(
        userDetails.getUser().getId());

    return ResponseEntity.ok(BaseResponse.success("먹방배지 목록 조회에 성공했습니다.", responses));
  }
}
