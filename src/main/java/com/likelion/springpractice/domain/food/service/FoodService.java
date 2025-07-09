package com.likelion.springpractice.domain.food.service;

import com.likelion.springpractice.domain.food.dto.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
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
public class FoodService {


  //컨트롤러에선, 서비스 메소드를 호출!! 서비스에선, 레포지토리 메서드를 호출한다!!
  private final FoodRepository foodRepository;
  private final UserRepository userRepository;
  private final ReviewRepository reviewRepository;

  //음식 전체 조회
  @Transactional
  public List<FoodResponse> getAllFoods() {
    log.info("[서비스] 음식 전체 조회 시도");
    List<Food> foodList = foodRepository.findAll(); //findAll을 통해 모든 게시글 Entity를 가져옴!
    log.info("[서비스] 조회된 음식 수: {}", foodList.size());
    return foodList.stream().map(this::toFoodResponse)
        .toList(); //가져온 리스트를 PostResponse(DTO)로 하나하나 변환해야 하므로 stream().map 사용!
  }

  //게시글 단일 조회
  @Transactional
  public FoodResponse getFoodById(Long id) {
    log.info("[서비스] 음식 단일 조회 시도: id={}", id);
    Food food = foodRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 음식 조회 실패 - 존재하지 않음: id={}", id);
          return new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
        });
    log.info("[서비스] 음식 단일 조회 성공: id={}", id);
    return toFoodResponse(food);
  }

  //음식 인기순 조회
  @Transactional
  public List<FoodResponse> getAllFoodsByLikeCount() {
    List<Food> foodList = foodRepository.findAllByOrderByLikeCountDesc();
    return foodList.stream().map(this::toFoodResponse).toList();
  }

  //음식 맵기순 조회
  @Transactional
  public List<FoodResponse> getAllFoodsByAvgRatingDesc() {
    List<Food> foodList = foodRepository.findAllByOrderByAvgRatingDesc();
    return foodList.stream().map(this::toFoodResponse).toList();
  }

  //음식 덜맵기순 조회
  @Transactional
  public List<FoodResponse> getAllFoodsByAvgRatingAsc() {
    List<Food> foodList = foodRepository.findAllByOrderByAvgRatingAsc();
    return foodList.stream().map(this::toFoodResponse).toList();
  }


  //Entity를 DTO로 변환해주는 메소드
  private FoodResponse toFoodResponse(Food food) {
    return FoodResponse.builder().foodId(food.getId()).name(food.getName())
        .description(food.getDescription()).like_count(food.getLikeCount())
        .avg_rating(food.getAvgRating()).build();
  }

  //내가 리뷰 남긴 음식 조회하기!!
  @Transactional(readOnly = true)
  public List<FoodResponse> getFoodsReviewedByUser(User user) {
    log.info("[서비스] 내가 리뷰 남긴 음식 조회 시도 - userId: {}", user.getId());

    user = userRepository.findById(user.getId())
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    List<Food> foodList = reviewRepository.findDistinctFoodsByUserId(user.getId());

    log.info("[서비스] 내가 리뷰한 음식 수: {}", foodList.size());

    return foodList.stream().map(this::toFoodResponse).toList();
  }

}
