package com.likelion.springpractice.domain.foodlike.controller;

import com.likelion.springpractice.domain.foodlike.dto.response.FoodLikeResponse;
import com.likelion.springpractice.domain.foodlike.service.FoodLikeService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "FoodLike", description = "음식 좋아요 관련 API")
public class FoodLikeController {

    private final FoodLikeService foodLikeService;

    @Operation(summary = "음식 좋아요 추가", description = "음식 개별 조회 페이지에서 누르지 않은 좋아요를 눌렀을 때 요청되는 API")
    @PostMapping("/foods/{foodId}/likes")
    public ResponseEntity<BaseResponse<FoodLikeResponse>> createFoodLike(
        @Parameter(description = "특정 유저 ID") @PathVariable(value = "userId") Long userId,
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "foodId") Long foodId) {
        FoodLikeResponse response = foodLikeService.addFoodLike(userId, foodId);
        return ResponseEntity.ok(
            BaseResponse.success(userId + "번 유저가" + foodId + "번 음식 좋아요 성공", response));
    }

    @Operation(summary = "음식 좋아요 삭제", description = "음식 개별 조회 페이지에서 눌렀던 좋아요를 눌렀을 때 요청되는 API")
    @DeleteMapping("/foods/{foodId}/likes")
    public ResponseEntity<BaseResponse<Boolean>> deleteFoodLike(
        @Parameter(description = "특정 유저 ID") @PathVariable(value = "userId") Long userId,
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "foodId") Long foodId) {
        Boolean response = foodLikeService.removeFoodLike(userId, foodId);
        return ResponseEntity.ok(
            BaseResponse.success(userId + "번 유저가" + foodId + "번 음식 좋아요 삭제", response));
    }
}
