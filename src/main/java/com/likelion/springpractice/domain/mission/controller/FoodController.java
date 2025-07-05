package com.likelion.springpractice.domain.mission.controller;

import com.likelion.springpractice.domain.mission.dto.request.FoodSimpleRequest;
import com.likelion.springpractice.domain.mission.dto.response.FoodRatingResponse;
import com.likelion.springpractice.domain.mission.dto.response.FoodSimpleResponse;
import com.likelion.springpractice.domain.mission.service.FoodService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
@Tag(name = "Food", description = "음식 관련 API")
public class FoodController {

  private final FoodService foodService;


  // 음식 등록
  @Operation(summary = "음식 등록 API", description = "음식 등록을 위한 API")
  @PostMapping
  public ResponseEntity<BaseResponse<FoodSimpleResponse>> createFood(
      @RequestBody FoodSimpleRequest request) {
    FoodSimpleResponse response = foodService.createFood(request);
    return ResponseEntity.ok(BaseResponse.success("음식 등록 성공", response));
  }

  // ✅ 음식 전체 조회
  @Operation(summary = "음식 조회 API", description = "음식 조회를 위한 API")
  @GetMapping
  public ResponseEntity<BaseResponse<List<FoodSimpleResponse>>> getAllFoods() {
    List<FoodSimpleResponse> responseList = foodService.getAllFoods();
    return ResponseEntity.ok(BaseResponse.success("음식 조회 성공", responseList));
  }

  // 음식 평균 평점 조회
  @Operation(summary = "음식 평균 평점 조회 API", description = "음식 평균 평점을 위한 API")
  @GetMapping("/{foodId}/rating")
  public ResponseEntity<BaseResponse<FoodRatingResponse>> getAverageRating(
      @PathVariable Long foodId
  ) {
    FoodRatingResponse response = foodService.getRating(foodId);
    return ResponseEntity.ok(BaseResponse.success("평점 조회 성공", response));
  }

}