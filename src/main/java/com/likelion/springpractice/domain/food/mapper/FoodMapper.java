package com.likelion.springpractice.domain.food.mapper;

import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.dto.response.SearchResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.user.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

  public FoodResponse toFoodResponse(Food food) {
    return FoodResponse.builder()
        .foodId(food.getId())
        .name(food.getName())
        .description(food.getDescription())
        .rate(food.getRate())
        .build();
  }

  public SearchResponse toSearchResponse(Food food) {
    return SearchResponse.builder()
        .foodId(food.getId())
        .name(food.getName())
        .rate(food.getRate())
        .build();
  }

}
