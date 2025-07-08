package com.likelion.springpractice.domain.user.controller;

import com.likelion.springpractice.domain.foodlike.dto.response.FoodLikeResponse;
import com.likelion.springpractice.domain.foodlike.service.FoodLikeService;
import com.likelion.springpractice.domain.foodreview.dto.response.FoodReviewResponse;
import com.likelion.springpractice.domain.foodreview.service.FoodReviewService;
import com.likelion.springpractice.domain.user.dto.request.SignUpRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateUserPasswordRequest;
import com.likelion.springpractice.domain.user.dto.request.UpdateUserRequest;
import com.likelion.springpractice.domain.user.dto.response.SignUpResponse;
import com.likelion.springpractice.domain.user.dto.response.UpdateUserResponse;
import com.likelion.springpractice.domain.user.service.UserService;
import com.likelion.springpractice.domain.userbadge.dto.response.UserBadgeResponse;
import com.likelion.springpractice.domain.userbadge.service.UserBadgeService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "User", description = "User 관리 API")
public class UserController {

    private final UserService userService;
    private final UserBadgeService userBadgeService;
    private final FoodReviewService foodReviewService;
    private final FoodLikeService foodLikeService;

    @Operation(summary = "회원가입 API", description = "사용자 회원가입을 위한 API", tags = {"[로그인X]회원가입"})
    @PostMapping("/users/sign-up")
    public ResponseEntity<BaseResponse<SignUpResponse>> signUp(
        @RequestBody @Valid SignUpRequest signUpRequest) {
        System.out.println(signUpRequest.getUsername() + ", " + signUpRequest.getPassword());
        SignUpResponse signUpResponse = userService.signUp(signUpRequest);

        return ResponseEntity.ok(BaseResponse.success("회원가입에 성공했습니다.", signUpResponse));
    }

    @Operation(summary = "사용자 정보 변경 API", description = "마이페이지에서 정보 변경을 눌렀을 때 요청되는 API", tags = {
        "[로그인O]정보 변경 관련"})
    @PutMapping("/my/information")
    public ResponseEntity<BaseResponse<UpdateUserResponse>> updateMyInformation(
        @RequestBody @Valid UpdateUserRequest updateUserRequest,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUser().getId();
        UpdateUserResponse response = userService.updateUserInformation(userId, updateUserRequest);

        return ResponseEntity.ok(BaseResponse.success("정보 변경에 성공했습니다.", response));
    }

    @Operation(summary = "사용자 비밀번호 변경 API", description = "마이페이지에서 비밀번호 변경을 눌렀을 때 요청되는 API", tags = {
        "[로그인O]정보 변경 관련"})
    @PutMapping("/my/password")
    public ResponseEntity<BaseResponse<Boolean>> updateMyPassword(
        @RequestBody @Valid UpdateUserPasswordRequest updateUserPasswordRequest,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUser().getId();

        Boolean response = userService.updateUserPassword(userId, updateUserPasswordRequest);

        return ResponseEntity.ok(BaseResponse.success("비밀번호 변경 성공", response));
    }

    @Operation(summary = "특정 사용자 보유 배찌 전체 조회", description = "사용자가 보유 배찌 페이지를 눌렀을 때 요청되는 API", tags = {
        "[로그인O]마이페이지 관련"})
    @GetMapping("/my/badges")
    public ResponseEntity<BaseResponse<List<UserBadgeResponse>>> getAllMyBadges(
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUser().getId();
        List<UserBadgeResponse> responses = userBadgeService.getAllUserBadges(userId);

        return ResponseEntity.ok(BaseResponse.success("내 배찌 목록 조회에 성공했습니다.", responses));
    }

    @Operation(summary = "특정 사용자 리뷰 전체 조회", description = "사용자가 작성 리뷰 페이지를 눌렀을 때 요청되는 API", tags = {
        "[로그인O]마이페이지 관련"})
    @GetMapping("/my/reviews")
    public ResponseEntity<BaseResponse<List<FoodReviewResponse>>> getAllMyReviews(
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUser().getId();
        List<FoodReviewResponse> responses = foodReviewService.getAllUserReviews(userId);

        return ResponseEntity.ok(BaseResponse.success("내 후기 목록 조회에 성공했습니다.", responses));
    }

    @Operation(summary = "특정 사용자 좋아요 전체 조회", description = "사용자가 좋아요한 음식 페이지를 눌렀을 때 요청되는 API", tags = {
        "[로그인O]마이페이지 관련"})
    @GetMapping("/my/likes")
    public ResponseEntity<BaseResponse<List<FoodLikeResponse>>> getAllMyLikes(
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUser().getId();
        List<FoodLikeResponse> responses = foodLikeService.getAllUserLikes(userId);

        return ResponseEntity.ok(BaseResponse.success("내 좋아요 목록 조회에 성공했습니다.", responses));
    }
}
