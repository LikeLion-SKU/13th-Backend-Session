package com.likelion.springpractice.domain.food.controller;

import com.likelion.springpractice.domain.food.dto.FoodResponse;
import com.likelion.springpractice.domain.food.service.FoodService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//사용자의 요청을 받아서, 그에 맞는 서비스 로직을 호출하고, 결과를 응답으로 반환하는 역할!!
@RestController  //API요청을 처리하는 컨트롤러임을 명시!
@RequiredArgsConstructor
@RequestMapping("/api/sp")  //이 클래스에서 모든 API경로 앞에 공통적으로 붙을 주소 설정!!
@Tag(name = "Food", description = "음식 관련 API")
//스웨거용 어노테이션. 이 컨트롤러가 Post관련 API임을 명시! -> API 문서에서 분류됨
public class FoodController {

  private final FoodService foodService;

  @Operation(summary = "음식 전체 조회",  //Operation의 구성요소 중 하나로, API에 대한 한줄요약.
      description = "전체 음식 조회 페이지로 이동될 때 요청되는 API") //상세설명
  @GetMapping("/foods")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoods() {
    List<FoodResponse> responses = foodService.getAllFoods();
    return ResponseEntity.ok(BaseResponse.success("음식 전체 조회 성공", responses));
  } //그 객체를 ResponseEntity.ok로 감싸서, 객체를 JSON으로 변환해 상태처리와 함께 반환한다!!

  @Operation(summary = "음식 단일 조회",
      description = "음식 게시판 페이지애서 특정 음식에 접근할 때 요청되는 API")
  @GetMapping("/foods/{id}")
  public ResponseEntity<BaseResponse<FoodResponse>> getPostById(
      @Parameter(description = "특정 게시글 ID") @PathVariable Long id) { //@PathVariable : URL 경로에서 {id}값을 받아옴!!
    FoodResponse response = foodService.getFoodById(id);
    return ResponseEntity.ok(BaseResponse.success(id + "번 음식 조회 성공", response));
  }

  @Operation(summary = "음식 좋아요 많은 순 조회 (인기순 조회)", description = "음식판 페이지에서 좋아요 많은 순 버튼을 눌렀을 때 요청되는 API")
  @GetMapping("/foods/popular")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoodsSortedByLikeCount() {
    List<FoodResponse> responses = foodService.getAllFoodsByLikeCount();
    return ResponseEntity.ok(BaseResponse.success(responses));
  }

  @Operation(summary = "음식 맵기 평점 높은 순 조회", description = "음식판 페이지에서 맵기 평점 높은 순 버튼을 눌렀을 때 요청되는 API")
  @GetMapping("/foods/spicy/desc")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoodsSortedByAvgRatingDesc() {
    List<FoodResponse> responses = foodService.getAllFoodsByAvgRatingDesc();
    return ResponseEntity.ok(BaseResponse.success(responses));
  }

  @Operation(summary = "음식 맵기 평점 낮은 순 조회", description = "음식판 페이지에서 맵기 평점 낮은 순 버튼을 눌렀을 때 요청되는 API")
  @GetMapping("/foods/spicy/asc")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoodsSortedByAvgRatingAsc() {
    List<FoodResponse> responses = foodService.getAllFoodsByAvgRatingAsc();
    return ResponseEntity.ok(BaseResponse.success(responses));
  }


}
