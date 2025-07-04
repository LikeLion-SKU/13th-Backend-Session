package com.likelion.springpractice.domain.food.controller;

import com.likelion.springpractice.domain.food.dto.request.FoodCreateRequest;
import com.likelion.springpractice.domain.food.dto.response.FoodDetailResponse;
import com.likelion.springpractice.domain.food.dto.response.FoodSummaryResponse;
import com.likelion.springpractice.domain.food.dto.response.FoodWithLikeResponse;
import com.likelion.springpractice.domain.food.service.FoodService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/foods")
@Tag(name = "Food", description = "음식 관리 API")
@Slf4j
public class FoodController {

  private final FoodService foodService;

  @Operation(summary = "음식 목록 조회 API", description = "음식 목록을 조회하는 API")
  @GetMapping
  public ResponseEntity<BaseResponse<List<FoodSummaryResponse>>> getFoodList() {
    List<FoodSummaryResponse> foodList = foodService.getFoodList();
    return ResponseEntity.ok(BaseResponse.success("음식 목록 조회 성공", foodList));
  }

  @Operation(summary = "음식 상세 조회 API", description = "음식의 상세 정보를 조회하는 API")
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<FoodDetailResponse>> getFoodDetail(
      @PathVariable Long id) {
    FoodDetailResponse foodDetail = foodService.getFoodDetail(id);
    return ResponseEntity.ok(BaseResponse.success("음식 상세 조회 성공", foodDetail));
  }

  @Operation(summary = "베스트 인기 순위 음식 조회 API", description = "베스트 인기 순위 음식을 조회하는 API")
  @GetMapping("/best")
  public ResponseEntity<BaseResponse<List<FoodWithLikeResponse>>> getBestFoodList() {
    List<FoodWithLikeResponse> bestFoodList = foodService.getBestFoodList();
    return ResponseEntity.ok(BaseResponse.success("베스트 음식 목록 조회 성공", bestFoodList));
  }

  // 음식 추가(임시)
  @Operation(summary = "음식 추가 API", description = "새로운 음식을 추가하는 API")
  @PostMapping
  public ResponseEntity<BaseResponse<Void>> addFood(@RequestBody FoodCreateRequest foodRequest) {
    foodService.addFood(foodRequest);
    return ResponseEntity.ok(BaseResponse.success("음식 추가 성공", null));
  }

}
