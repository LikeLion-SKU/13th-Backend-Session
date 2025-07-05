package com.likelion.springpractice.domain.mission.controller;

import com.likelion.springpractice.domain.mission.dto.request.IntroductionRequest;
import com.likelion.springpractice.domain.mission.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.mission.dto.response.IntroductionResponse;
import com.likelion.springpractice.domain.mission.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.mission.dto.response.UserSummaryResponse;
import com.likelion.springpractice.domain.mission.service.UserService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "User 관리 API")
public class UserController {

  private final UserService userService;

  @Operation(summary = "회원가입 API", description = "사용자 회원가입을 위한 API")
  @PostMapping("/sign-up")
  public ResponseEntity<BaseResponse<SignUpResponse>> signUp(
      @RequestBody @Valid SignUpRequest signUpRequest) {
    SignUpResponse signUpResponse = userService.signUp(signUpRequest);
    return ResponseEntity.ok(BaseResponse.success("회원가입에 성공했습니다.", signUpResponse));
  }

  @Operation(summary = "자기소개 등록 API", description = "자기 소개 내용 입력해주세요")
  @PostMapping("/introduction")
  public ResponseEntity<BaseResponse<IntroductionResponse>> registerIntroduction(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid IntroductionRequest request
  ) {
    IntroductionResponse introductionResponse = userService.registerIntroduction(
        userDetails.getUser(), request);
    return ResponseEntity.ok(BaseResponse.success("자기소개 등록을 성공하였습니다.", introductionResponse));
  }

  @Operation(summary = "자기소개 조회 API", description = "나의 자기소개 확인")
  @GetMapping("/introduction")
  public ResponseEntity<BaseResponse<IntroductionResponse>> getIntroduction(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    IntroductionResponse response = userService.getIntroduction(userDetails.getUser());
    return ResponseEntity.ok(BaseResponse.success("자기소개 조회 성공", response));
  }

  @Operation(summary = "자기소개 수정 API", description = "나의 자기소개 수정")
  @PatchMapping("/introduction")
  public ResponseEntity<BaseResponse<IntroductionResponse>> updateIntroduction(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid IntroductionRequest request
  ) {
    IntroductionResponse response = userService.updateIntroduction(userDetails.getUser(), request);
    return ResponseEntity.ok(BaseResponse.success("자기소개 수정 성공", response));
  }

  // 마이페이지 요약 조회 (후기 수, 좋아요 수, 배지 등)
  @Operation(summary = "마이페이지 조회 API", description = "나의 마이페이지(후기수, 좋아요 수, 배지 등)")
  @GetMapping("/summary")
  public ResponseEntity<BaseResponse<UserSummaryResponse>> getUserSummary(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    UserSummaryResponse summary = userService.getUserSummary(userDetails.getUser());
    return ResponseEntity.ok(BaseResponse.success("마이페이지 정보 조회 성공", summary));
  }
}
