package com.likelion.springpractice.domain.foodlike.service;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.foodlike.dto.response.FoodLikeResponse;
import com.likelion.springpractice.domain.foodlike.entity.FoodLike;
import com.likelion.springpractice.domain.foodlike.repository.FoodLikeRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodLikeService {

    private final FoodLikeRepository foodLikeRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    //음식 좋아요 추가
    @Transactional
    public FoodLikeResponse addFoodLike(Long userId, Long foodId) {
        User user = userRepository.findById(userId)
            .orElseThrow();

        Food food = foodRepository.findById(foodId)
            .orElseThrow();

        FoodLike foodLike = FoodLike.builder()
            .user(user)
            .food(food).build();

        foodLikeRepository.save(foodLike);

        return toFoodLikeResponse(foodLike);
    }

    //음식 좋아요 삭제
    @Transactional
    public Boolean removeFoodLike(Long userId, Long foodId) {
        User user = userRepository.findById(userId)
            .orElseThrow();

        Food food = foodRepository.findById(foodId)
            .orElseThrow();

        Optional<FoodLike> foodLike = foodLikeRepository.findByUserIdAndFoodId(userId, foodId);
        foodLikeRepository.delete(foodLike.get());

        return true;
    }

    //Response DTO Converter
    private FoodLikeResponse toFoodLikeResponse(FoodLike foodLike) {
        return FoodLikeResponse.builder().foodlikeId(foodLike.getId())
            .userId(foodLike.getUser().getId()).foodId(foodLike.getFood().getId()).build();
    }
}
