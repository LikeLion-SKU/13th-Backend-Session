package com.likelion.springpractice.domain.food.controller;


import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.food.dto.response.SearchResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/foods")
@Tag(name="Food", description="Food 관리 API")
public class FoodController {

  private final FoodService foodService;

  // 스웨거 확인용 //
  @Operation(summary = "음식명으로 음식 단일 조회(확인용)",
      description = "음식명을 주면 해당 음식 정보를 조회하는 API.")
  @GetMapping("/name/{name}")
  public ResponseEntity<BaseResponse<FoodResponse>> getFoodByName(
      @Parameter(description = "특정 음식명", example = "떡볶이")
      @PathVariable String name) {
    FoodResponse response = foodService.getFoodByName(name);
    return ResponseEntity.ok(BaseResponse.success("음식 단일 조회 성공", response));
  }

  @Operation(summary = "음식 전체 조회(확인용)",
      description = "전체 음식 정보를 조회하는 API.")
  @GetMapping("")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getAllFoods(
      @Parameter(description = "정렬 기준 (예: rate, name) | null 가능", example = "rate")
      @RequestParam(required = false) String sort
  ) {
    List<FoodResponse> response = foodService.getAllFoods(sort);
    return ResponseEntity.ok(BaseResponse.success("전체 음식 조회 성공", response));
  }


  // 검색 페이지, 마이페이지 => 음식 상세 페이지로 접근할 때 사용
  @Operation(summary = "음식 id로 상세정보 조회(음식 상세 페이지 접근용)",
      description = "음식 고유 id를 주면 해당 음식 정보를 조회하는 API.")
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<FoodResponse>> getFoodById(
      @Parameter(description = "특정 음식 ID", example = "1")
      @PathVariable Long id) {
    FoodResponse response = foodService.getFoodById(id);
    return ResponseEntity.ok(BaseResponse.success("음식 단일 상세 조회 성공", response));
  }


  // 음식 검색 시, 검색 페이지에 해당하는 음식들 보여줄 때 사용.
  @Operation(summary = "검색한 음식 리스트 조회(검색용)",
      description = "검색어와 일치하는 음식 정보 리스트를 조회하는 API.")
  @GetMapping("/search")
  public ResponseEntity<BaseResponse<List<SearchResponse>>> searchFoods(
      @Parameter(description = "검색어", example = "떡볶이")
      @RequestParam(required = false) String keyword,

      @Parameter(description = "정렬 기준 (예: rate, name) | null 가능", example = "rate")
      @RequestParam(required = false) String sort ) {
    List<SearchResponse> response = foodService.getAllFoods(keyword, sort);
    return ResponseEntity.ok(BaseResponse.success("검색어와 일치하는 음식 리스트 조회 성공", response));
  }




}
