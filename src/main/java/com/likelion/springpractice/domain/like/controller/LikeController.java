package com.likelion.springpractice.domain.like.controller;


import com.likelion.springpractice.domain.food.dto.FoodResponse;
import com.likelion.springpractice.domain.like.service.LikeService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//사용자의 요청을 받아서, 그에 맞는 서비스 로직을 호출하고, 결과를 응답으로 반환하는 역할!!
@RestController  //API요청을 처리하는 컨트롤러임을 명시!
@RequiredArgsConstructor
@RequestMapping("/api/sp")
@Tag(name = "Like", description = "좋아요 관련 API")
public class LikeController {

  private final LikeService likeService;


  // 좋아요 누르기
  @Operation(summary = "음식 좋아요 누르기", description = "음식 상세 페이지에서 좋아요 버튼을 눌렀을 때 요청되는 API")
  @PostMapping("/foods/{foodId}/likes")
  public ResponseEntity<BaseResponse<String>> likeFood(
      @Parameter(description = "좋아요를 누를 음식 ID") @PathVariable Long foodId) {
    likeService.likeFood(foodId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 등록 완료", null));
  }

  @Operation(summary = "음식 좋아요 삭제", description = "음식 상세 페이지에서 좋아요를 취소했을 때 요청되는 API")
  @DeleteMapping("/{foodId}/likes")
  public ResponseEntity<BaseResponse<String>> unlikeFood(
      @Parameter(description = "좋아요를 취소할 음식 ID") @PathVariable Long foodId) {
    likeService.unlikeFood(foodId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 취소 완료", null));
  }

  @Operation(summary = "좋아요 누른 음식 목록 조회", description = "마이페이지에서 내가 좋아요 누른 음식들 조회하는 API")
  @GetMapping("/likes/my")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getMyLikedFoods() {
    List<FoodResponse> likedFoods = likeService.getLikedFoodsByUser();
    return ResponseEntity.ok(BaseResponse.success("내가 좋아요 누른 음식 조회 성공", likedFoods));
  }
}
