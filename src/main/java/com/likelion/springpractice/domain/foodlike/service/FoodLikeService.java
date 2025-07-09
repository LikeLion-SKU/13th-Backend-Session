package com.likelion.springpractice.domain.foodlike.service;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.foodlike.dto.response.FoodLikeResponse;
import com.likelion.springpractice.domain.foodlike.entity.FoodLike;
import com.likelion.springpractice.domain.foodlike.exception.FoodLikeErrorCode;
import com.likelion.springpractice.domain.foodlike.repository.FoodLikeRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
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
    public FoodLikeResponse toggleFoodLike(Long userId, Long foodId) {
        User user = userRepository.findById(userId)
            .orElseThrow();

        Food food = foodRepository.findById(foodId)
            .orElseThrow();

        FoodLike foodLike;

        Optional<FoodLike> exist = foodLikeRepository.findByUserIdAndFoodId(userId, foodId);
        if (exist.isEmpty()) {
            foodLike = FoodLike.builder()
                    .isLiked(true)
                    .user(user)
                    .food(food).build();
            foodLikeRepository.save(foodLike);
            food.increaseLikes();
        } else {
            foodLike = exist.get();
            foodLike.setIsLiked(!foodLike.getIsLiked());
            if(foodLike.getIsLiked()) {
                food.increaseLikes();
            } else {
                food.decreaseLikes();
            }
            foodLikeRepository.save(foodLike);
        }

        return toFoodLikeResponse(foodLike);
    }

    public List<FoodLikeResponse> getAllUserLikes(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow();

        List<FoodLike> likeList = foodLikeRepository.findAllByUserId(userId);

        return likeList.stream().map(this::toFoodLikeResponse).toList();
    }

    //Response DTO Converter
    private FoodLikeResponse toFoodLikeResponse(FoodLike foodLike) {
        return FoodLikeResponse.builder().foodlikeId(foodLike.getId())
                .isLiked(foodLike.getIsLiked())
            .userId(foodLike.getUser().getId()).foodId(foodLike.getFood().getId()).build();
    }
}
