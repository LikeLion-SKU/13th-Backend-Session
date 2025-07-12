package com.likelion.springpractice.domain.user.controller;

import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.request.UserRequest.SelfIntroRequest;
import com.likelion.springpractice.domain.user.dto.request.UserRequest.SettingsRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.dto.response.UserResponse;
import com.likelion.springpractice.domain.user.service.UserService;
import com.likelion.springpractice.global.Response.BaseResponse;
import com.likelion.springpractice.global.Security.CustomUserDetails;
import com.likelion.springpractice.global.exception.CustomException;
import com.likelion.springpractice.global.exception.GlobalErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
  public ResponseEntity<BaseResponse<SignUpResponse>> signUp
      (@RequestBody @Valid SignUpRequest signUpRequest) {

    SignUpResponse signUpResponse = userService.signUp(signUpRequest);
    return ResponseEntity.ok(BaseResponse.success("회원가입에 성공했습니다.", signUpResponse));
  }

  @Operation(summary = "사용자 정보 조회 API", description = "설정 화면에서 사용자 정보 조회를 위한 API")
  @GetMapping("/")
  public ResponseEntity<BaseResponse<UserResponse>> getUserInfoById(
      @Parameter(hidden = true)
      // Spring Security에서 현재 인증된 사용자 정보를 가져올 수 있다!!
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    if (userDetails == null) {
      throw new CustomException(GlobalErrorCode.UNAUTHORIZED);
    }

    UserResponse userResponse = userService.getUserInfoById(userDetails.getUser().getId());

    return ResponseEntity.ok(BaseResponse.success("사용자 정보 조회에 성공했습니다.", userResponse));
  }

  // 사용자 정보 변경에 대한 API는 하나로 통일하는게 훨씬 효율적일 것 같음! 그냥 Request로 모든 값 받는걸로 하는건 어떤가.
  // 자기소개 변경은 별도의 API를 갖게 하고, 설정 변경(국적, 별명)도 따로 갖게하는 것이 확장성에 좋을 수도?
  // 결론: 프론트 뷰에 따라(전달받을 값에 따라) 따로 Request 구성해서 API 짜면 될 거 같다!!

  @Operation(summary = "사용자 자기소개 변경 API", description = "사용자 자기소개 변경을 위한 API")
  @PatchMapping("/self-intro")
  public ResponseEntity<BaseResponse<UserResponse>> updateSelfIntro(
      @Parameter(hidden = true)
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @Parameter(description = "사용자 자기소개 수정 내용")
      @RequestBody SelfIntroRequest selfIntroRequest) {

    if (userDetails == null) {
      throw new CustomException(GlobalErrorCode.UNAUTHORIZED);
    }

    UserResponse userResponse
        = userService.updateSelfIntro(userDetails.getUser().getId(), selfIntroRequest);

    return ResponseEntity.ok(BaseResponse.success("자기소개 변경 성공했습니다.", userResponse));
  }

  @Operation(summary = "사용자 설정 변경 API", description = "사용자 설정 변경을 위한 API")
  @PatchMapping("/settings")
  public ResponseEntity<BaseResponse<UserResponse>> updateSelfIntro(
      @Parameter(hidden = true)
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @Parameter(description = "사용자 설정 수정 내용")
      @RequestBody SettingsRequest settingsRequest) {
    
    if (userDetails == null) {
      throw new CustomException(GlobalErrorCode.UNAUTHORIZED);
    }

    UserResponse userResponse
        = userService.updateSettings(userDetails.getUser().getId(), settingsRequest);

    return ResponseEntity.ok(BaseResponse.success("사용자 설정 변경 성공했습니다.", userResponse));
  }
}
