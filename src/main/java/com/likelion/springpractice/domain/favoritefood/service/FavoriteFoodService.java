package com.likelion.springpractice.domain.favoritefood.service;

import com.likelion.springpractice.domain.favoritefood.dto.response.FavoriteFoodResponse;
import com.likelion.springpractice.domain.favoritefood.entity.FavoriteFood;
import com.likelion.springpractice.domain.favoritefood.exception.FavoriteFoodErrorCode;
import com.likelion.springpractice.domain.favoritefood.repository.FavoriteFoodRepository;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FavoriteFoodService {

  private final UserRepository userRepository;
  private final FoodRepository foodRepository;
  private final FavoriteFoodRepository favoriteRepository;

  @Transactional
  public FavoriteFoodResponse likeFood(Long userId, Long foodId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    if (favoriteRepository.existsByUserAndFood(user, food)) {
      throw new CustomException(FavoriteFoodErrorCode.ALREADY_LIKED);
    }

    FavoriteFood favorite = FavoriteFood.builder()
        .user(user)
        .food(food)
        .build();
    favoriteRepository.save(favorite);

    int likeCount = favoriteRepository.countByFood(food);
    log.info("Food liked: userId={}, foodId={}, totalLikes={}", userId, foodId, likeCount);

    return FavoriteFoodResponse.builder()
        .liked(true)
        .likeCount(likeCount)
        .build();
  }

  @Transactional
  public FavoriteFoodResponse unlikeFood(Long userId, Long foodId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    FavoriteFood favorite = favoriteRepository.findByUserAndFood(user, food)
        .orElseThrow(() -> new CustomException(FavoriteFoodErrorCode.FAVORITE_NOT_FOUND));

    favoriteRepository.delete(favorite);

    int likeCount = favoriteRepository.countByFood(food);
    log.info("Food unliked: userId={}, foodId={}, totalLikes={}", userId, foodId, likeCount);

    return FavoriteFoodResponse.builder()
        .liked(false)
        .likeCount(likeCount)
        .build();
  }
}