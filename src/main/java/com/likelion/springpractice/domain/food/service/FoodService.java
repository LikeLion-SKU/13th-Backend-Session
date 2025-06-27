package com.likelion.springpractice.domain.food.service;

import com.likelion.springpractice.domain.food.dto.request.FoodCreateRequest;
import com.likelion.springpractice.domain.food.dto.response.FoodListResponse;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.mapper.FoodMapper;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

  private final FoodRepository foodRepository;
  private final FoodMapper foodMapper;

  @Transactional
  public FoodResponse createFood(FoodCreateRequest req) {
    Food food = Food.builder()
        .foodName(req.getFoodName())
        .foodDescription(req.getFoodDescription())
        .likeCount(0)
        .reviewCount(0)
        .build();
    return foodMapper.toResponse(foodRepository.save(food));
  }

  public FoodListResponse getAllFoods() {
    List<Food> foods = foodRepository.findAll();
    return foodMapper.toListResponse(foods);
  }

  public FoodResponse getFoodById(Long id) {
    Food food = foodRepository.findById(id)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));
    return foodMapper.toResponse(food);
  }

  @Transactional
  public Boolean deleteFood(Long id) {
    Food food = foodRepository.findById(id)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));
    foodRepository.delete(food);
    return true;
  }
}