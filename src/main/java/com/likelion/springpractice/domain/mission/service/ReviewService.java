package com.likelion.springpractice.domain.mission.service;

import com.likelion.springpractice.domain.mission.dto.request.ReviewRequest;
import com.likelion.springpractice.domain.mission.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.mission.entity.Food;
import com.likelion.springpractice.domain.mission.entity.Review;
import com.likelion.springpractice.domain.mission.entity.User;
import com.likelion.springpractice.domain.mission.exception.FoodErrorCode;
import com.likelion.springpractice.domain.mission.exception.UserErrorCode;
import com.likelion.springpractice.domain.mission.repository.FoodRepository;
import com.likelion.springpractice.domain.mission.repository.ReviewRepository;
import com.likelion.springpractice.domain.mission.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;
  private final FoodRepository foodRepository;
  private final UserService userService; // 배지 업데이트 포함


  /**
   * 유저가 리뷰 작성 → User.reviewCount 증가 → UserService.updateBadge()로 배지 자동 반영 → Grade 테이블에 반영됨
   */

  //리뷰작성
  @Transactional
  public ReviewResponse createReview(User user, ReviewRequest request) {
    if (user == null) {
      throw new CustomException(UserErrorCode.USER_NOT_FOUND);
    }

    Food food = foodRepository.findById(request.getFoodId()).orElse(null);
    if (food == null) {
      throw new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
    }

    Review review = Review.builder()
        .user(user)
        .food(food)
        .content(request.getContent())
        .rating(request.getRating())
        .build();

    reviewRepository.save(review);

    // 후기 수 증가 + 배지 갱신
    user.increaseReviewCount();
    userService.updateBadge(user); // 배지 자동 갱신
    userRepository.save(user);

    return ReviewResponse.builder()
        .reviewId(review.getId())
        .content(review.getContent())
        .rating(review.getRating())
        .username(user.getUsername())
        .foodName(food.getName())
        .createdAt(review.getCreatedAt())
        .build();
  }


  //음식 ID를 통해 리뷰들 조회
  @Transactional(readOnly = true)
  public List<ReviewResponse> getReviewsByFoodId(Long foodId) {
    List<Review> list = reviewRepository.findAllByFoodId(foodId);
    return list.stream().map(this::toResponse).collect(Collectors.toList());
  }

  //나의 리뷰들 조회
  public List<ReviewResponse> getMyReviews(User user) {
    if (user == null) {
      throw new CustomException(UserErrorCode.USER_NOT_FOUND);
    }
    List<Review> list = reviewRepository.findAllByUserId(user.getId());
    return list.stream().map(this::toResponse).collect(Collectors.toList());
  }


  private ReviewResponse toResponse(Review review) {
    return ReviewResponse.builder()
        .reviewId(review.getId())
        .content(review.getContent())
        .rating(review.getRating())
        .username(review.getUser().getUsername())
        .foodName(review.getFood().getName())
        .createdAt(review.getCreatedAt())
        .build();
  }
}