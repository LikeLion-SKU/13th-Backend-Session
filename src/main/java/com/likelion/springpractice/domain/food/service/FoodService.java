package com.likelion.springpractice.domain.food.service;

import com.likelion.springpractice.domain.food.dto.response.FoodDetailResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodService {

  private final FoodRepository foodRepository;

  public FoodDetailResponse getFoodDetail(Long foodId) {
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    log.info("Food detail retrieved: foodId={}, foodName={}", food.getId(), food.getFoodName());

    return FoodDetailResponse.builder()
        .foodName(food.getFoodName())
        .description(food.getDescription())
        .build();
  }
}