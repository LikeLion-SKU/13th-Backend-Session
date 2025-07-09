package com.likelion.springpractice.domain.food.service;

import com.likelion.springpractice.domain.food.dto.request.CreateFoodRequest;
import com.likelion.springpractice.domain.food.dto.request.UpdateFoodRequest;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodService {

    private final FoodRepository foodRepository;

    //음식 생성
    @Transactional
    public FoodResponse createFood(CreateFoodRequest createFoodRequest) {
        //유효성 검사
        Food food = Food.builder()
            .name(createFoodRequest.getName())
            .description(createFoodRequest.getDescription())
            .image(createFoodRequest.getImage())
            .build();
        foodRepository.save(food);
        //로그 처리

        return toFoodResponse(food);
    }

    //음식 전체 조회
    public List<FoodResponse> getAllFoods() {
        List<Food> foodList = foodRepository.findAll();
        return foodList.stream().map(this::toFoodResponse).toList();
    }

    //음식 단일 조회
    public FoodResponse getFoodById(Long id) {
        Food food = foodRepository.findById(id)
            .orElseThrow();
        return toFoodResponse(food);
    }

    //음식 수정
    @Transactional
    public FoodResponse updateFood(Long id, UpdateFoodRequest updateFoodRequest) {
        Food food = foodRepository.findById(id)
            .orElseThrow();

        food.update(updateFoodRequest.getName(), updateFoodRequest.getDescription(),
            updateFoodRequest.getImage());
        return toFoodResponse(food);
    }

    //음식 삭제
    @Transactional
    public Boolean deleteFood(Long id) {
        Food food = foodRepository.findById(id)
            .orElseThrow();
        foodRepository.delete(food);
        return true;
    }

    //음식 좋아요순 조회
    public List<FoodResponse> getAllFoodsSortedByLikes() {
        List<Food> foodList = foodRepository.findAllByOrderByLikesDesc();
        return foodList.stream().map(this::toFoodResponse).toList();
    }

    //음식 평점순 조회
    public List<FoodResponse> getAllFoodsSortedByScore() {
        List<Food> foodList = foodRepository.findAllByOrderByScoreDesc();
        return foodList.stream().map(this::toFoodResponse).toList();
    }

    private FoodResponse toFoodResponse(Food food) {
        return FoodResponse.builder().foodId(food.getId())
            .name(food.getName()).description(food.getDescription()).image(food.getImage()).likes(food.getLikes()).score(food.getScore()).build();
    }
}
