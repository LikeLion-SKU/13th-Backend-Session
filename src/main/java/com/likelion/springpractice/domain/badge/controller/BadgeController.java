package com.likelion.springpractice.domain.badge.controller;

import com.likelion.springpractice.domain.badge.dto.request.CreateBadgeRequest;
import com.likelion.springpractice.domain.badge.dto.response.BadgeResponse;
import com.likelion.springpractice.domain.badge.service.BadgeService;
import com.likelion.springpractice.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Badge", description = "배찌 관련 API")
public class BadgeController {

    private final BadgeService badgeService;

    @Operation(summary = "배찌 생성", description = "관리자가 배찌 생성 버튼을 눌렀을 때 요청되는 API")
    @PostMapping("/badges")
    public ResponseEntity<BaseResponse<BadgeResponse>> createBadge(
        @Parameter(description = "배찌 생성 내용") @RequestBody @Valid CreateBadgeRequest createBadgeRequest) {

        BadgeResponse response = badgeService.createBadge(createBadgeRequest);

        return ResponseEntity.ok(BaseResponse.success("배찌 생성 성공", response));
    }

    @Operation(summary = "배찌 전체 조회", description = "배찌 전체 조회를 했을 때 요청되는 API")
    @GetMapping("/badges")
    public ResponseEntity<BaseResponse<List<BadgeResponse>>> getAllBadges() {
        List<BadgeResponse> responses = badgeService.getAllBadges();
        return ResponseEntity.ok(BaseResponse.success("배찌 전체 조회 성공", responses));
    }

    @Operation(summary = "배찌 단일 조회", description = "특정 배찌에 접근할 때 요청되는 API")
    @GetMapping("/badges/{badgeId}")
    public ResponseEntity<BaseResponse<BadgeResponse>> getBadgeById(
        @Parameter(description = "특정 배찌 ID") @PathVariable(value = "badgeId") Long id) {
        BadgeResponse response = badgeService.getBadgeById(id);
        return ResponseEntity.ok(BaseResponse.success(id + "번 배찌 조회 성공", response));
    }

    @Operation(summary = "배찌 삭제", description = "관리자가 배찌 삭제 버튼을 눌렀을 때 요청되는 API")
    @DeleteMapping("/badges/{badgeId}")
    public ResponseEntity<BaseResponse<Boolean>> deleteBadge(
        @Parameter(description = "특정 배찌 ID") @PathVariable(value = "badgeId") Long id) {
        Boolean response = badgeService.deleteBadge(id);
        return ResponseEntity.ok(BaseResponse.success(id + "번 배찌 삭제 성공", response));
    }
}
