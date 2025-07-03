package com.likelion.springpractice.domain.food.controller;

import com.likelion.springpractice.domain.food.dto.request.CreateFoodRequest;
import com.likelion.springpractice.domain.food.dto.request.UpdateFoodRequest;
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
import org.springframework.web.bind.annotation.PutMapping;
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

    @Operation(summary = "음식 전체 조회", description = "메인 화면에서 음식 전체를 조회할 때 요청되는 API")
    @GetMapping("/foods")
    public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoods() {
        List<FoodResponse> responses = foodService.getAllFoods();
        return ResponseEntity.ok(BaseResponse.success("음식 전체 조회 성공", responses));
    }

    @Operation(summary = "음식 개별 조회", description = "사용자가 음식 사진을 눌렀을 때 요청되는 API")
    @GetMapping("/foods/{foodId}")
    public ResponseEntity<BaseResponse<FoodResponse>> getFoodBydId(
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "id") Long id) {
        FoodResponse response = foodService.getFoodById(id);
        return ResponseEntity.ok(BaseResponse.success(id + "번 음식 조회 성공", response));
    }

    @Operation(summary = "음식 수정", description = "관리자가 음식 수정 후 수정 완료 버튼을 눌렀을 때 요청되는 API")
    @PutMapping("/foods/{foodId}")
    public ResponseEntity<BaseResponse<FoodResponse>> updateFood(
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "id") Long id,
        @Parameter(description = "음식 수정 내용") @RequestBody @Valid UpdateFoodRequest updateFoodRequest) {
        FoodResponse response = foodService.updateFood(id, updateFoodRequest);
        return ResponseEntity.ok(BaseResponse.success(id + "번 음식 수정 성공", response));
    }

    @Operation(summary = "음식 삭제", description = "관리자가 음식 삭제 버튼을 눌렀을 때 요청되는 API")
    @DeleteMapping("/foods/{foodId}")
    public ResponseEntity<BaseResponse<Boolean>> deleteFood(
        @Parameter(description = "특정 음식 ID") @PathVariable(value = "id") Long id) {
        Boolean response = foodService.deleteFood(id);
        return ResponseEntity.ok(BaseResponse.success(id + "번 음식 삭제 성공", response));
    }

    @Operation(summary = "음식 좋아요순 조회", description = "사용자가 음식 좋아요순 조회를 눌렀을 때 요청되는 API")
    @GetMapping("/foods/likes")
    public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoodsByLikes() {
        List<FoodResponse> responses = foodService.getAllFoodsSortedByLikes();
        return ResponseEntity.ok(BaseResponse.success("좋아요순 음식 전체 조회 성공", responses));
    }

    @Operation(summary = "음식 평점순 조회", description = "사용자가 음식 평점순 조회를 눌렀을 때 요청되는 API")
    @GetMapping("/foods/score")
    public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoodsByScore() {
        List<FoodResponse> responses = foodService.getAllFoodsSortedByScore();
        return ResponseEntity.ok(BaseResponse.success("평점순 음식 전체 조회 성공", responses));
    }
}
