package com.likelion.springpractice.week11.controller;

import com.likelion.springpractice.week11.dto.response.FoodDetailResponseDto;
import com.likelion.springpractice.week11.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    // 음식 상세 조회
    @GetMapping("/{foodId}")
    public ResponseEntity<FoodDetailResponseDto> getFoodDetail(@PathVariable Long foodId) {
        return ResponseEntity.ok(foodService.getFoodDetail(foodId));
    }

    // 좋아요 수 기준 인기 음식 조회
    @GetMapping("/popular")
    public ResponseEntity<List<FoodDetailResponseDto>> getPopularFoods() {
        return ResponseEntity.ok(foodService.getPopularFoods());
    }
}