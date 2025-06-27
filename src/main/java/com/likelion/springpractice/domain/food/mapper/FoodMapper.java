package com.likelion.springpractice.domain.food.mapper;

import com.likelion.springpractice.domain.food.dto.response.FoodListResponse;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

  public FoodResponse toResponse(Food food) {
    return FoodResponse.builder()
        .id(food.getId())
        .foodName(food.getFoodName())
        .foodDescription(food.getFoodDescription())
        .likeCount(food.getLikeCount())
        .reviewCount(food.getReviewCount())
        .build();
  }

  public FoodListResponse toListResponse(List<Food> foods) {
    return FoodListResponse.builder()
        .foods(foods.stream()
            .map(this::toResponse)
            .collect(Collectors.toList()))
        .build();
  }
}

