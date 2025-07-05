package com.likelion.springpractice.domain.mission.service;

import com.likelion.springpractice.domain.mission.entity.Food;
import com.likelion.springpractice.domain.mission.entity.Likes;
import com.likelion.springpractice.domain.mission.entity.User;
import com.likelion.springpractice.domain.mission.exception.FoodErrorCode;
import com.likelion.springpractice.domain.mission.exception.UserErrorCode;
import com.likelion.springpractice.domain.mission.repository.FoodRepository;
import com.likelion.springpractice.domain.mission.repository.LikesRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final LikesRepository likesRepository;
  private final FoodRepository foodRepository;

  //좋아요 누르기
  @Transactional
  public void toggleLike(User user, Long foodId) {
    if (user == null) {
      throw new CustomException(UserErrorCode.USER_NOT_FOUND);
    }

    Food food = foodRepository.findById(foodId).orElse(null);

    if (food == null) {
      throw new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
    }

    Likes like = likesRepository.findByUserIdAndFoodId(user.getId(), foodId);

    //한번 더 누를시 좋아요 취소
    if (like != null) {
      likesRepository.delete(like);
      food.decreaseLikeCount(); // 좋아요 취소 시 감소
    } else {
      Likes newLike = Likes.builder()
          .user(user)
          .food(food)
          .build();
      likesRepository.save(newLike);
      food.increaseLikeCount(); // 좋아요 추가 시 증가
    }

    foodRepository.save(food); // likeCount 반영
  }

  @Transactional(readOnly = true)
  public List<Food> getLikedFoods(User user) {
    if (user == null) {
      throw new CustomException(UserErrorCode.USER_NOT_FOUND);
    }
    List<Likes> likes = likesRepository.findAllByUserId(user.getId());
    return likes.stream().map(Likes::getFood).collect(Collectors.toList());
  }


}
