package com.likelion.springpractice.week11.controller;

import com.likelion.springpractice.global.security.CustomUserDetails;
import com.likelion.springpractice.week11.dto.response.FoodDetailResponseDto;
import com.likelion.springpractice.week11.service.FoodLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Like", description = "좋아요 관련 API")
@RestController
@RequiredArgsConstructor
public class FoodLikeController {

    private final FoodLikeService foodLikeService;

    // 좋아요 등록/취소
    @Operation(
            summary = "좋아요 등록/취소",
            description = "해당 음식(foodId)에 대해 좋아요를 등록하거나, 이미 누른 경우 좋아요를 취소합니다. "
                    + "로그인된 사용자 기준으로 처리됩니다."
    )
    @PostMapping("/api/foods/{foodId}/like")
    public ResponseEntity<String> toggleLike(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long foodId) {

        boolean liked = foodLikeService.toggleLike(userDetails.getId(), foodId);
        return liked ?
                ResponseEntity.ok("좋아요 등록 완료") :
                ResponseEntity.ok("좋아요 취소 완료");
    }

    // 내가 좋아요한 음식 목록 조회
    @Operation(summary = "좋아요 누른 음식 조회", description = "내가 좋아요를 누른 음식 목록을 조회합니다.")
    @GetMapping("/api/users/likes")
    public ResponseEntity<List<FoodDetailResponseDto>> getLikedFoods(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(foodLikeService.getLikedFoods(userDetails.getId()));
    }
}