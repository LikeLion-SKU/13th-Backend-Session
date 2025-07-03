package com.likelion.springpractice.domain.like.service;


import com.likelion.springpractice.domain.auth.exception.AuthErrorCode;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.mapper.FoodMapper;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.like.mapper.LikeMapper;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.user.exception.UserErrorCode;
import com.likelion.springpractice.domain.user.repository.UserRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
  private final UserRepository userRepository;
  private final FoodRepository foodRepository;
  private final FoodMapper foodMapper;



  // 해당 음식에 좋아요를 했는지 조회
  @Transactional
  public LikeResponse isLiked(Long foodId, String userName) {
    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByUsername(userName)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 2. 음식 id로 해당 음식 있는지 확인
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));


    // 3. 좋아요 목록에 있는지 확인해서 있으면 state값을, 없으면 false를 저장해서 response
    Optional<Like> optionalLike = likeRepository.findByUserAndFood(user, food);

    boolean liked = optionalLike.map(Like::isState).orElse(false);

    return LikeResponse.builder()
        .food(foodMapper.toFoodResponse(food))
        .state(liked)
        .build();

  }



  // 회원이 좋아요한 음식 리스트 리턴
  @Transactional
  public List<FoodResponse> getLikes(String userName) {
    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByUsername(userName)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 2. 해당 아이디로 저장된 좋아요 목록 리스트 가져오기
    List<Like> likeList = likeRepository.findAllByUserAndStateIsTrue(user);

    // 3. 좋아요 목록에 저장된 음식 id 보고, 음식 리스트 가져오기
    List<Food> foodList = likeList.stream()
        .map(Like::getFood)
        .collect(Collectors.toList());

    // 4. 음식 리스트를 리턴
    return foodList.stream()
        .map(foodMapper::toFoodResponse)
        .collect(Collectors.toList());
  }


  // 좋아요 상태 전환(없으면 insert)
  @Transactional
  public LikeResponse changeLike(Long foodId, String userName) {
    // 1. 로그인 된 아이디로 회원이 있는지 확인. 없으면 에러
    User user = userRepository.findByUsername(userName)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 2. 음식 id로 해당 음식 있는지 확인
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));

    // 3. Like 테이블에 있는지 확인.
    Optional<Like> optionalLike = likeRepository.findByUserAndFood(user, food);

    if(optionalLike.isPresent()) {
      // 현재 좋아요에 있으면 state 값 전환해서 리턴
      Like like = optionalLike.get();
      boolean currentState = like.isState();
      like.setState(!currentState);   // 상태 토글
      likeRepository.save(like);      // 반영

      return LikeResponse.builder()
          .food(foodMapper.toFoodResponse(food))
          .state(like.isState())
          .build();
    }
    else{
      // 현재 좋아요 기록이 아예 없으면, like 테이블에 추가하고
      Like like = Like.builder()
          .state(true)
          .user(user)
          .food(food)
          .build();

      likeRepository.save(like);

      // state에 true 반영해서 리턴
      return LikeResponse.builder()
          .food(foodMapper.toFoodResponse(like.getFood()))
          .state(true)
          .build();

    }

  }

}
