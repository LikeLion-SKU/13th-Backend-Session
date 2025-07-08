package com.likelion.springpractice.domain.badge.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "CreateBadgeRequest DTO", description = "배찌 생성을 위한 데이터 전송")
public class CreateBadgeRequest {

    @NotBlank(message = "이름 항목은 필수입니다.")
    @Schema(description = "배찌 이름", example = "아기배찌")
    private String name;

    @NotBlank(message = "획득 조건 항목은 필수입니다.")
    @Schema(description = "획득 조건", example = "후기 5개 올리면 아기배찌 획득 가능")
    private String acquisition_condition;

}
