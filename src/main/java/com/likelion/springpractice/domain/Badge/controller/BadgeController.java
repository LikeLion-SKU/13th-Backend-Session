package com.likelion.springpractice.domain.Badge.controller;

import com.likelion.springpractice.domain.Badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.Badge.service.BadgeService;
import com.likelion.springpractice.global.Response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bedges")
@Tag(name = "Bedge", description = "Bedge 관리 API")
public class BadgeController {

  private final BadgeService badgeService;

  @Operation(summary = "배지 전체 조회 API", description = "존재하는 배지 전체 조회를 위한 API")
  @GetMapping("/")
  public ResponseEntity<BaseResponse<List<BadgeResponse>>> getAllBadges() {

    List<BadgeResponse> responses = badgeService.getAllBadges();

    return ResponseEntity.ok(BaseResponse.success("배지 전체 조회에 성공했습니다.", responses));
  }
}
