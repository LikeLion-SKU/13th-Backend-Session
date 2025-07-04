package com.likelion.springpractice.domain.Food.mapper;

import com.likelion.springpractice.domain.Food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.Food.entity.Food;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

  public FoodResponse toFoodResponse(Food food) {
    return FoodResponse
        .builder()
        .id(food.getId())
        .name(food.getName())
        .description(food.getDescription())
        .likeCount(food.getLikeCount())
        .spicyLevelAvg(food.getSpicyLevelAvg())
        .build();
  }

  public List<FoodResponse> toFoodResponseList(List<Food> foodList) {
    return foodList.stream()
        .map(this::toFoodResponse)
        .toList();
  }
}
