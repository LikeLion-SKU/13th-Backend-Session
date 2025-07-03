package com.likelion.springpractice.domain.Like.service;

import static com.likelion.springpractice.global.exception.GlobalErrorCode.ALREADY_LIKED;
import static com.likelion.springpractice.global.exception.GlobalErrorCode.NOT_FOUND_FOOD;
import static com.likelion.springpractice.global.exception.GlobalErrorCode.NOT_FOUND_LIKE;

import com.likelion.springpractice.domain.Food.entity.Food;
import com.likelion.springpractice.domain.Food.repository.FoodRepository;
import com.likelion.springpractice.domain.Like.dto.response.LikeCountResponse;
import com.likelion.springpractice.domain.Like.dto.response.LikeFoodResponse;
import com.likelion.springpractice.domain.Like.entity.Like;
import com.likelion.springpractice.domain.Like.mapper.LikeMapper;
import com.likelion.springpractice.domain.Like.repository.LikeRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.exception.CustomException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final LikeRepository likeRepository;
  private final FoodRepository foodRepository;
  private final LikeMapper likeMapper;

  @Transactional
  public LikeCountResponse addLike(User user, Long foodId) {
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_FOOD));

    if (likeRepository.existsByUserAndFood(user, food)) {
      throw new CustomException(ALREADY_LIKED);
    }

    likeRepository.save(Like.builder()
        .user(user)
        .food(food)
        .build());

    long count = likeRepository.countByFood(food);
    return new LikeCountResponse(foodId, count, true);
  }

  @Transactional
  public LikeCountResponse cancelLike(User user, Long foodId) {
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_FOOD));

    Like like = likeRepository.findByUserAndFood(user, food)
        .orElseThrow(() -> new CustomException(NOT_FOUND_LIKE));

    likeRepository.delete(like);

    long count = likeRepository.countByFood(food);
    return new LikeCountResponse(foodId, count, false);
  }

  public LikeCountResponse getLikeCount(User user, Long foodId) {
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_FOOD));

    long count = likeRepository.countByFood(food);
    boolean liked = likeRepository.existsByUserAndFood(user, food);
    return new LikeCountResponse(foodId, count, liked);
  }

  public List<LikeFoodResponse> getMyLikes(User user) {
    return likeRepository.findByUser(user).stream()
        .map(likeMapper::toResponse)
        .collect(Collectors.toList());
  }
}
