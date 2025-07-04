package com.likelion.springpractice.week11.service;

import com.likelion.springpractice.week11.domain.Food;
import com.likelion.springpractice.week11.domain.FoodLike;
import com.likelion.springpractice.week11.domain.User;
import com.likelion.springpractice.week11.dto.response.FoodDetailResponseDto;
import com.likelion.springpractice.week11.repository.FoodLikeRepository;
import com.likelion.springpractice.week11.repository.FoodRepository;
import com.likelion.springpractice.week11.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodLikeService {

    private final FoodLikeRepository foodLikeRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    // 좋아요 등록 또는 취소
    public boolean toggleLike(Long userId, Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("음식을 찾을 수 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        FoodLike like = foodLikeRepository.findByUserIdAndFoodId(userId, foodId)
                .orElse(null);

        if (like == null) {
            // 처음 좋아요
            FoodLike newLike = FoodLike.builder()
                    .user(user)
                    .food(food)
                    .likedAt(LocalDateTime.now())
                    .build();
            foodLikeRepository.save(newLike);
            return true; // 좋아요 처리
        } else {
            if (like.getDeletedAt() == null) {
                like.setDeletedAt(LocalDateTime.now()); // soft delete 처리
                foodLikeRepository.save(like);
                return false; // 좋아요 취소
            } else {
                like.setDeletedAt(null); // 다시 좋아요
                like.setLikedAt(LocalDateTime.now());
                foodLikeRepository.save(like);
                return true; // 좋아요 처리
            }
        }
    }

    // 내가 좋아요한 음식 목록 조회
    public List<FoodDetailResponseDto> getLikedFoods(Long userId) {
        return foodLikeRepository.findAllByUserId(userId).stream()
                .filter(like -> like.getDeletedAt() == null)
                .map(like -> {
                    Food food = like.getFood();
                    return new FoodDetailResponseDto(
                            food.getId(),
                            food.getName(),
                            food.getDescription(),
                            0.0, // 상세 아님 → 평점 생략
                            0,   // 상세 아님 → 좋아요 수 생략
                            null
                    );
                })
                .collect(Collectors.toList());
    }
}