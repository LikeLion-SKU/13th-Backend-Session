package com.likelion.springpractice.week11.controller;

import com.likelion.springpractice.week11.domain.Food;
import com.likelion.springpractice.week11.dto.response.FoodDetailResponseDto;
import com.likelion.springpractice.week11.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Food", description = "음식 관련 API")
@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @Operation(summary = "음식 목록 조회", description = "데이터베이스에 등록된 모든 음식 정보를 반환합니다.")
    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }
    // 음식 상세 조회
    @Operation(summary = "음식 상세 조회", description = "foodId를 기반으로 음식 정보를 조회합니다.")
    @GetMapping("/{foodId}")
    public ResponseEntity<FoodDetailResponseDto> getFoodDetail(@PathVariable Long foodId) {
        return ResponseEntity.ok(foodService.getFoodDetail(foodId));
    }

    // 좋아요 수 기준 인기 음식 조회
    @Operation(summary = "인기 음식 조회", description = "인기 있는 음식 목록을 조회합니다.")
    @GetMapping("/popular")
    public ResponseEntity<List<FoodDetailResponseDto>> getPopularFoods() {
        return ResponseEntity.ok(foodService.getPopularFoods());
    }
}