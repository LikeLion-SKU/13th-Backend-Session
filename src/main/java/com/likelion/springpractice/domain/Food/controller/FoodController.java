package com.likelion.springpractice.domain.Food.controller;

import com.likelion.springpractice.domain.Food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.Food.service.FoodService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@Tag(name = "Food", description = "음식 조회 API")
public class FoodController {

  private final FoodService foodService;

  @Operation(summary = "전체 음식 조회")
  @GetMapping
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoods() {
    return ResponseEntity.ok(BaseResponse.success("음식 목록 조회 성공", foodService.getAllFoods()));
  }

  @Operation(summary = "음식 상세 조회")
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<FoodResponse>> getFoodById(@PathVariable Long id) {
    return ResponseEntity.ok(BaseResponse.success("음식 상세 조회 성공", foodService.getFoodById(id)));
  }
}