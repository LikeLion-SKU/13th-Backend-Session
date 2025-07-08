package com.likelion.springpractice.domain.foodreview.service;

import com.likelion.springpractice.domain.badge.evaluator.BadgeConditionEvaluator;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.foodreview.dto.request.CreateFoodReviewRequest;
import com.likelion.springpractice.domain.foodreview.dto.response.FoodReviewResponse;
import com.likelion.springpractice.domain.foodreview.entity.FoodReview;
import com.likelion.springpractice.domain.foodreview.exception.FoodReviewErrorCode;
import com.likelion.springpractice.domain.foodreview.repository.FoodReviewRepository;
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
@Slf4j
@RequiredArgsConstructor
public class FoodReviewService {

    private final FoodReviewRepository foodReviewRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final BadgeConditionEvaluator badgeConditionEvaluator;

    //리뷰 추가
    @Transactional
    public FoodReviewResponse addFoodReview(Long userId, Long foodId,
        CreateFoodReviewRequest createFoodReviewRequest) {
        User user = userRepository.findById(userId)
            .orElseThrow();

        Food food = foodRepository.findById(foodId)
            .orElseThrow();

        Optional<FoodReview> isExist = foodReviewRepository.findByUserIdAndFoodId(userId, foodId);
        if (isExist.isPresent()) {
            throw new CustomException(FoodReviewErrorCode.FOODREVIEW_ALREADY_EXISTS);
        }

        FoodReview foodReview = FoodReview.builder()
            .food(food)
            .user(user)
            .title(createFoodReviewRequest.getTitle())
            .content(createFoodReviewRequest.getContent())
            .score(createFoodReviewRequest.getScore())
            .build();

        foodReviewRepository.save(foodReview);
        food.updateScore(foodReviewRepository.findAverageScoreByFoodId(foodId));
        badgeConditionEvaluator.evaluate(user);
        return toFoodReviewResponse(foodReview);
    }

    //리뷰 삭제
    @Transactional
    public Boolean removeFoodReview(Long userId, Long foodId) {
        User user = userRepository.findById(userId)
            .orElseThrow();

        Food food = foodRepository.findById(foodId)
            .orElseThrow();

        Optional<FoodReview> foodReview = foodReviewRepository.findByUserIdAndFoodId(userId,
            foodId);
        if (!foodReview.isPresent()) {
            throw new CustomException(FoodReviewErrorCode.FOODREVIEW_NOT_FOUND);
        }

        foodReviewRepository.delete(foodReview.get());
        if (foodReviewRepository.existsById(foodReview.get().getId())) {
            food.updateScore(foodReviewRepository.findAverageScoreByFoodId(foodId));
        } else {
            food.updateScore(0);
        }

        return true;
    }

    public List<FoodReviewResponse> getAllUserReviews(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow();

        List<FoodReview> reviewList = foodReviewRepository.findAllByUserId(userId);

        return reviewList.stream().map(this::toFoodReviewResponse).toList();
    }


    private FoodReviewResponse toFoodReviewResponse(FoodReview foodReview) {
        return FoodReviewResponse.builder()
            .foodReviewId(foodReview.getId())
            .foodId(foodReview.getFood().getId())
            .userId(foodReview.getUser().getId())
            .title(foodReview.getTitle())
            .content(foodReview.getContent())
            .score(foodReview.getScore())
            .build();
    }
}
