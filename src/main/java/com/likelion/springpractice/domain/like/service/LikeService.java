package com.likelion.springpractice.domain.like.service;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.food.service.FoodService;
import com.likelion.springpractice.domain.like.dto.request.LikeRequest;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final LikeRepository likeRepository;
  private final FoodRepository foodRepository;
  private final UserRepository userRepository;
  private final FoodService foodService;

  // 좋아요 토글
  @Transactional
  public Boolean likeToggle(String username, Long foodId) {
    // 사용자 존재 확인
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 음식 존재 확인
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    Optional<Like> likeOpt = likeRepository.findByUserAndFood(user, food);

    if(likeOpt.isPresent()) { // 좋아요를 누른 user, food가 존재한다면
      Like like = likeOpt.get(); // 값 저장
      if(like.isStatus()) { // 이미 좋아요를 눌렀다면
        like.setStatus(false); // DB의 상태를 바꿈
        food.decreaseLikeNum(); // 음식 좋아요 수 감소
        return false; // 좋아요 x
      } else { // 좋아요를 누른 user, food가 존재하지만 좋아요 상태가 false인 경우
        like.setStatus(true); // DB 상태 true로 바꿈
        food.increaseLikeNum(); // 음식 좋아요 수 증가
        return true; // 좋아요 o
      }
    } else { // 좋아요를 처음 누르는 경우
      Like like = Like.builder()
          .user(user)
          .food(food)
          .status(true) // 좋아요 o
          .build();
      likeRepository.save(like);
      food.increaseLikeNum();

      return true;
    }
  }

}
