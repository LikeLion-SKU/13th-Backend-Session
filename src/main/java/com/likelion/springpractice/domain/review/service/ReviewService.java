package com.likelion.springpractice.domain.review.service;

import com.likelion.springpractice.domain.badge.service.BadgeService;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.review.dto.request.CreateReviewRequest;
import com.likelion.springpractice.domain.review.dto.request.UpdateReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.CreateReviewResponse;
import com.likelion.springpractice.domain.review.dto.response.DeleteReviewResponse;
import com.likelion.springpractice.domain.review.dto.response.UpdateReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.exception.ReviewErrorCode;
import com.likelion.springpractice.domain.review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

  private final UserRepository userRepository;
  private final FoodRepository foodRepository;
  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;
  private final BadgeService badgeService;

  @Transactional
  public CreateReviewResponse createReview(Long userId, Long foodId, CreateReviewRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    Review review = Review.builder()
        .user(user)
        .food(food)
        .spicyLevel(request.getSpicyLevel())
        .comment(request.getComment())
        .build();

    Review saved = reviewRepository.save(review);
    log.info("Review created: reviewId={}, userId={}, foodId={}", saved.getId(), userId, foodId);

    badgeService.issueBadgesIfEligible(user);
    int reviewCount = reviewRepository.countByUser(user);

    return reviewMapper.toCreateReviewResponse(saved, reviewCount);
  }

  @Transactional
  public UpdateReviewResponse updateReview(Long userId, Long reviewId, UpdateReviewRequest request) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND));

    if (!review.getUser().getId().equals(userId)) {
      throw new CustomException(ReviewErrorCode.UNAUTHORIZED_REVIEW_ACCESS);
    }

    review.updateReview(request.getSpicyLevel(), request.getComment());
    log.info("Review updated: reviewId={}, userId={}", reviewId, userId);

    return reviewMapper.toUpdateReviewResponse(review);
  }

  @Transactional
  public DeleteReviewResponse deleteReview(Long userId, Long reviewId) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND));

    if (!review.getUser().getId().equals(userId)) {
      throw new CustomException(ReviewErrorCode.UNAUTHORIZED_REVIEW_ACCESS);
    }

    reviewRepository.delete(review);
    log.info("Review deleted: reviewId={}, userId={}", reviewId, userId);

    return DeleteReviewResponse.builder()
        .deletedReviewId(reviewId)
        .build();
  }
}
