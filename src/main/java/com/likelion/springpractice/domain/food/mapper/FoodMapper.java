package com.likelion.springpractice.domain.food.mapper;

import com.likelion.springpractice.domain.food.dto.response.FoodDetailResponse;
import com.likelion.springpractice.domain.food.dto.response.FoodSummaryResponse;
import com.likelion.springpractice.domain.food.dto.response.FoodWithLikeResponse;
import com.likelion.springpractice.domain.food.entity.Food;

public class FoodMapper {

  public static FoodDetailResponse toFoodDetailResponse(Food food) {
    return FoodDetailResponse.builder()
        .id(food.getId())
        .name(food.getName())
        .description(food.getDescription())
        .build();
  }

  public static FoodSummaryResponse toFoodSummaryResponse(Food food) {
    return FoodSummaryResponse.builder()
        .id(food.getId())
        .name(food.getName())
        .build();
  }

  public static FoodWithLikeResponse toFoodWithLikeResponse(Food food, Long likeCount) {
    return FoodWithLikeResponse.builder()
        .id(food.getId())
        .name(food.getName())
        .likeCount(likeCount)
        .build();
  }

}
