package com.likelion.springpractice.domain.like.service;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.like.exception.LikeErrorCode;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.exception.CustomException;
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

  /**
   * 음식에 대한 좋아요를 등록하는 서비스 메서드.
   * <p>
   * 주어진 음식 ID와 사용자 정보를 기반으로 좋아요를 등록하고, 성공 시 해당 음식의 좋아요 개수를 반환한다.
   * </p>
   *
   * @param foodId 음식 ID
   * @param user   사용자 정보
   * @return 좋아요 등록 결과와 현재 좋아요 개수를 포함한 {@link LikeResponse} 객체
   * @throws CustomException {@link FoodErrorCode#FOOD_NOT_FOUND} – 해당 ID의 음식이 존재하지 않는 경우 발생
   *                         {@link LikeErrorCode#ALREADY_LIKED} – 이미 좋아요를 누른 경우 발생
   */
  @Transactional
  public LikeResponse likeFood(Long foodId, User user) {
    log.info("[서비스] 음식 좋아요 등록 시도: foodId={}, userName={}", foodId, user.getUsername());
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    if (likeRepository.existsByUserAndFood(user, food)) {
      log.warn("[서비스] 이미 좋아요를 누른 음식: {}", foodId);
      throw new CustomException(LikeErrorCode.ALREADY_LIKED);
    }

    Like like = Like.builder()
        .user(user)
        .food(food)
        .build();
    likeRepository.save(like);

    long likeCount = likeRepository.countByFood(food);
    log.info("[서비스] 음식 좋아요 등록 성공: foodId={}, likeCount={}", foodId, likeCount);
    return new LikeResponse(foodId, true, likeCount);
  }

  /**
   * 음식에 대한 좋아요를 취소하는 서비스 메서드.
   * <p>
   * 주어진 음식 ID와 사용자 정보를 기반으로 좋아요를 취소하고, 성공 시 해당 음식의 좋아요 개수를 반환한다.
   * </p>
   *
   * @param foodId 음식 ID
   * @param user   사용자 정보
   * @return 좋아요 취소 결과와 현재 좋아요 개수를 포함한 {@link LikeResponse} 객체
   * @throws CustomException {@link FoodErrorCode#FOOD_NOT_FOUND} – 해당 ID의 음식이 존재하지 않는 경우 발생
   *                         {@link LikeErrorCode#LIKE_NOT_FOUND} – 좋아요가 존재하지 않는 경우 발생
   */
  @Transactional
  public LikeResponse unlikeFood(Long foodId, User user) {
    log.info("[서비스] 음식 좋아요 취소 시도: foodId={}, userName={}", foodId, user.getUsername());
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    Like like = likeRepository.findByUserAndFood(user, food)
        .orElseThrow(() -> new CustomException(LikeErrorCode.LIKE_NOT_FOUND));

    likeRepository.delete(like);

    long likeCount = likeRepository.countByFood(food);
    log.info("[서비스] 음식 좋아요 취소 성공: foodId={}, likeCount={}", foodId, likeCount);
    return new LikeResponse(foodId, false, likeCount);
  }


  /**
   * 사용자가 특정 음식에 대해 좋아요를 눌렀는지 여부와 총 좋아요 개수를 조회하는 서비스 메서드.
   * <p>
   * 주어진 음식 ID와 사용자 정보를 기반으로 좋아요 여부를 확인하고, 해당 음식의 총 좋아요 개수를 반환한다.
   * </p>
   *
   * @param foodId 음식 ID
   * @param user   사용자 정보
   * @return 좋아요 여부와 총 좋아요 개수를 포함한 {@link LikeResponse} 객체
   * @throws CustomException {@link FoodErrorCode#FOOD_NOT_FOUND} – 해당 ID의 음식이 존재하지 않는 경우 발생
   */
  @Transactional(readOnly = true)
  public LikeResponse getLikeByUser(Long foodId, User user) {
    log.info("[서비스] 음식 좋아요 여부 확인 시도: foodId={}, userName={}", foodId, user.getUsername());
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    boolean liked = likeRepository.existsByUserAndFood(user, food);
    long likeCount = likeRepository.countByFood(food);

    log.info("[서비스] 음식 좋아요 여부 확인 성공: foodId={}, isLiked={}, likeCount={}", foodId, liked,
        likeCount);
    return new LikeResponse(foodId, liked, likeCount);
  }
}