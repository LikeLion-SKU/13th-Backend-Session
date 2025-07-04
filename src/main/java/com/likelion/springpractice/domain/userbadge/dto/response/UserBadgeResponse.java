package com.likelion.springpractice.domain.userbadge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "UserBadgeResponse : 유저 배찌 응답 DTO")
public class UserBadgeResponse {

    @Schema(description = "배찌 아이디", example = "1")
    private Long badgeId;

    @Schema(description = "배찌 이름", example = "아기배찌")
    private String badgeName;
}
