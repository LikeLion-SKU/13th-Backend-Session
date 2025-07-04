package com.likelion.springpractice.week11.controller;

import com.likelion.springpractice.week11.dto.response.FoodDetailResponseDto;
import com.likelion.springpractice.week11.security.CustomUserDetails;
import com.likelion.springpractice.week11.service.FoodLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodLikeController {

    private final FoodLikeService foodLikeService;

    // 좋아요 등록/취소
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
    @GetMapping("/api/users/likes")
    public ResponseEntity<List<FoodDetailResponseDto>> getLikedFoods(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(foodLikeService.getLikedFoods(userDetails.getId()));
    }
}