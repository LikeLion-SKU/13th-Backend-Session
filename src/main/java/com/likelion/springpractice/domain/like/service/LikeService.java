package com.likelion.springpractice.domain.like.service;


import com.likelion.springpractice.domain.food.dto.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

  private final LikeRepository likeRepository;
  private final FoodRepository foodRepository;
  private final UserRepository userRepository;


  @Transactional
  public void likeFood(Long foodId) {
    Long userId = 1L;
    log.info("[서비스] 음식 좋아요 시도 - foodId: {}, userId: {}", foodId, userId);

    User user = userRepository.findById(userId)
        .orElseThrow(() -> {
          log.warn("[서비스] 좋아요 실패 - 사용자 없음");
          return new CustomException(UserErrorCode.USER_NOT_FOUND);
        });

    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> {
          log.warn("[서비스] 좋아요 실패 - 음식 없음: foodId={}", foodId);
          return new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
        });

    Optional<Like> optionalLike = likeRepository.findByUserIdAndFoodId(user.getId(), food.getId());

    if (optionalLike.isEmpty()) {
      log.info("[서비스] 처음 좋아요 생성");
      Like like = Like.builder()
          .user(user)
          .food(food)
          .likeStatus(true)
          .build();
      likeRepository.save(like);

      food.increaseLikeCount(); // 좋아요 수 증가
    } else {
      Like like = optionalLike.get();
      if (!like.isLiked()) {
        log.info("[서비스] 기존 좋아요 false → true 토글");
        like.toggleLikeStatus();
        food.increaseLikeCount();
      } else {
        log.info("[서비스] 이미 좋아요 상태입니다. 중복 처리 방지");
      }
    }
  }

  @Transactional
  public void unlikeFood(Long foodId) {
    Long userId = 1L;
    log.info("[서비스] 음식 좋아요 삭제 시도 - foodId: {}, userId: {}", foodId, userId);

    Like like = likeRepository.findByUserIdAndFoodId(userId, foodId)
        .orElseThrow(() -> {
          log.warn("[서비스] 좋아요 삭제 실패 - 좋아요 데이터 없음");
          return new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
        });

    if (like.isLiked()) {
      like.toggleLikeStatus(); // false로 전환
      like.getFood().decreaseLikeCount(); // 좋아요 수 감소
      log.info("[서비스] 좋아요 상태 false로 변경, 좋아요 수 감소 완료");
    } else {
      log.info("[서비스] 이미 좋아요가 꺼진 상태입니다. 아무 작업 없음");
    }
  }


  @Transactional(readOnly = true)
  public List<FoodResponse> getLikedFoodsByUser() {
    Long userId = 1L;
    log.info("[서비스] 좋아요 누른 음식 목록 조회 시도 - userId: {}", userId);

    User user = userRepository.findById(1L)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    List<Like> likes = likeRepository.findAllByUserIdAndLikeStatusTrue(user.getId());

    log.info("[서비스] 좋아요 누른 음식 수: {}", likes.size());

    // Like → Food → FoodResponse
    return likes.stream()
        .map(like -> toFoodResponse(like.getFood()))
        .toList();
  }

  // Entity → DTO 변환 (FoodService와 동일한 방식)
  private FoodResponse toFoodResponse(Food food) {
    return FoodResponse.builder()
        .foodId(food.getId())
        .name(food.getName())
        .description(food.getDescription())
        .like_count(food.getLikeCount())
        .avg_rating(food.getAvgRating())
        .build();
  }
}
