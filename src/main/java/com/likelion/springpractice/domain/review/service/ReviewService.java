package com.likelion.springpractice.domain.review.service;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.review.dto.request.CreateReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.exception.ReviewErrorCode;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
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
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final FoodRepository foodRepository;
  private final UserRepository userRepository;

  //리뷰 전체 조회
  @Transactional
  public List<ReviewResponse> getAllReviewsByFoodId(Long foodId) {
    log.info("[서비스] 음식에 달린 리뷰 전체 조회 시도");
    List<Review> reviewList = reviewRepository.findByFoodId(
        foodId); //findAll을 통해 모든 게시글 Entity를 가져옴!
    log.info("[서비스] 조회된 리뷰 수: {}", reviewList.size());
    return reviewList.stream().map(this::toReviewResponse)
        .toList();
  }

  //리부 단일 조회
  @Transactional
  public ReviewResponse getReviewById(Long id) {
    log.info("[서비스] 리뷰 단일 조회 시도: id={}", id);
    Review review = reviewRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 리뷰 조회 실패 - 존재하지 않음: id={}", id);
          return new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND);
        });
    log.info("[서비스] 리뷰 단일 조회 성공: id={}", id);
    return toReviewResponse(review);
  }

  // 리뷰 생성
  // 클라이언트가 보낸 리뷰 생성 요청을 처리!!
  @Transactional
  public ReviewResponse createReview(Long foodId,
      CreateReviewRequest createReviewRequest) { //DTO를 인자로 받아,
    log.info("[서비스]게시글 생성 시도: rating= {}, comment={}", createReviewRequest.getRating(),
        createReviewRequest.getComment());

    // 로그인 유저 정보 필요하다면 다음 라인 사용 (User user = ... 로부터)
    // User user = userRepository.findByUsername(...) 또는 SecurityContext에서 꺼내오기

    User user = userRepository.findById(1L)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    if (createReviewRequest.getRating() < 1 || createReviewRequest.getRating() > 5) {
      throw new CustomException(ReviewErrorCode.INVALID_REVIEW_RATING);
    }

    if (createReviewRequest.getComment() == null || createReviewRequest.getComment().isBlank()) {
      throw new CustomException(ReviewErrorCode.INVALID_REVIEW_COMMENT);
    }

    if (createReviewRequest.getComment().length() > 500) {
      throw new CustomException(ReviewErrorCode.COMMENT_TOO_LONG);
    }

    Review review = Review.builder()  //DTO -> Entity 변환 후!
        .rating(createReviewRequest.getRating()) //프론트에서 보낸 "title" 값을 Post 객체의 필드로 넣는 과정
        .comment(createReviewRequest.getComment())
        .food(food)
        .user(user)
        .build();
    reviewRepository.save(review);  //Post 객체를 DB에 저장!!

    log.info("[서비스]게시글 생성 완료: id= {}, rating= {}, comment={}", review.getId(), review.getRating(),
        review.getComment());
    return toReviewResponse(review); //그리고, 저장된 결과(Entity)를 DTO로 변환해서 반환!!
  }

  //Entity를 DTO로 변환해주는 메소드
  private ReviewResponse toReviewResponse(Review review) {
    return ReviewResponse.builder().reviewId(review.getId())
        .comment(review.getComment()).rating(review.getRating())
        .username(review.getUser().getUsername()).createdAt(review.getCreatedAt())
        .build();
  }

}
