package com.likelion.springpractice.domain.food.controller;

import com.likelion.springpractice.domain.food.dto.request.FoodCreateRequest;
import com.likelion.springpractice.domain.food.dto.response.FoodListResponse;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.service.FoodService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
@Tag(name = "Food", description = "음식 관련 API (생성, 조회, 삭제)")
public class FoodController {

  private final FoodService foodService;

  @Operation(summary = "음식 생성", description = "관리자가 새로운 음식을 등록합니다.")
  @PostMapping
  public ResponseEntity<BaseResponse<FoodResponse>> createFood(
      @Valid @RequestBody FoodCreateRequest request) {
    FoodResponse res = foodService.createFood(request);
    return ResponseEntity.ok(BaseResponse.success("음식 생성 성공", res));
  }

  @Operation(summary = "음식 전체 조회", description = "등록된 모든 음식 목록을 조회합니다.")
  @GetMapping
  public ResponseEntity<BaseResponse<FoodListResponse>> getAllFoods() {
    FoodListResponse response = foodService.getAllFoods();
    return ResponseEntity.ok(BaseResponse.success("음식 전체 조회 성공", response));
  }

  @Operation(summary = "음식 상세 조회", description = "특정 음식 ID를 기준으로 상세 정보를 조회합니다.")
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<FoodResponse>> getFoodById(
      @Parameter(description = "조회할 음식 ID") @PathVariable Long id) {
    FoodResponse response = foodService.getFoodById(id);
    return ResponseEntity.ok(BaseResponse.success("음식 상세 조회 성공", response));
  }

  @Operation(summary = "음식 삭제", description = "특정 음식 ID를 삭제합니다.")
  @DeleteMapping("/{id}")
  public ResponseEntity<BaseResponse<Boolean>> deleteFood(
      @Parameter(description = "삭제할 음식 ID") @PathVariable Long id) {
    return ResponseEntity.ok(BaseResponse.success("음식 삭제 성공", foodService.deleteFood(id)));
  }
}