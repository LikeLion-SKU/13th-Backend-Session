package com.likelion.springpractice.domain.user.controller;

import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateUserRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.dto.response.UserResponse;
import com.likelion.springpractice.domain.user.entity.User;
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
      @RequestBody @Valid SignUpRequest signUpRequest) {
    System.out.println(signUpRequest.getEmail() + ", "
        + signUpRequest.getPassword());  // 사실 비번은 보안 때문에 로깅 안하는게 좋은듯
    SignUpResponse signUpResponse = userService.signUp(signUpRequest);
    return ResponseEntity.ok(BaseResponse.success("회원가입에 성공했습니다.", signUpResponse));
  }

  @Operation(summary = "회원 정보 수정 API", description = "사용자 정보수정을 위한 API")
  @PutMapping("/update")
  public ResponseEntity<BaseResponse<UserResponse>> updateUserInfo(
      @AuthenticationPrincipal CustomUserDetails userDetails,
      @RequestBody @Valid UpdateUserRequest request
  ) {
    User updated = userService.updateUser(userDetails.getUser(), request);

    UserResponse response = UserResponse.builder()
        .name(updated.getUsername())
        .nationality(updated.getNationality())
        .build();

    return ResponseEntity.ok(BaseResponse.success("회원 정보 수정 완료", response));
  }

}
