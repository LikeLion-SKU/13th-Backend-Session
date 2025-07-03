package com.likelion.springpractice.domain.like.controller;

import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.service.LikeService;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "like", description = "좋아요 관련 API")
public class LikeController {

  private final LikeService likeService;

  // 사용자별 좋아요 조회 API -> 매개변수에 LikeID 필요?
  @Operation(summary = "사용자별 좋아요 조회", description = "특정 사용자가 누른 좋아요 리스트 조회")
  @GetMapping("/likes")
  public ResponseEntity<BaseResponse<List<LikeResponse>>> getLikesByUser(
      @AuthenticationPrincipal CustomUserDetails userDetails) { // 인증 객체 가져오는 애너테이션
    User user = userDetails.getUser();
    List<LikeResponse> likeList = likeService.getLikesByUser(user);
    return ResponseEntity.ok(BaseResponse.success("사용자별 좋아요 조회 완료", likeList));
  }

  // 좋아요 생성 API -> userDetails 로 수정
  @Operation(summary = "좋아요 생성", description = "특정 음식에 대해 좋아요 생성")
  @PostMapping("/likes/{foodId}")
  public ResponseEntity<BaseResponse<LikeResponse>> createLike(
      @Parameter(description = "좋아요를 누를 음식 ID") @PathVariable Long foodId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    User user = userDetails.getUser();
    LikeResponse response = likeService.createLike(user, foodId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 등록 완료", response));
  }

  // 좋아요 삭제 API
  @Operation(summary = "좋아요 삭제", description = "특정 음식에 대한 좋아요 취소")
  @DeleteMapping("/likes/{foodId}")
  public ResponseEntity<BaseResponse<Boolean>> deleteLike(
      @Parameter(description = "좋아요 취소 음식 ID") @PathVariable Long foodId,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    User user = userDetails.getUser();
    likeService.deleteLike(user, foodId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 취소 완료", true));
  }

}
