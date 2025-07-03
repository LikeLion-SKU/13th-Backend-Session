package com.likelion.springpractice.domain.food.controller;

import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.service.FoodService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
@Tag(name = "food", description = "음식 관련 API")
public class FoodController {

  private final FoodService foodService;

  // 음식 전체 조회 API
  @Operation(summary = "음식 전체 조회", description = "음식 검색 페이지로 이동할때 요청되는 API")
  @GetMapping("/foods")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoods() {
    List<FoodResponse> response = foodService.getAllFoods();
    return ResponseEntity.ok(BaseResponse.success("음식 전체 조회 성공", response));
  }

  // 음식 단일 조회 API
  @Operation(summary = "음식 단일 조회", description = "음식 검색 페이지에서 음식 하나에 접근할때 요청되는 API")
  @GetMapping("/foods/{id}")
  public ResponseEntity<BaseResponse<FoodResponse>> getFoodById(
      @Parameter(description = "특정 음식 ID") @PathVariable long id) {
    FoodResponse response = foodService.getFoodById(id);
    return ResponseEntity.ok(BaseResponse.success("음식 단일 조회 성공", response));
  }

  // 음식 인기순 조회 API
  @Operation(summary = "음식 좋아요 순으로 전체 조회", description = "음식 검색 페이지에서 좋아요 많은 순으로 접근할 때 요청되는 API")
  @GetMapping("/foods/likes")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getManyLikes() {
    List<FoodResponse> response = foodService.getPopularFoods();
    return ResponseEntity.ok(BaseResponse.success("음식 좋아요 순으로 조회 성공", response));
  }
  
}
