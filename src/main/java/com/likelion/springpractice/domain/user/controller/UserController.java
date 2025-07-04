package com.likelion.springpractice.domain.user.controller;

import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateEmailRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateLanguageRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdatePasswordRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateUsernameRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateEmailResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateLanguageResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdatePasswordResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateUsernameResponse;
import com.likelion.springpractice.domain.user.service.UserService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  @PatchMapping("/update/email")
  @Operation(summary = "이메일 변경", description = "로그인 이메일을 변경합니다.")
  public ResponseEntity<BaseResponse<UpdateEmailResponse>> updateEmail(
      @RequestBody @Valid UpdateEmailRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    UpdateEmailResponse response = userService.updateEmail(userDetails.getUser().getId(), request);
    return ResponseEntity.ok(BaseResponse.success("이메일이 변경되었습니다.", response));
  }

  @PatchMapping("/update/username")
  @Operation(summary = "이름 변경", description = "사용자 이름을 변경합니다.")
  public ResponseEntity<BaseResponse<UpdateUsernameResponse>> updateUsername(
      @RequestBody @Valid UpdateUsernameRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    UpdateUsernameResponse response = userService.updateUsername(userDetails.getUser().getId(), request);
    return ResponseEntity.ok(BaseResponse.success("이름이 변경되었습니다.", response));
  }

  @PatchMapping("/update/language")
  @Operation(summary = "국적 변경", description = "사용자의 국적을 변경합니다.")
  public ResponseEntity<BaseResponse<UpdateLanguageResponse>> updateLanguage(
      @RequestBody @Valid UpdateLanguageRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    UpdateLanguageResponse response = userService.updateLanguage(userDetails.getUser().getId(), request);
    return ResponseEntity.ok(BaseResponse.success("국적이 변경되었습니다.", response));
  }

  @PatchMapping("/update/password")
  @Operation(summary = "비밀번호 변경", description = "사용자의 비밀번호를 변경합니다.")
  public ResponseEntity<BaseResponse<UpdatePasswordResponse>> updatePassword(
      @RequestBody @Valid UpdatePasswordRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    UpdatePasswordResponse response = userService.updatePassword(userDetails.getUser().getId(), request);
    return ResponseEntity.ok(BaseResponse.success("비밀번호가 변경되었습니다.", response));
  }
}
