package com.likelion.springpractice.domain.food.controller;

import com.likelion.springpractice.domain.food.dto.request.CreateFoodRequest;
import com.likelion.springpractice.domain.food.dto.request.UpdateFoodRequest;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.service.FoodService;
import com.likelion.springpractice.domain.foodlike.dto.response.FoodLikeResponse;
import com.likelion.springpractice.domain.foodlike.service.FoodLikeService;
import com.likelion.springpractice.domain.foodreview.dto.request.CreateFoodReviewRequest;
import com.likelion.springpractice.domain.foodreview.dto.response.FoodReviewResponse;
import com.likelion.springpractice.domain.foodreview.service.FoodReviewService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Food", description = "음식 관련 API")
public class FoodController {

    private final FoodService foodService;
    private final FoodLikeService foodLikeService;
    private final FoodReviewService foodReviewService;

    @Operation(summary = "음식 생성", description = "관리자가 음식 업로드 버튼을 눌렀을 때 요청되는 API", tags = {
        "음식 CRUD"})
    @PostMapping("/foods")
    public ResponseEntity<BaseResponse<FoodResponse>> createFood(
        @Parameter(description = "음식 작성 내용") @RequestBody @Valid CreateFoodRequest createFoodRequest) {
        FoodResponse response = foodService.createFood(createFoodRequest);

        return ResponseEntity.ok(BaseResponse.success("음식 생성 성공", response));
    }

    @Operation(summary = "음식 전체 조회", description = "메인 화면에서 음식 전체를 조회할 때 요청되는 API", tags = {
        "음식 CRUD"})
    @GetMapping("/foods")
    public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoods() {
        List<FoodResponse> responses = foodService.getAllFoods();
        return ResponseEntity.ok(BaseResponse.success("음식 전체 조회 성공", responses));
    }

    @Operation(summary = "음식 개별 조회", description = "사용자가 음식 사진을 눌렀을 때 요청되는 API", tags = {
        "음식 CRUD"})
    @GetMapping("/foods/{foodId}")
    public ResponseEntity<BaseResponse<FoodResponse>> getFoodBydId(
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "id") Long id) {
        FoodResponse response = foodService.getFoodById(id);
        return ResponseEntity.ok(BaseResponse.success(id + "번 음식 조회 성공", response));
    }

    @Operation(summary = "음식 수정", description = "관리자가 음식 수정 후 수정 완료 버튼을 눌렀을 때 요청되는 API", tags = {
        "음식 CRUD"})
    @PutMapping("/foods/{foodId}")
    public ResponseEntity<BaseResponse<FoodResponse>> updateFood(
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "id") Long id,
        @Parameter(description = "음식 수정 내용") @RequestBody @Valid UpdateFoodRequest updateFoodRequest) {
        FoodResponse response = foodService.updateFood(id, updateFoodRequest);
        return ResponseEntity.ok(BaseResponse.success(id + "번 음식 수정 성공", response));
    }

    @Operation(summary = "음식 삭제", description = "관리자가 음식 삭제 버튼을 눌렀을 때 요청되는 API", tags = {
        "음식 CRUD"})
    @DeleteMapping("/foods/{foodId}")
    public ResponseEntity<BaseResponse<Boolean>> deleteFood(
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "id") Long id) {
        Boolean response = foodService.deleteFood(id);
        return ResponseEntity.ok(BaseResponse.success(id + "번 음식 삭제 성공", response));
    }

    @Operation(summary = "음식 좋아요순 조회", description = "사용자가 음식 좋아요순 조회를 눌렀을 때 요청되는 API", tags = {
        "음식 조회 관련"})
    @GetMapping("/foods/likes")
    public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoodsByLikes() {
        List<FoodResponse> responses = foodService.getAllFoodsSortedByLikes();
        return ResponseEntity.ok(BaseResponse.success("좋아요순 음식 전체 조회 성공", responses));
    }

    @Operation(summary = "음식 평점순 조회", description = "사용자가 음식 평점순 조회를 눌렀을 때 요청되는 API", tags = {
        "음식 조회 관련"})
    @GetMapping("/foods/score")
    public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoodsByScore() {
        List<FoodResponse> responses = foodService.getAllFoodsSortedByScore();
        return ResponseEntity.ok(BaseResponse.success("평점순 음식 전체 조회 성공", responses));
    }

    @Operation(summary = "음식 좋아요 추가", description = "음식 개별 조회 페이지에서 누르지 않은 좋아요를 눌렀을 때 요청되는 API", tags = {
        "음식 좋아요 관련"})
    @PostMapping("/foods/{foodId}/likes")
    public ResponseEntity<BaseResponse<FoodLikeResponse>> createFoodLike(
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "foodId") Long foodId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getId();
        FoodLikeResponse response = foodLikeService.addFoodLike(userId, foodId);
        return ResponseEntity.ok(
            BaseResponse.success(userId + "번 유저가" + foodId + "번 음식 좋아요 성공", response));
    }

    @Operation(summary = "음식 좋아요 삭제", description = "음식 개별 조회 페이지에서 눌렀던 좋아요를 눌렀을 때 요청되는 API", tags = {
        "음식 좋아요 관련"})
    @DeleteMapping("/foods/{foodId}/likes")
    public ResponseEntity<BaseResponse<Boolean>> deleteFoodLike(
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "foodId") Long foodId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getId();
        Boolean response = foodLikeService.removeFoodLike(userId, foodId);
        return ResponseEntity.ok(
            BaseResponse.success(userId + "번 유저가" + foodId + "번 음식 좋아요 삭제", response));
    }

    @Operation(summary = "음식 리뷰 추가", description = "음식 개별 조회 페이지에서 리뷰 작성을 누르면 요청되는 API", tags = {
        "음식 리뷰 관련"})
    @PostMapping("/foods/{foodId}/review")
    public ResponseEntity<BaseResponse<FoodReviewResponse>> createFoodReview(
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "foodId") Long foodId,
        @Parameter(description = "리뷰 내용") @RequestBody @Valid CreateFoodReviewRequest createFoodReviewRequest,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getId();
        FoodReviewResponse response = foodReviewService.addFoodReview(userId, foodId,
            createFoodReviewRequest);
        return ResponseEntity.ok(
            BaseResponse.success(userId + "번 유저가" + foodId + "번 음식 리뷰 성공", response));
    }

    @Operation(summary = "음식 리뷰 삭제", description = "음식 개별 조회 페이지에서 작성한 리뷰 삭제를 누르면 요청되는 API", tags = {
        "음식 리뷰 관련"})
    @DeleteMapping("/foods/{foodId}/review")
    public ResponseEntity<BaseResponse<Boolean>> deleteFoodReview(
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "foodId") Long foodId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long userId = userDetails.getUser().getId();
        Boolean response = foodReviewService.removeFoodReview(userId, foodId);
        return ResponseEntity.ok(
            BaseResponse.success(userId + "번 유저가" + foodId + "번 음식 리뷰 삭제", response));
    }
}
