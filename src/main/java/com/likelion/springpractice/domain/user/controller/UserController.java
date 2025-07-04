package com.likelion.springpractice.domain.user.controller;

import com.likelion.springpractice.domain.user.dto.request.PasswordUpdateRequest;
import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.service.UserService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
      @RequestBody @Valid SignUpRequest signUpRequest) { // 앞에 @Valid 어노테이션 붙이면 Request에서 설정한 유효성 검사가 자동으로 진행됨!!
    System.out.println(signUpRequest.getUsername() + ", " + signUpRequest.getPassword());
    SignUpResponse signUpResponse = userService.signUp(signUpRequest);
    return ResponseEntity.ok(BaseResponse.success("회원가입에 성공했습니다.", signUpResponse));
  }

  //비번 변경을 위한 API, 하다가 다른 기능들 구현하러감!!
  @Operation(summary = "비밀번호 변경 API", description = "비밀번호 변경을 위한 API")
  @PutMapping("/me/password")
  public ResponseEntity<BaseResponse<String>> updatePassword(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody PasswordUpdateRequest request
  ) {
    Long userId = userDetails.getUser().getId(); //User 엔티티 내부 ID
    userService.updatePassword(userId, request);
    return ResponseEntity.ok(BaseResponse.success("비밀번호 변경 성공"));
  }

}
