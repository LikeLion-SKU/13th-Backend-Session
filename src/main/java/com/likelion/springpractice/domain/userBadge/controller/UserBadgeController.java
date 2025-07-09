package com.likelion.springpractice.domain.userBadge.controller;

import com.likelion.springpractice.domain.user.entity.User;
import com.likelion.springpractice.domain.userBadge.dto.BadgeDetailResponse;
import com.likelion.springpractice.domain.userBadge.dto.BadgeSummaryResponse;
import com.likelion.springpractice.domain.userBadge.service.UserBadgeService;
import com.likelion.springpractice.global.response.BaseResponse;
import com.likelion.springpractice.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//사용자의 요청을 받아서, 그에 맞는 서비스 로직을 호출하고, 결과를 응답으로 반환하는 역할!!
@RestController  //API요청을 처리하는 컨트롤러임을 명시!
@RequiredArgsConstructor
@RequestMapping("/api/sp/users/me")  //이 클래스에서 모든 API경로 앞에 공통적으로 붙을 주소 설정!!
@Tag(name = "UserBadge", description = "획득한 배찌 관련 API")
//스웨거용 어노테이션. 이 컨트롤러가 Post관련 API임을 명시! -> API 문서에서 분류됨
public class UserBadgeController {

  private final UserBadgeService userBadgeService;

  @Operation(summary = "획득한 배지 최신순 조회", description = "마이페이지에서 획득한 배찌 리스트 버튼 눌렀을 때 요청되는 API")
  @GetMapping("/badges")
  public ResponseEntity<BaseResponse<List<BadgeSummaryResponse>>> getMyBadges(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    User user = userDetails.getUser();
    List<BadgeSummaryResponse> responses = userBadgeService.getMyBadges(user);
    return ResponseEntity.ok(BaseResponse.success(responses));
  }

  @GetMapping("/badges/{badgeId}")
  public ResponseEntity<BaseResponse<BadgeDetailResponse>> getMyBadgeDetail(
      @PathVariable Long badgeId,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    User user = userDetails.getUser();
    BadgeDetailResponse response = userBadgeService.getBadgeDetail(badgeId, user);
    return ResponseEntity.ok(BaseResponse.success(response));
  }
}
