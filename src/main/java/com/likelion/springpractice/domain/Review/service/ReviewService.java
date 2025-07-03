package com.likelion.springpractice.domain.Review.service;

import static com.likelion.springpractice.global.exception.GlobalErrorCode.REVIEW_FORBIDDEN;
import static com.likelion.springpractice.global.exception.GlobalErrorCode.REVIEW_NOT_FOUND;

import com.likelion.springpractice.domain.Food.entity.Food;
import com.likelion.springpractice.domain.Food.repository.FoodRepository;
import com.likelion.springpractice.domain.Review.dto.request.ReviewRequest;
import com.likelion.springpractice.domain.Review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.Review.entity.Review;
import com.likelion.springpractice.domain.Review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.Review.repository.ReviewRepository;
import com.likelion.springpractice.domain.badge.service.BadgeService;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.exception.CustomException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final FoodRepository foodRepository;
  private final ReviewMapper reviewMapper;
  private final BadgeService badgeService;

  @Transactional
  public ReviewResponse writeReview(User user, ReviewRequest request) {
    Food food = foodRepository.findById(request.getFoodId())
        .orElseThrow(() -> new IllegalArgumentException("음식이 존재하지 않습니다."));

    Review review = Review.builder()
        .food(food)
        .user(user)
        .content(request.getContent())
        .spiceRating(request.getSpiceRating())
        .build();

    Review saved = reviewRepository.save(review);

    badgeService.checkAndAssignBadge(user); // ✅ 후기 작성 후 뱃지 체크

    return reviewMapper.toResponse(saved);
  }

  public List<ReviewResponse> getReviewsByFood(Long foodId) {
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new IllegalArgumentException("음식이 존재하지 않습니다."));

    return reviewRepository.findByFood(food).stream()
        .map(reviewMapper::toResponse)
        .toList();
  }

  @Transactional
  public void deleteReview(User user, Long reviewId) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));  // 리뷰 없음

    if (!review.getUser().getId().equals(user.getId())) {
      throw new CustomException(REVIEW_FORBIDDEN);  // 권한 없음
    }

    reviewRepository.delete(review);
    badgeService.recalculateBadges(user);
  }
}
