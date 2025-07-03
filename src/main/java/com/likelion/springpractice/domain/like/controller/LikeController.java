package com.likelion.springpractice.domain.like.controller;


import com.likelion.springpractice.domain.auth.exception.AuthErrorCode;
import com.likelion.springpractice.domain.food.dto.response.FoodResponse;
import com.likelion.springpractice.domain.like.dto.response.LikeResponse;
import com.likelion.springpractice.domain.like.service.LikeService;
import com.likelion.springpractice.global.exception.CustomException;
import com.likelion.springpractice.global.jwt.JwtProvider;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@Tag(name="Like", description="Like 관리 API")
public class LikeController {
  private final LikeService likeService;
  private final JwtProvider jwtProvider;

  // 해당 음식을 내가 좋아요 눌렀는지 조회
  @Operation(summary = "음식id로 해당 음식 좋아요 상태 조회",
      description = "음식id를 주면 해당 음식의 좋아요 상태를 조회하는 API.")
  @GetMapping("/food/{id}")
  public ResponseEntity<BaseResponse<LikeResponse>> getLikeStatus(
      @Parameter(description = "음식 id", example = "1")
      @PathVariable(required = true) Long id,
      HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN);  // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractSocialId(token);  // 토큰에서 로그인한 아이디 추출

    // 만약에 없으면 에러
    LikeResponse response = likeService.isLiked(id, userName);
    return ResponseEntity.ok(BaseResponse.success("좋아요 상태 조회 성공", response));
  }


  
  // 내가 좋아요 누른 음식 목록 조회
  @Operation(summary = "좋아요한 음식 리스트 조회",
      description = "좋아요한 음식 리스트 조회하는 API.")
  @GetMapping("")
  public ResponseEntity<BaseResponse<List<FoodResponse>>> getLikedFoods(HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN); // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractSocialId(token);  // 토큰에서 로그인한 아이디 추출

    // 만약에 없으면 에러

    List<FoodResponse> response = likeService.getLikes(userName);
    return ResponseEntity.ok(BaseResponse.success("좋아요 리스트 조회 성공", response));
  }


  // 좋아요 클릭 시 상태 전환하기
  @Operation(summary = "음식의 좋아요 상태 전환",
      description = "음식id를 주면 해당 음식의 좋아요 상태를 전환하는 API.")
  @PatchMapping("/food/{id}")
  public ResponseEntity<BaseResponse<LikeResponse>> toggleLike(
      @Parameter(description = "음식 id", example = "1")
      @PathVariable(required = true) Long id,
      HttpServletRequest request) {
    // 로그인한 아이디 가져오기 //
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new CustomException(AuthErrorCode.INVALID_ACCESS_TOKEN);  // 에러 처리 (원하는 예외로 바꿔도 됨)
    }

    String token = authHeader.substring(7).trim();   // "Bearer " 제거
    String userName = jwtProvider.extractSocialId(token);  // 토큰에서 로그인한 아이디 추출

    // 만약에 없으면 에러

    LikeResponse response = likeService.changeLike(id, userName);
    return ResponseEntity.ok(BaseResponse.success("좋아요 상태 조회 성공", response));
  }



}
