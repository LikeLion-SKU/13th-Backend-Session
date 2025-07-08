package com.likelion.springpractice.domain.badge.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "BadgeResponse : 배찌 응답 DTO")
public class BadgeResponse {

    @Schema(description = "배찌 ID", example = "1")
    private Long badgeId;

    @Schema(description = "배찌 이름", example = "숟가락배찌")
    private String name;

    @Schema(description = "획득 조건", example = "후기 15개 올리면 숟가락 배찌 획득 가능")
    private String acquisition_condition;
}
