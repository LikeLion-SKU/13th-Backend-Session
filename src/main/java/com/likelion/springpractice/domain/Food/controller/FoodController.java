package com.likelion.springpractice.domain.Food.controller;

import com.likelion.springpractice.domain.Food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.Food.service.FoodService;
import com.likelion.springpractice.domain.Like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.Like.service.LikeService;
import com.likelion.springpractice.domain.Review.dto.request.ReviewRequest;
import com.likelion.springpractice.domain.Review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.Review.service.ReviewService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
@Tag(name = "Food", description = "Food 관리 API")
public class FoodController {

  private final FoodService foodService;
  private final LikeService likeService;
  private final ReviewService reviewService;

  // 이건 페이징 처리하면 좋을 것 같다!! 쿼리스트링으로 page 받아오기.
  @Operation(summary = "음식 전체 조회 API", description = "음식 전체 조회를 위한 API")
  @GetMapping("/")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoods() {

    List<FoodResponse> responses = foodService.getAllFoods();

    return ResponseEntity.ok(BaseResponse.success("음식 전체 조회에 성공했습니다.", responses));
  }

  @Operation(summary = "음식 베스트 인기 순위 API", description = "음식 베스트 인기 순위를 위한 API")
  @GetMapping("/popular")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getPopularFoods() {

    List<FoodResponse> responses = foodService.getPopularFoods();

    return ResponseEntity.ok(BaseResponse.success("음식 베스트 인기 순위 조회에 성공했습니다.", responses));
  }

  @Operation(summary = "음식 검색어 조회 API", description = "음식 검색어 조회를 위한 API")
  @GetMapping("/search")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> searchFoods(
      @RequestParam(name = "keyword") String keyword) {

    List<FoodResponse> responses = foodService.searchFoods(keyword);

    return ResponseEntity.ok(BaseResponse.success("음식 검색어 조회에 성공했습니다.", responses));
  }

  @Operation(summary = "음식 단일 조회 API", description = "음식 단일 조회를 위한 API")
  @GetMapping("/{foodId}")
  public ResponseEntity<BaseResponse<FoodResponse>> getFood(
      @Parameter(description = "특정 음식 ID")
      @PathVariable Long foodId) {

    FoodResponse response = foodService.getFoodById(foodId);

    return ResponseEntity.ok(BaseResponse.success("음식 조회에 성공했습니다.", response));
  }

  @Operation(summary = "음식에 좋아요 최초 등록 API", description = "좋아요 최초 등록을 위한 API")
  @PostMapping("/{foodId}/likes")
  public ResponseEntity<BaseResponse<LikeResponse>> createLike(
      @Parameter(hidden = true)
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @Parameter(description = "특정 음식 ID")
      @PathVariable Long foodId) {

    LikeResponse response = likeService.createLike(userDetails.getUser().getId(), foodId);
    return ResponseEntity.ok(BaseResponse.success("좋아요 등록에 성공했습니다.", response));
  }

  @Operation(summary = "후기 작성 API", description = "특정 음식에 대한 후기를 작성하는 API")
  @PostMapping("/{foodId}/reviews")
  public ResponseEntity<BaseResponse<ReviewResponse>> createReview(
      @Parameter(hidden = true)
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @Parameter(description = "리뷰를 작성할 음식 ID") @PathVariable Long foodId,
      @RequestBody ReviewRequest reviewRequest) {

    ReviewResponse response = reviewService.createReview(userDetails.getUser().getId(), foodId,
        reviewRequest);
    return ResponseEntity.ok(BaseResponse.success("후기 작성에 성공했습니다.", response));
  }
}
