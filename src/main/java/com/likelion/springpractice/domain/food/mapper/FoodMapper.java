package com.likelion.springpractice.domain.food.mapper;

import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

  public FoodResponse toFoodResponse(Food food, long likeCount, double avgRating) {
    return FoodResponse.builder()
        .foodId(food.getFoodId())
        .foodname(food.getFoodName())
        .foodDescription(food.getFoodDescription())
        .avgRating(avgRating)  // 좋아요 수, 매운맛 평점은 Service에서 계산해서 전달받을 예정
        .likeCount(likeCount)
        .build();
  }
}
