package com.likelion.springpractice.domain.Like.service;

import com.likelion.springpractice.domain.Food.entity.Food;
import com.likelion.springpractice.domain.Food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.Food.repository.FoodRepository;
import com.likelion.springpractice.domain.Like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.Like.entity.Like;
import com.likelion.springpractice.domain.Like.exception.LikeErrorCode;
import com.likelion.springpractice.domain.Like.mapper.LikeMapper;
import com.likelion.springpractice.domain.Like.repository.LikeRepository;
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
public class LikeService {

  private final LikeRepository likeRepository;
  private final UserRepository userRepository;
  private final FoodRepository foodRepository;
  private final LikeMapper likeMapper;

  @Transactional
  public LikeResponse createLike(Long userId, Long foodId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    Like existingLike = likeRepository.findByUserIdAndFoodId(userId, foodId).orElse(null);
    // 이미 존재하는 Like일 때 에러 처리
    if (existingLike != null && !existingLike.isDeleted()) {
      throw new CustomException(LikeErrorCode.LIKE_ALREADY_EXISTS);
    }

    Like like = Like.builder()
        .user(user)
        .food(food)
        .isDeleted(false)
        .build();

    Like saved = likeRepository.save(like);
    food.increaseLikeCount();

    return likeMapper.toLikeResponse(saved);
  }

  @Transactional
  public LikeResponse updateLike(Long likeId) {

    // like는 메모리 내의 객체를 참조하고 있으므로 항상 최신 상태로 조회 가능
    Like like = likeRepository.findById(likeId)
        .orElseThrow(() -> new CustomException(LikeErrorCode.LIKE_NOT_FOUND));

    Food food = like.getFood();

    if (like.isDeleted()) {
      like.reLike();
      food.increaseLikeCount();
    } else {
      like.softDelete();
      food.decreaseLikeCount();
    }

    return likeMapper.toLikeResponse(like);
  }

  @Transactional(readOnly = true)
  public List<LikeResponse> getMyLikes(Long userId) {

    List<Like> myLikes = likeRepository.findAllByUserIdAndIsDeletedFalse(userId);
    return likeMapper.toLikeResponseList(myLikes);
  }
}
