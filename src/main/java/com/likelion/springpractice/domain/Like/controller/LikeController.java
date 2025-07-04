package com.likelion.springpractice.domain.Like.controller;

import com.likelion.springpractice.domain.Like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.Like.service.LikeService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@Tag(name = "Like", description = "좋아요 관련 API")
public class LikeController {

  private final LikeService likeService;

  @Operation(summary = "좋아요 수정 API", description = "좋아요 수정(재등록)을 위한 API")
  @PutMapping("/{likeId}/update")
  public ResponseEntity<BaseResponse<LikeResponse>> updateLike(
      @Parameter(hidden = true)
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @Parameter(description = "특정 좋아요 ID")
      @PathVariable Long likeId) {

    LikeResponse response = likeService.updateLike(likeId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 재등록에 성공했습니다.", response));
  }

  @Operation(summary = "내 좋아요 목록 조회 API", description = "내가 누른 좋아요들을 조회하는 API")
  @GetMapping("/my")
  public ResponseEntity<BaseResponse<List<LikeResponse>>> getMyLikes(
      @Parameter(hidden = true)
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    List<LikeResponse> responses = likeService.getMyLikes(userDetails.getUser().getId());
    return ResponseEntity.ok(BaseResponse.success("내 좋아요 목록 조회에 성공했습니다.", responses));
  }
}
