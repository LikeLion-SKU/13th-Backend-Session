package com.likelion.springpractice.domain.food.mapper;

import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

  public FoodResponse toFoodResponse(Food food) {
    return FoodResponse.builder()
        .food_id(food.getFoodId())
        .foodName(food.getFoodName())
        .description(food.getDescription())
        .likeNum(food.getLikeNum())
        .revirewNum(food.getRevirewNum())
        .rating(food.getRating())
        .build();
  }

}
