package com.likelion.springpractice.domain.food.controller;

import com.likelion.springpractice.domain.food.dto.response.FoodDetailResponse;
import com.likelion.springpractice.domain.food.service.FoodService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@Tag(name = "Food", description = "음식 정보 API")
public class FoodController {

  private final FoodService foodService;

  @GetMapping("/{foodId}")
  @Operation(summary = "음식 상세 조회", description = "음식 이름과 설명 반환")
  public ResponseEntity<BaseResponse<FoodDetailResponse>> getFoodDetail(@PathVariable Long foodId) {
    FoodDetailResponse response = foodService.getFoodDetail(foodId);
    return ResponseEntity.ok(BaseResponse.success("음식 정보를 불러왔습니다.", response));
  }
}