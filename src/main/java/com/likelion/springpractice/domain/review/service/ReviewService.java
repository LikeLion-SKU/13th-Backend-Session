package com.likelion.springpractice.domain.review.service;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.review.dto.request.CreateReviewRequest;
import com.likelion.springpractice.domain.review.dto.request.UpdateReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.exception.ReviewErrorCode;
import com.likelion.springpractice.domain.review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.exception.CustomException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewMapper reviewMapper;
  private final FoodRepository foodRepository;

  // 후기 생성
  @Transactional
  public ReviewResponse createReview(CreateReviewRequest createReviewRequest) {
    log.info("[ReviewService] 후기 생성 시도: foodId = {}, content = {}, score = {}",
        createReviewRequest.getFoodId(), createReviewRequest.getReviewContent(),
        createReviewRequest.getReviewScore());

    // 유효성 검사
    if (createReviewRequest.getReviewContent() == null || createReviewRequest.getReviewContent()
        .isBlank()) {
      throw new CustomException(ReviewErrorCode.INVALID_REVIEW_CONTENT);
    }

    if (createReviewRequest.getReviewScore() < 0 || createReviewRequest.getReviewScore() > 5) {
      throw new CustomException(ReviewErrorCode.INVALID_REVIEW_SCORE);
    }

    Food food = foodRepository.findById(createReviewRequest.getFoodId())
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    Review review = Review.builder()
        .food(food)
        .content(createReviewRequest.getReviewContent())
        .score(createReviewRequest.getReviewScore())
        .build();

    reviewRepository.save(review);

    log.info("[서비스] 후기 생성 완료: id = {}, foodId = {}, score = {}",
        review.getReviewId(), review.getFood().getFoodId(), review.getScore());

    return reviewMapper.toReviewResponse(review);
  }

  // 후기 삭제
  @Transactional
  public void deleteReview(Long reviewId) {
    log.info("[ReviewService] 후기 삭제 시도: reviewId = {}", reviewId);

    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND));

    reviewRepository.delete(review);

    log.info("[ReviewService] 후기 삭제 완료: reviewId = {}", reviewId);
  }

  // 후기 수정
  @Transactional
  public ReviewResponse updateReview(Long reviewId, UpdateReviewRequest updateReviewRequest) {
    log.info("[ReviewService] 후기 수정 시도 : reviewId={}, newContent={}, newScore={}",
        reviewId, updateReviewRequest.getContent(), updateReviewRequest.getScore());

    if (updateReviewRequest.getContent() == null || updateReviewRequest.getContent().isBlank()) {
      throw new CustomException(ReviewErrorCode.INVALID_REVIEW_CONTENT);
    }

    if (updateReviewRequest.getScore() < 0 || updateReviewRequest.getScore() > 5) {
      throw new CustomException(ReviewErrorCode.INVALID_REVIEW_SCORE);
    }

    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> {
          log.warn("[ReviewService] 후기 수정 실패 - 존재하지 않음: reviewId={}", reviewId);
          return new IllegalArgumentException("후기를 찾을 수 없습니다");
        });

    review.update(updateReviewRequest.getContent(), updateReviewRequest.getScore());

    log.info("[ReviewService] 후기 수정 완료 : reviewId={}, content={}, score={}",
        review.getReviewId(), updateReviewRequest.getContent(), updateReviewRequest.getScore());

    return reviewMapper.toReviewResponse(review);
  }

  // 사용자별 후기 내역 조회
  @Transactional
  public List<ReviewResponse> getReviewsByUser(User user) {
    log.info("[ReviewService] 사용자별 좋아요 조회 시도");
    List<Review> reviewList = reviewRepository.findAllByUser(user);

    if (reviewList.isEmpty()) {
      log.warn("[LikeService] 좋아요 데이터가 존재하지 않음");
      throw new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND);
    }
    return reviewList.stream().map(
        review -> reviewMapper.toReviewResponse(review)).toList();
  }

  // 음식별 후기 내역 조회
  @Transactional
  public List<ReviewResponse> getReviewsByFood(Food food) {
    log.info("[ReviewService] 사용자별 좋아요 조회 시도");
    List<Review> reviewList = reviewRepository.findAllByFood(food);

    if (reviewList.isEmpty()) {
      log.warn("[LikeService] 좋아요 데이터가 존재하지 않음");
      throw new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND);
    }
    return reviewList.stream().map(
        review -> reviewMapper.toReviewResponse(review)).toList();
  }
}
