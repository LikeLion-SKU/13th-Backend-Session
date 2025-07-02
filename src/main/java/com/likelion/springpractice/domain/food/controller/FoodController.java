package com.likelion.springpractice.domain.food.controller;

import com.likelion.springpractice.domain.food.dto.request.CreateFoodRequest;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.service.FoodService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Food", description = "음식 관련 API")
public class FoodController {

    private final FoodService foodService;

    @Operation(summary = "음식 생성", description = "관리자가 음식 업로드 버튼을 눌렀을 때 요청되는 API")
    @PostMapping("/foods")
    public ResponseEntity<BaseResponse<FoodResponse>> createFood(
        @Parameter(description = "음식 작성 내용") @RequestBody @Valid CreateFoodRequest createFoodRequest) {
        FoodResponse response = foodService.createFood(createFoodRequest);

        return ResponseEntity.ok(BaseResponse.success("음식 생성 성공", response));
    }

    
}
