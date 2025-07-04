package com.likelion.springpractice.domain.Food.service;

import com.likelion.springpractice.domain.Food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.Food.entity.Food;
import com.likelion.springpractice.domain.Food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.Food.mapper.FoodMapper;
import com.likelion.springpractice.domain.Food.repository.FoodRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FoodService {

  private final FoodRepository foodRepository;
  private final FoodMapper foodMapper;

  @Transactional(readOnly = true)
  public List<FoodResponse> getAllFoods() {

    List<Food> foodList = foodRepository.findAll();
    return foodMapper.toFoodResponseList(foodList);
  }

  @Transactional(readOnly = true)
  public List<FoodResponse> getPopularFoods() {

    List<Food> foodList = foodRepository.findFoodsByLikeCountDesc();
    return foodMapper.toFoodResponseList(foodList);
  }

  @Transactional(readOnly = true)
  public List<FoodResponse> searchFoods(String keyword) {

    // 검색어가 null이거나 blank()인 경우
    if (keyword == null || keyword.isBlank()) {
      throw new CustomException(FoodErrorCode.INVALID_SEARCH_KEYWORD);
    }

    List<Food> foodList = foodRepository.searchByKeyword(keyword);

    // 검색 결과가 없는 경우
    if (foodList.isEmpty()) {
      throw new CustomException(FoodErrorCode.NO_SEARCH_RESULT);
    }
    return foodMapper.toFoodResponseList(foodList);
  }

  @Transactional(readOnly = true)
  public FoodResponse getFoodById(Long id) {
    Food food = foodRepository.findById(id)
        .orElseThrow(() -> {
          return new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
        });

    return foodMapper.toFoodResponse(food);
  }
}
