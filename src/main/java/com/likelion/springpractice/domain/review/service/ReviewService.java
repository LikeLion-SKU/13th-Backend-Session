package com.likelion.springpractice.domain.review.service;

import com.likelion.springpractice.domain.badge.service.BadgeService;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.food.service.FoodService;
import com.likelion.springpractice.domain.review.dto.request.CreateReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.exception.ReviewErrorCode;
import com.likelion.springpractice.domain.review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;
  private final UserRepository userRepository;
  private final FoodRepository foodRepository;
  private final FoodService foodService;
  private final BadgeService badgeService;

  // 리뷰 작성
  @Transactional
  public ReviewResponse createReview(String username, CreateReviewRequest createReviewRequest) {

    // 사용자 존재 확인
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 음식 존재 확인
    Food food = foodRepository.findById(createReviewRequest.getFoodId())
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    // 사용자는 음식 하나 당 하나의 리뷰만 작성 가능

    // 작성한 리뷰 생성
    Review review = Review.builder()
        .content(createReviewRequest.getContent())
        .score(createReviewRequest.getScore())
        .user(user)
        .food(food)
        .build();

    Review savedReview = reviewRepository.save(review);

    // 음식 리뷰 수 증가
    food.increaseReviewNum();

    // 음식 평점 계산 후 업데이트
    double newRating = foodService.calculateRating(food.getFoodId());
    food.updateRating(newRating);

    // 특정 사용자 배찌 지급
    badgeService.getBadge(username);

    return reviewMapper.toReviewResponse(savedReview);
  }

  // 특정 음식 리뷰 조회
  public List<ReviewResponse> getReviews(Long foodId) {
    List<Review> reviewList = reviewRepository.findByFood_FoodId(foodId);

    if (reviewList.isEmpty()) {
      throw new CustomException(ReviewErrorCode.REVIEW_ERROR_CODE);
    }

    return reviewList.stream().map(reviewMapper::toReviewResponse).toList();
  }

  // 특정 음식 리뷰 삭제
  @Transactional
  public Boolean deleteReview(Long userId, Long foodId) {
    Review review = reviewRepository.findByUser_UserIdAndFood_FoodId(userId, foodId)
        .orElseThrow(() -> {
          throw new CustomException(ReviewErrorCode.REVIEW_ERROR_CODE);
        });

    reviewRepository.delete(review);
    return true;
  }

}
