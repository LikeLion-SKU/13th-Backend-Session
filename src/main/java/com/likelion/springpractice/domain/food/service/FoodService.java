package com.likelion.springpractice.domain.food.service;

import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.mapper.FoodMapper;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.domain.like.repository.LikeRepository;
import com.likelion.springpractice.domain.review.entity.Review;
import com.likelion.springpractice.domain.review.repository.ReviewRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodService {

  private final FoodRepository foodRepository;
  private final LikeRepository likeRepository;
  private final FoodMapper foodMapper;
  private final ReviewRepository reviewRepository;

  // 음식 전체 조회
  public List<FoodResponse> getAllFoods() {
    log.info("[FoodService] 전체 음식 조회 시도");
    List<Food> foodList = foodRepository.findAll();

    if (foodList.isEmpty()) {
      log.warn("[FoodService] 음식 데이터가 존재하지 않음");
      throw new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
    }
    List<FoodResponse> responseList = foodList.stream().map(food -> foodMapper.toFoodResponse(food,
        likeRepository.countByFood(food), calculateAvgRating(food))).toList();
    log.info("[FoodService] 조회된 음식 수 : {}", responseList.size());
    return responseList;
  }

  // 조회수와 매운맛 평점 등 상세 정보 조회
  public FoodResponse getFoodById(Long foodId) {
    log.info("[FoodService] 음식 상세 정보 조회 시도 : id={}", foodId);
    Food food = foodRepository.findById(foodId)
        .orElseThrow(() -> {
          log.warn("[FoodService] 음식 조회 실패 - 존재하지 않음: id={}", foodId);
          return new CustomException(FoodErrorCode.FOOD_NOT_FOUND);
        });
    long likeCount = likeRepository.countByFood(food);
    double avgRating = calculateAvgRating(food);

    FoodResponse response = foodMapper.toFoodResponse(food, likeCount, avgRating);
    log.info("[FoodService] 음식 단일 조회 성공 : id ={}", foodId);
    return response;
  }

  // 좋아요 수에 따른 인기순 조회
  public List<FoodResponse> getPopularFoods() {
    log.info("[FoodService] 인기 음식 조회 시도");
    List<Food> foodList = foodRepository.findAll();
    List<FoodResponse> responseList = foodList.stream().map(food -> foodMapper.toFoodResponse(food,
            likeRepository.countByFood(food), calculateAvgRating(food)))
        .sorted(Comparator.comparingLong(FoodResponse::getLikeCount).reversed())
        .toList();  // 정렬을 필수로 해야할까? 아니 어차피 그냥 인기순으 아니고 정확하게는 베스트 인기 순위인데..?
    log.info("[FoodService] 인기 음식 조회 완료");
    return responseList;
  }

  // 매운맛 평점 계싼 메소드 -> 하 진짜 오류 개많이 남  수정 매우매우 필요 !!!!!!!!!
  public double calculateAvgRating(Food food) {
    List<Review> reviewList = reviewRepository.findAllByFood(food);
    if (reviewList.isEmpty()) {
      return 0.0;
    }
    double result = reviewList.stream().mapToInt(r -> r.getScore()).average() // 수정 필요할듯
        .orElse(0.0);  // 오류때문에 수정함
    return result;
  }
}
