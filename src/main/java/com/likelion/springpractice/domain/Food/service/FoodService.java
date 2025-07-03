package com.likelion.springpractice.domain.Food.service;

import com.likelion.springpractice.domain.Food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.Food.entity.Food;
import com.likelion.springpractice.domain.Food.repository.FoodRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {

  private final FoodRepository foodRepository;

  public List<FoodResponse> getAllFoods() {
    return foodRepository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  public FoodResponse getFoodById(Long id) {
    Food food = foodRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 음식이 존재하지 않습니다."));
    return toResponse(food);
  }

  private FoodResponse toResponse(Food food) {
    return FoodResponse.builder()
        .id(food.getId())
        .name(food.getName())
        .description(food.getDescription())
        .spiciness(food.getSpiciness())
        .build();
  }
}