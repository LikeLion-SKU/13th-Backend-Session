package com.likelion.springpractice.domain.mission.service;

import com.likelion.springpractice.domain.mission.dto.request.FoodSimpleRequest;
import com.likelion.springpractice.domain.mission.dto.response.FoodRatingResponse;
import com.likelion.springpractice.domain.mission.dto.response.FoodSimpleResponse;
import com.likelion.springpractice.domain.mission.entity.Food;
import com.likelion.springpractice.domain.mission.entity.Review;
import com.likelion.springpractice.domain.mission.exception.FoodErrorCode;
import com.likelion.springpractice.domain.mission.repository.FoodRepository;
import com.likelion.springpractice.domain.mission.repository.ReviewRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {

  private final FoodRepository foodRepository;
  private final ReviewRepository reviewRepository;

  // 음식 등록
  public FoodSimpleResponse createFood(FoodSimpleRequest request) {
    Food food = Food.builder()
        .name(request.getName())
        .content("") // description 대신 content에 빈 값 또는 기본 텍스트
        .likeCount(0)
        .build();

    Food saved = foodRepository.save(food);

    return FoodSimpleResponse.builder()
        .foodId(saved.getId())
        .name(saved.getName())
        .likeCount(saved.getLikeCount())
        .build();
  }

  // 음식 전체 조회
  public List<FoodSimpleResponse> getAllFoods() {
    List<Food> foodList = foodRepository.findAll();

    return foodList.stream().map(food ->
        FoodSimpleResponse.builder()
            .foodId(food.getId())
            .name(food.getName())
            .likeCount(food.getLikeCount())
            .build()
    ).collect(Collectors.toList());
  }

  //평점 평균 조회
  public FoodRatingResponse getRating(Long foodId) {
    Food food = foodRepository.findById(foodId).orElse(null);
    if (food == null) {
      throw new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
    }

    List<Review> reviews = reviewRepository.findAllByFoodId(foodId);
    double average = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);

    return FoodRatingResponse.builder()
        .foodId(food.getId())
        .name(food.getName())
        .averageRating(average)
        .build();
  }
}