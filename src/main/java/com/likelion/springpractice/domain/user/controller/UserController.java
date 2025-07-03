package com.likelion.springpractice.domain.user.controller;

import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.user.dto.request.ChangePasswordRequest;
import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateResponse;
import com.likelion.springpractice.domain.user.dto.response.UserResponse;
import com.likelion.springpractice.domain.user.service.UserService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name="User", description="User 관리 API")
public class UserController {

  private final UserService userService;

  @Operation(summary="회원가입 API", description ="사용자 회원가입을 위한 API")
  @PostMapping("/sign-up")
  public ResponseEntity<BaseResponse<SignUpResponse>> signup(
      @RequestBody @Valid SignUpRequest signUpRequest) {
    SignUpResponse signUpResponse = userService.signUp(signUpRequest);
    return ResponseEntity.ok(BaseResponse.success("회원가입에 성공했습니다.", signUpResponse));
  }

  @Operation(summary="사용자 정보 변경 API", description ="사용자 정보 변경을 위한 API")
  @PatchMapping("/updateUser")
  public ResponseEntity<BaseResponse<UpdateResponse>> updateUser(
      @RequestBody @Valid UpdateRequest updateRequest) {
    UpdateResponse updateResponse = userService.updateUser(updateRequest);
    return ResponseEntity.ok(BaseResponse.success("정보 변경에 성공했습니다.", updateResponse));
  }

  @Operation(summary="비밀번호 변경 API", description ="비밀번호 변경을 위한 API")
  @PatchMapping("/changePassword")
  public ResponseEntity<BaseResponse<UpdateResponse>> updatePassword(
      @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
    UpdateResponse updateResponse = userService.updatePassword(changePasswordRequest);
    return ResponseEntity.ok(BaseResponse.success("비밀번호 변경에 성공했습니다.", updateResponse));
  }


  @Operation(summary = "회원 단일 조회",
      description = "아이디를 주면 해당 회원 정보를 조회하는 API.")
  @GetMapping("/users/{id}")
  public ResponseEntity<BaseResponse<UserResponse>> getUserById(
      @Parameter(description = "특정 회원 ID", example = "abc@naver.com")
      @PathVariable String id) {
    UserResponse response = userService.getUserById(id);
    return ResponseEntity.ok(BaseResponse.success("회원 단일 조회 성공", response));
  }

  @Operation(summary = "회원 전체 조회",
      description = "전체 회원 정보를 조회하는 API.")
  @GetMapping("/users")
  public ResponseEntity<BaseResponse<List<UserResponse>>> getAllUsers() {
    List<UserResponse> response = userService.getAllUsers();
    return ResponseEntity.ok(BaseResponse.success("전체 회원 조회 성공", response));
  }
  

}
