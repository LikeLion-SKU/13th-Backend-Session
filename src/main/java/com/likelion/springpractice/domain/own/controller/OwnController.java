package com.likelion.springpractice.domain.own.controller;


import com.likelion.springpractice.domain.auth.exception.AuthErrorCode;
import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.like.service.LikeService;
import com.likelion.springpractice.domain.own.dto.response.OwnResponse;
import com.likelion.springpractice.domain.own.service.OwnService;
import com.likelion.springpractice.global.exception.CustomException;
import com.likelion.springpractice.global.jwt.JwtProvider;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owns")
@Tag(name="Own", description="Own 관리 API")
public class OwnController {
  private final OwnService ownService;
  private final JwtProvider jwtProvider;

  // 내가 획득한 배지 목록 조회
  @Operation(summary = "획득한 배지 리스트 조회(획득한 것만)",
      description = "내가 획득한 배지 리스트 조회하는 API.")
  @GetMapping("")
  public ResponseEntity<BaseResponse<List<BadgeResponse>>> getOwnedBadges(HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN); // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractSocialId(token);  // 토큰에서 로그인한 아이디 추출


    List<BadgeResponse> response = ownService.getBadgeOwnList(userName);
    return ResponseEntity.ok(BaseResponse.success("획득한 배지 리스트 조회 성공", response));
  }


  // 내 배지 현황 목록 조회
  @Operation(summary = "내 배지 획득 기록 리스트 조회(확인용)",
      description = "내가 획득했던 배지 기록 리스트 조회하는 API. (true: 소장중, false: 비소장)")
  @GetMapping("/history")
  public ResponseEntity<BaseResponse<List<OwnResponse>>> getBadgeHistory(HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN); // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractSocialId(token);  // 토큰에서 로그인한 아이디 추출


    List<OwnResponse> response = ownService.getBadgeStateList(userName);
    return ResponseEntity.ok(BaseResponse.success("내 배지 현황 리스트 조회 성공", response));
  }


}
