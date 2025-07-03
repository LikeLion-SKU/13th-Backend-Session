package com.likelion.springpractice.domain.badge.controller;


import com.likelion.springpractice.domain.auth.exception.AuthErrorCode;
import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.dto.response.OwnedBadgeResponse;
import com.likelion.springpractice.domain.badge.service.BadgeService;
import com.likelion.springpractice.global.exception.CustomException;
import com.likelion.springpractice.global.jwt.JwtProvider;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/badges")
@Tag(name="Badge", description="Badge 관리 API")
public class BadgeController {

  private final BadgeService badgeService;
  private final JwtProvider jwtProvider;


  @Operation(summary = "배지 고유 id로 배지 단일 조회",
      description = "배지 고유 id을 주면 해당 배지 정보를 조회하는 API.")
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<BadgeResponse>> getBadgeById(
      @Parameter(description = "특정 배지 ID", example = "1")
      @PathVariable Long id) {
    BadgeResponse response = badgeService.getBadgeById(id);
    return ResponseEntity.ok(BaseResponse.success("배지 단일 조회 성공", response));
  }

  @Operation(summary = "배지 전체 조회",
      description = "존재하는 전체 배지 정보를 조회하는 API.")
  @GetMapping("")
  public ResponseEntity<BaseResponse<List<BadgeResponse>>> getAllBadges() {
    List<BadgeResponse> response = badgeService.getAllBadges();
    return ResponseEntity.ok(BaseResponse.success("전체 배지 조회 성공", response));
  }

  @Operation(summary = "배지 전체 조회(획득 여부 포함)",
      description = "존재하는 전체 배지 정보를 조회하는 API.")
  @GetMapping("/states")
  public ResponseEntity<BaseResponse<List<OwnedBadgeResponse>>> getBadgeStates(HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN); // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractSocialId(token);  // 토큰에서 로그인한 아이디 추출

    List<OwnedBadgeResponse> response = badgeService.getAllOwnedBadges(userName);
    return ResponseEntity.ok(BaseResponse.success("전체 배지 조회 성공", response));
  }

}
