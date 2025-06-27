package com.likelion.springpractice.domain.like.service;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.like.dto.request.LikeRequest;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.entity.FoodLike;
import com.likelion.springpractice.domain.like.mapper.LikeMapper;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

  private final FoodRepository foodRepository;
  private final LikeRepository likeRepository;
  private final LikeMapper likeMapper;

  @Transactional
  public LikeResponse toggleLike(Long foodId, Long userId, LikeRequest request) {
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    boolean wantLike = request.getLike();

    FoodLike foodLike = likeRepository.findByFoodIdAndUserId(foodId, userId)
        .orElse(null);

    if (foodLike == null) {
      foodLike = FoodLike.builder()
          .food(food)
          .userId(userId)
          .isLiked(wantLike)
          .likedAt(java.time.LocalDateTime.now())
          .build();
      likeRepository.save(foodLike);
    } else {
      // 기존 레코드 업데이트
      if (wantLike) {
        foodLike.like();
      } else {
        foodLike.cancel();
      }
    }

    int likeCnt = likeRepository.countByFoodIdAndIsLikedTrue(foodId);
    return likeMapper.toResponse(foodId, likeCnt, wantLike);
  }
}