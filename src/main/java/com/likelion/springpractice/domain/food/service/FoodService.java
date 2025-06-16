package com.likelion.springpractice.domain.food.service;

import com.likelion.springpractice.domain.food.dto.request.CreateFoodRequest;
import com.likelion.springpractice.domain.food.dto.request.GetFoodRequest;
import com.likelion.springpractice.domain.food.dto.request.UpdateFoodRequest;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.mapper.FoodMapper;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.post.exception.PostErrorCode;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodService {

  private final FoodRepository foodRepository;
  private final FoodMapper foodMapper;
  private final UserRepository userRepository;

  // 음식 생성
  @Transactional
  public FoodResponse createFood(CreateFoodRequest createFoodRequest) {

    Food food = Food.builder()
        .foodName(createFoodRequest.getFoodName())
        .description(createFoodRequest.getDescription())
        .likeNum(0)
        .rating(0.0)
        .reviewNum(0)
        .build();

    foodRepository.save(food);

    return foodMapper.toFoodResponse(food);
  }

  // 음식 전체 조회
  public List<FoodResponse> getAllFoods() {
    List<Food> foodList = foodRepository.findAll();
    return foodList.stream().map(foodMapper::toFoodResponse).collect(Collectors.toList());
  }

  // 음식 단일 조회
  public FoodResponse getFoodById(Long foodId) {
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> {
          throw new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
        });

    return foodMapper.toFoodResponse(food);
  }

  // 음식 수정
  @Transactional
  public FoodResponse updateFood(Long foodId, UpdateFoodRequest updateFoodRequest) {
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> {
          throw new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
        });

    food.update(updateFoodRequest.getFoodName(), updateFoodRequest.getDescription());

    return foodMapper.toFoodResponse(food);
  }

  // 음식 삭제
  @Transactional
  public Boolean deleteFood(Long foodId) {
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> {
          throw new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
        });
    foodRepository.deleteById(foodId);
    return true;
  }

}
