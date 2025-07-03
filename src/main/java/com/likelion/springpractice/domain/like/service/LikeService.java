package com.likelion.springpractice.domain.like.service;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.like.entity.LikeId;
import com.likelion.springpractice.domain.like.exception.LikeErrorCode;
import com.likelion.springpractice.domain.like.mapper.LikeMapper;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeService {

  private final LikeRepository likeRepository;
  private final LikeMapper likeMapper;
  private final FoodRepository foodRepository;

  // 사용자별 좋아요 리스트 조회 -> for 마이페이지 좋아요 리스트
  @Transactional
  public List<LikeResponse> getLikesByUser(User user) {
    log.info("[LikeService] 사용자별 좋아요 조회 시도");
    List<Like> likeList = likeRepository.findAllByUser(user);

    if (likeList.isEmpty()) {
      log.warn("[LikeService] 좋아요 데이터가 존재하지 않음");
      throw new CustomException(LikeErrorCode.LIKE_NOT_FOUND);
    }
    return likeList.stream().map(
        like -> likeMapper.toLikeResponse(like)).toList();
  }

  // user 객체는 인증된 객체니까 굳이 userId 로 해서 DB 에서 받지 않고 인증 후 SecurityContextHolder의 인증 객체에서 꺼내쓰자 -> 개빡


  // 좋아요 생성 -> 왜 아니 복합키 괜히 쓴건가
  @Transactional
  public LikeResponse createLike(User user, Long foodId) {  // 인증 객체에서 갖다쓰기

    log.info("[LikeService] 좋아요 생성 시도: userId={}, foodId={}", user.getId(), foodId);

    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> {
          log.warn("[LikeService] 음식이 존재하지 않음 - 생성 실패");
          return new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
        });

    if (likeRepository.existsByUserAndFood(user, food)) {
      log.warn("[LikeService] 이미 좋아요 누른 상태 - 생성 실패");
      throw new CustomException(LikeErrorCode.LIKE_IS_EXIST);
    }

    Like like = Like.builder().id(new LikeId(user.getId(), food.getFoodId())).user(user).food(food)
        .build();
    likeRepository.save(like);
    log.info("[LikeService] 좋아요 생성 완료: userId={}, foodId={}", user.getId(), foodId);

    return likeMapper.toLikeResponse(like);
  }


  // 좋아요 삭제
  @Transactional
  public Boolean deleteLike(User user, Long foodId) {
    log.info("[LikeService] 좋아요 삭제 시도: userId={}, foodId={}", user.getId(), foodId);

    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> {
          log.warn("[LikeService] 음식이 존재하지 않음 - 삭제 실패");
          return new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
        });

    Like like = likeRepository.findByUserAndFood(user, food)
        .orElseThrow(() -> {
          log.warn("[LikeService] 좋아요가 존재하지 않음 - 삭제 실패");
          return new CustomException(LikeErrorCode.LIKE_NOT_FOUND);
        });

    likeRepository.delete(like);
    log.info("[LikeService] 좋아요 삭제 완료: userId={}, foodId={}", user.getId(), foodId);

    return true;
  }
}
