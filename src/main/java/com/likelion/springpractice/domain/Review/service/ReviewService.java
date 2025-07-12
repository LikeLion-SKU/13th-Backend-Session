package com.likelion.springpractice.domain.Review.service;

import com.likelion.springpractice.domain.Badge.entity.Badge;
import com.likelion.springpractice.domain.Badge.repository.BadgeRepository;
import com.likelion.springpractice.domain.BadgeHistory.entity.BadgeHistory;
import com.likelion.springpractice.domain.BadgeHistory.repository.BadgeHistoryRepository;
import com.likelion.springpractice.domain.Food.entity.Food;
import com.likelion.springpractice.domain.Food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.Food.repository.FoodRepository;
import com.likelion.springpractice.domain.Review.dto.request.ReviewRequest;
import com.likelion.springpractice.domain.Review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.Review.entity.Review;
import com.likelion.springpractice.domain.Review.exception.ReviewErrorCode;
import com.likelion.springpractice.domain.Review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.Review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
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
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final FoodRepository foodRepository;
  private final BadgeRepository badgeRepository;
  private final BadgeHistoryRepository badgeHistoryRepository;
  private final ReviewMapper reviewMapper;

  @Transactional
  public ReviewResponse createReview(Long userId, Long foodId, ReviewRequest request) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    if (reviewRepository.existsByUserIdAndFoodIdAndIsDeletedFalse(userId, foodId)) {
      throw new CustomException(ReviewErrorCode.REVIEW_ALREADY_EXISTS);
    }

    Review review = Review.builder()
        .user(user)
        .food(food)
        .content(request.getContent())
        .spicyLevel(request.getSpicyLevel())
        .isDeleted(false)
        .build();

    reviewRepository.save(review);
    double newAvg = reviewRepository.calculateSpicyAverageForFood(foodId);
    food.updateSpicyLevelAvg(newAvg);

    long reviewCount = reviewRepository.countByUserId(userId);  // 후기 누적 수

    List<Badge> unlockableBadges = badgeRepository.findByUnlockCount(reviewCount);

    for (Badge badge : unlockableBadges) {
      boolean alreadyOwned = badgeHistoryRepository.existsByUserIdAndBadgeId(userId, badge.getId());

      if (!alreadyOwned) {
        badgeHistoryRepository.save(new BadgeHistory(user, badge));
      }
    }

    return reviewMapper.toReviewResponse(review);
  }

  @Transactional
  public ReviewResponse updateReview(Long userId, Long reviewId, ReviewRequest reviewRequest) {

    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND));

    // 자신이 작성한 글인지 확인하는 절차 필요!
    if (!review.getUser().getId().equals(userId)) {
      throw new CustomException(ReviewErrorCode.REVIEW_FORBIDDEN);
    }
    review.updateReview(reviewRequest.getContent(), reviewRequest.getSpicyLevel());

    double newAvg = reviewRepository.calculateSpicyAverageForFood(review.getFood().getId());
    review.getFood().updateSpicyLevelAvg(newAvg);

    return reviewMapper.toReviewResponse(review);
  }

  @Transactional
  public void deleteReview(Long userId, Long reviewId) {

    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND));

    if (!review.getUser().getId().equals(userId)) {
      throw new CustomException(ReviewErrorCode.REVIEW_FORBIDDEN);
    }

    review.softDelete();

    double newAvg = reviewRepository.calculateSpicyAverageForFood(review.getFood().getId());
    review.getFood().updateSpicyLevelAvg(newAvg);
  }

  @Transactional(readOnly = true)
  public List<ReviewResponse> getAllReviews(Long userId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    List<Review> reviewList = reviewRepository.findAllByUserIdAndIsDeletedFalse(userId);

    return reviewMapper.toReviewResponseList(reviewList);
  }
}
