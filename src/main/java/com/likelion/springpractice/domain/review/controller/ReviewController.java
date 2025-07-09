package com.likelion.springpractice.domain.review.controller;


import com.likelion.springpractice.domain.food.dto.FoodResponse;
import com.likelion.springpractice.domain.food.service.FoodService;
import com.likelion.springpractice.domain.review.dto.request.CreateReviewRequest;
import com.likelion.springpractice.domain.review.dto.response.ReviewResponse;
import com.likelion.springpractice.domain.review.service.ReviewService;
import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//사용자의 요청을 받아서, 그에 맞는 서비스 로직을 호출하고, 결과를 응답으로 반환하는 역할!!
@RestController  //API요청을 처리하는 컨트롤러임을 명시!
@RequiredArgsConstructor
@RequestMapping("/api/sp")  //이 클래스에서 모든 API경로 앞에 공통적으로 붙을 주소 설정!!
@Tag(name = "Review", description = "리뷰 관련 API")
//스웨거용 어노테이션. 이 컨트롤러가 Post관련 API임을 명시! -> API 문서에서 분류됨
public class ReviewController {

  private final ReviewService reviewService;
  private final FoodService foodService;

  @Operation(summary = "음식에 달린 리뷰 전체 조회",  //Operation의 구성요소 중 하나로, API에 대한 한줄요약.
      description = "한 음식에 대한 전체 리뷰 조회 페이지로 이동될 때 요청되는 API") //상세설명
  @GetMapping("/foods/{foodId}/reviews")
  public ResponseEntity<BaseResponse<List<ReviewResponse>>> getAllReviewsByFood(
      @PathVariable Long foodId) {
    List<ReviewResponse> responses = reviewService.getAllReviewsByFoodId(foodId);
    return ResponseEntity.ok(BaseResponse.success("음식에 달린 리뷰 전체 조회 성공", responses));
  } //그 객체를 ResponseEntity.ok로 감싸서, 객체를 JSON으로 변환해 상태처리와 함께 반환한다!!

  @Operation(summary = "리뷰 단일 조회",
      description = "리뷰 페이지애서 특정 리뷰에 접근할 때 요청되는 API")
  @GetMapping("/reviews/{reviewId}")
  public ResponseEntity<BaseResponse<ReviewResponse>> getReviewById(
      @Parameter(description = "특정 리뷰 ID") @PathVariable Long reviewId) { //@PathVariable : URL 경로에서 {id}값을 받아옴!!
    ReviewResponse response = reviewService.getReviewById(reviewId);
    return ResponseEntity.ok(BaseResponse.success(reviewId + "번 리뷰 조회 성공", response));
  }

  //-> final키워드가 붙어있음! -> 불변 필드, 반드시 생성자에서 초기화가 필요하다!!
  //따라서, @RequiredArgsConstructor가 자동으로 생성자 코드를 만들어, PostService 빈을 자동으로 PostController에 주입해준다!
  @Operation(summary = "리뷰 생성", description = "개별 음식 페이지에서 리뷰 작성 후 생성 버튼을 눌렀을 때 요청되는 API")
  @PostMapping("/foods/{foodId}/reviews")
  public ResponseEntity<BaseResponse<ReviewResponse>> createReview(
      @Parameter(description = "리뷰 작성 내용")  //Swagger에서 파라미터의 의미를 설명함! 실제 동작엔 관계X
      @PathVariable Long foodId,
      @RequestBody @Valid CreateReviewRequest createReviewRequest,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) { //@RequestBody : JSON -> java객체로 변환!
    User user = userDetails.getUser();
    ReviewResponse response = reviewService.createReview(foodId, createReviewRequest, user);
    return ResponseEntity.ok(
        BaseResponse.success("리뷰 생성 성공", response));
  }


  @Operation(summary = "내가 리뷰 작성한 음식 조회", description = "마이페이지에서 내가 리뷰 남긴 음식들만 조회하는 API")
  @GetMapping("/reviews/my-foods")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getReviewedFoodsByMe(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    User user = userDetails.getUser();
    List<FoodResponse> responses = foodService.getFoodsReviewedByUser(user);
    return ResponseEntity.ok(BaseResponse.success("내가 리뷰 작성한 음식 조회 성공", responses));
  }

}
