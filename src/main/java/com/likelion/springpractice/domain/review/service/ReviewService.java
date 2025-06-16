package com.likelion.springpractice.domain.review.service;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.review.dto.request.CreateReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;
  private final UserRepository userRepository;
  private final FoodRepository foodRepository;

  // 리뷰 작성
  @Transactional
  public ReviewResponse createReview(String username, CreateReviewRequest createReviewRequest) {

    // 사용자 존재 확인
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 음식 존재 확인
    Food food = foodRepository.findById(createReviewRequest.getFoodId())
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    // 작성한 리뷰 생성
    Review review = Review.builder()
        .content(createReviewRequest.getContent())
        .score(createReviewRequest.getScore())
        .user(user)
        .food(food)
        .build();

    Review savedReview = reviewRepository.save(review);

    return reviewMapper.toReviewResponse(savedReview);
  }



}
