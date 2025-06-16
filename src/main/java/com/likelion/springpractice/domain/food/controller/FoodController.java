package com.likelion.springpractice.domain.food.controller;

import com.likelion.springpractice.domain.food.dto.request.CreateFoodRequest;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.service.FoodService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @Operation(summary = "음식 생성 API", description = "음식 페이지에서 음식 생성 버튼을 눌렀을 때 요청되는 API")
  @PostMapping
  public ResponseEntity<BaseResponse<FoodResponse>> createFood(
      @Parameter(description = "음식 내용")
      @RequestBody @Valid CreateFoodRequest createFoodRequest) {
    FoodResponse foodResponse = foodService.createFood(createFoodRequest);
    return ResponseEntity.ok(BaseResponse.success("음식 생성 완료", foodResponse));
  }

  @Operation(summary = "음식 단일 조회 API", description = "음식 페이지에서 특정 음식 조회 버튼을 눌렀을 때 요청되는 API")
  @GetMapping("/{foodId}")
  public ResponseEntity<BaseResponse<FoodResponse>> getFoodsById(@Parameter(description = "특정 음식 ID") @PathVariable Long foodId) {
    FoodResponse foodResponse = foodService.getFoodById(foodId);
    return ResponseEntity.ok(BaseResponse.success("해당 음식 조회 성공", foodResponse));
  }

  @Operation(summary = "음식 전체 조회 API", description = "음식 페이지에서 음식 전체 조회 버튼을 눌렀을 때 요청되는 API")
  @GetMapping
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoods() {
    List<FoodResponse> foodList = foodService.getAllFoods();
    return ResponseEntity.ok(BaseResponse.success("음식 전체 조회 성공", foodList));
  }

  @Operation(summary = "음식 삭제 API", description = "음식 페이지에서 음식 삭제 버튼을 눌렀을 때 요청되는 API")
  @DeleteMapping("/{foodId}")
  public ResponseEntity<BaseResponse<Boolean>> deleteFood(@PathVariable Long foodId) {
    Boolean result = foodService.deleteFood(foodId);
    return ResponseEntity.ok(BaseResponse.success("음식 삭제 성공",result));
  }
}
