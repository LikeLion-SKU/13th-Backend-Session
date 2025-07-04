package com.likelion.springpractice.domain.review.service;

import com.likelion.springpractice.domain.badge.service.BadgeService;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.review.dto.request.ReviewCreateRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewDetailResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.exception.ReviewErrorCode;
import com.likelion.springpractice.domain.review.mapper.ReviewMapper;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

  private final BadgeService badgeService;
  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;
  private final FoodRepository foodRepository;

  /**
   * 사용자가 리뷰를 생성하는 서비스 메서드.
   * <p>
   * 주어진 음식 ID와 사용자 정보를 기반으로 새로운 리뷰를 생성하고, 이를 데이터베이스에 저장한다.
   * </p>
   *
   * @param foodId  음식 ID
   * @param user    리뷰를 작성하는 사용자
   * @param request 리뷰 생성 요청 DTO
   * @return 생성된 리뷰의 상세 정보가 담긴 {@link ReviewDetailResponse} 객체
   * @throws CustomException {@link FoodErrorCode#FOOD_NOT_FOUND} – 해당 음식이 존재하지 않는 경우 발생
   */
  @Transactional
  public ReviewDetailResponse createReview(Long foodId, User user, ReviewCreateRequest request) {
    log.info("[서비스] 리뷰 생성 시도: 사용자 ID = {}, 리뷰 내용 = {}", user.getUserId(), request.getContent());

    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    Review review = Review.builder()
        .user(user)
        .food(food)
        .content(request.getContent())
        .rating(request.getRating())
        .build();

    Review savedReview = reviewRepository.save(review);
    user.setReviewCount(user.getReviewCount() + 1);
    userRepository.save(user);

    badgeService.checkAndAssignBadge(user);

    log.info("[서비스] 리뷰 생성 성공: 리뷰 ID = {}", review.getReviewId());
    return ReviewMapper.toDetailResponse(savedReview);
  }

  /**
   * 사용자가 자신의 리뷰를 삭제하는 서비스 메서드.
   * <p>
   * 주어진 리뷰 ID와 사용자 정보를 기반으로 해당 리뷰를 삭제한다. 사용자가 작성한 리뷰만 삭제할 수 있다.
   * </p>
   *
   * @param reviewId 리뷰 ID
   * @param user     리뷰를 삭제하는 사용자
   * @throws CustomException {@link ReviewErrorCode#REVIEW_NOT_FOUND} – 해당 리뷰가 존재하지 않는 경우 발생
   *                         {@link ReviewErrorCode#REVIEW_DELETE_FORBIDDEN} – 사용자가 작성하지 않은 리뷰를
   *                         삭제하려는 경우 발생
   */
  public void deleteReview(Long reviewId, User user) {
    log.info("[서비스] 리뷰 삭제 시도: 리뷰 ID = {}, 사용자 ID = {}", reviewId, user.getUserId());

    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND));

    if (!review.getUser().getUserId().equals(user.getUserId())) {
      throw new CustomException(ReviewErrorCode.REVIEW_DELETE_FORBIDDEN);
    }

    reviewRepository.delete(review);
    log.info("[서비스] 리뷰 삭제 성공: 리뷰 ID = {}", reviewId);
  }


  /**
   * 특정 음식에 대한 리뷰 목록을 조회하는 서비스 메서드.
   * <p>
   * 주어진 음식 ID로 해당 음식에 대한 모든 리뷰를 조회하고, 이를 {@link ReviewDetailResponse} DTO로 변환하여 반환한다.
   * </p>
   *
   * @param foodId 음식 ID
   * @return 해당 음식에 대한 리뷰 목록이 담긴 {@link List} 객체
   * @throws CustomException {@link FoodErrorCode#FOOD_NOT_FOUND} – 해당 음식이 존재하지 않는 경우 발생
   */
  public List<ReviewDetailResponse> getReviewsByFoodId(Long foodId) {
    log.info("[서비스] 음식 ID = {}에 대한 리뷰 목록 조회 시도", foodId);

    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    List<Review> reviews = reviewRepository.findByFood(food);
    if (reviews.isEmpty()) {
      log.warn("[서비스] 음식 ID = {}에 대한 리뷰가 없습니다.", foodId);
      return List.of(); // 빈 리스트 반환
    }

    List<ReviewDetailResponse> responses = reviews.stream()
        .map(ReviewMapper::toDetailResponse)
        .collect(Collectors.toList());

    log.info("[서비스] 음식 ID = {}에 대한 리뷰 목록 조회 성공: 총 {}개 리뷰", foodId, responses.size());
    return responses;
  }


}
