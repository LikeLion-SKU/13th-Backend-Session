package com.likelion.springpractice.domain.review.service;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.review.dto.request.ReviewCreateRequest;
import com.likelion.springpractice.domain.review.dto.request.ReviewUpdateRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewListResponse;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.exception.ReviewErrorCode;
import com.likelion.springpractice.domain.review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

  private final FoodRepository foodRepository;
  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;

  @Transactional
  public ReviewResponse createReview(Long foodId, ReviewCreateRequest dto, Long userId) {
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    Review review = Review.builder()
        .food(food)
        .userId(userId)
        .spicinessLevel(dto.getSpicinessLevel())
        .content(dto.getContent())
        .build();

    return reviewMapper.toResponse(reviewRepository.save(review));
  }

  public ReviewListResponse getReviewsByFoodId(Long foodId) {
    List<Review> list = reviewRepository.findByFoodId(foodId);
    return reviewMapper.toListResponse(list);
  }

  @Transactional
  public ReviewResponse updateReview(Long reviewId, ReviewUpdateRequest dto, Long userId) {
    Review review = reviewRepository.findByIdAndUserId(reviewId, userId)
        .orElseThrow(() -> new CustomException(ReviewErrorCode.UNAUTHORIZED_REVIEW_EDIT));

    // 현재 사용자 정보 가져오기
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean isAdmin = authentication.getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

    // 작성자 본인 또는 관리자만 수정 가능
    if (!review.getUserId().equals(userId) && !isAdmin) {
      throw new CustomException(ReviewErrorCode.UNAUTHORIZED_REVIEW_EDIT);
    }

    review.update(dto.getSpicinessLevel(), dto.getContent());
    return reviewMapper.toResponse(review);
  }

  @Transactional
  public Boolean deleteReview(Long reviewId, Long userId) {
    Review review = reviewRepository.findByIdAndUserId(reviewId, userId)
        .orElseThrow(() -> new CustomException(ReviewErrorCode.UNAUTHORIZED_REVIEW_EDIT));
    reviewRepository.delete(review);
    return true;
  }
}

