package com.likelion.springpractice.domain.food.service;

import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.dto.response.SearchResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.mapper.FoodMapper;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Slf4j
@RequiredArgsConstructor
public class FoodService {
  private final FoodRepository foodRepository;
  private final FoodMapper foodMapper;
  
  // 음식명으로, 음식 단일 조회
  @Transactional
  public FoodResponse getFoodByName(String name) {
    // 넘겨받은 이름과 같은 음식명 찾기. 없으면 에러
    Food food = foodRepository.findByName(name).orElseThrow(() -> new CustomException(
        FoodErrorCode.FOOD_NOT_FOUND));

    // 해당 음식의 정보를 리턴.
    return foodMapper.toFoodResponse(food);
  }


  // 음식 전체 조회
  @Transactional
  public List<FoodResponse> getAllFoods(String sort) {
    List<Food> foodList = foodRepository.findAll();

    // 정렬 기준에 따라 리스트를 정렬
    if("rate".equals(sort)) {
      foodList.sort((a,b)->Double.compare(b.getRate(), a.getRate()));
    }
    else if("name".equals(sort)) {
      foodList.sort((a,b)->a.getName().compareToIgnoreCase(b.getName()));
    }
    else if("id".equals(sort)) {
      foodList.sort((a,b)->a.getId().compareTo(b.getId()));
    }

    return foodList.stream().map(foodMapper::toFoodResponse).toList();
  }


  
  // 음식 id로, 단일 음식 상세 보기
  @Transactional
  public FoodResponse getFoodById(Long foodId) {
    // 넘겨받은 아이디와 같은 음식 찾기. 없으면 에러
    Food food = foodRepository.findById(foodId).orElseThrow(() -> new CustomException(
        FoodErrorCode.FOOD_NOT_FOUND));

    // 해당 음식의 정보를 리턴.
    return foodMapper.toFoodResponse(food);
  }

  
  // 검색 음식 리스트 보기
  @Transactional
  public List<SearchResponse> getAllFoods(String keyword, String sort) {
    // 음식명, 설명에 키워드가 포함된 음식 리스트 저장.
    List<Food> foodList = foodRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);

    // 정렬 기준에 따라 리스트를 정렬
    if("rate".equals(sort)) {
      foodList.sort((a,b)->Double.compare(b.getRate(), a.getRate()));
    }
    else if("name".equals(sort)) {
      foodList.sort((a,b)->a.getName().compareToIgnoreCase(b.getName()));
    }
    else if("id".equals(sort)) {
      foodList.sort((a,b)->a.getId().compareTo(b.getId()));
    }


    // 리스트의 타입을 검색용으로 바꿔서 리턴.
    return foodList.stream().map(foodMapper::toSearchResponse).toList();
  }
  
  
}
