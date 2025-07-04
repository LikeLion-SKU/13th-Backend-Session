package com.likelion.springpractice.domain.food.service;

import com.likelion.springpractice.domain.food.dto.request.FoodCreateRequest;
import com.likelion.springpractice.domain.food.dto.response.FoodDetailResponse;
import com.likelion.springpractice.domain.food.dto.response.FoodSummaryResponse;
import com.likelion.springpractice.domain.food.dto.response.FoodWithLikeResponse;
import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.food.exception.FoodErrorCode;
import com.likelion.springpractice.domain.food.mapper.FoodMapper;
import com.likelion.springpractice.domain.food.repository.FoodRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodService {

  private final FoodRepository foodRepository;

  /**
   * 음식 목록을 조회하는 서비스 메서드.
   * <p>
   * 데이터베이스에서 모든 음식을 조회하고, 이를 {@link FoodSummaryResponse} DTO로 변환하여 반환한다.
   * </p>
   *
   * @return 음식 목록이 담긴 {@link List} 객체
   */
  public List<FoodSummaryResponse> getFoodList() {
    log.info("[서비스] 음식 목록 조회 시도");

    List<FoodSummaryResponse> foodList = foodRepository.findAll()
        .stream()
        .map(FoodMapper::toFoodSummaryResponse)
        .toList();

    log.info("[서비스] 음식 목록 조회 성공: 총 {}개 음식", foodList.size());
    return foodList;
  }

  /**
   * 음식 상세 정보를 조회하는 서비스 메서드.
   * <p>
   * 주어진 음식 ID로 데이터베이스에서 음식을 조회하고, 이를 {@link FoodDetailResponse} DTO로 변환하여 반환한다.
   * </p>
   *
   * @param id 음식 ID
   * @return 음식 상세 정보가 담긴 {@link FoodDetailResponse} 객체
   * @throws CustomException {@link FoodErrorCode#FOOD_NOT_FOUND} – 해당 ID의 음식이 존재하지 않는 경우 발생
   */
  public FoodDetailResponse getFoodDetail(Long id) {
    log.info("[서비스] 음식 상세 조회 시도: 음식 ID = {}", id);
    Food food = foodRepository.findById(id)
        .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));
    log.info("[서비스] 음식 상세 조회 성공: 음식 이름 = {}", food.getName());
    return FoodMapper.toFoodDetailResponse(food);
  }

  /**
   * 베스트 인기 순위 음식을 조회하는 서비스 메서드.
   * <p>
   * 데이터베이스에서 좋아요 수를 기준으로 정렬된 음식을 조회하고, 이를 {@link FoodWithLikeResponse} DTO로 변환하여 반환한다.
   * </p>
   *
   * @return 베스트 인기 순위 음식 목록이 담긴 {@link List} 객체
   */
  public List<FoodWithLikeResponse> getBestFoodList() {
    log.info("[서비스] 베스트 인기 순위 음식 조회 시도");

    List<FoodWithLikeResponse> bestFoodList = foodRepository.findAllOrderByLikeCountDesc()
        .stream()
        .map(food -> FoodMapper.toFoodWithLikeResponse(food, (long) food.getLikes().size()))
        .toList();

    log.info("[서비스] 베스트 인기 순위 음식 조회 성공: 총 {}개 음식", bestFoodList.size());
    return bestFoodList;
  }

  /**
   * 새로운 음식을 추가하는 서비스 메서드.
   * <p>
   * 주어진 {@link FoodCreateRequest} DTO를 기반으로 새로운 음식 엔티티를 생성하고, 이를 데이터베이스에 저장한다.
   * </p>
   *
   * @param foodRequest 음식 생성 요청 DTO
   */
  public void addFood(FoodCreateRequest foodRequest) {
    log.info("[서비스] 음식 추가 시도: 음식 이름 = {}", foodRequest.getName());

    // 음식 엔티티 생성 및 저장
    Food food = Food.builder()
        .name(foodRequest.getName())
        .description(foodRequest.getDescription())
        .build();

    foodRepository.save(food);

    log.info("[서비스] 음식 추가 성공: 음식 이름 = {}", food.getName());
  }
}
