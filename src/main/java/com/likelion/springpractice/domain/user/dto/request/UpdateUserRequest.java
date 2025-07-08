package com.likelion.springpractice.domain.user.dto.request;

import com.likelion.springpractice.domain.user.entity.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "UpdateUserRequest DTO", description = "사용자 정보 변경을 위한 데이터 전송")
public class UpdateUserRequest {

    @Schema(description = "사용자 이름", example = "sieon")
    private String username;

    @Schema(description = "자기소개", example = "안녕하세요. 금시언입니다.")
    private String introduce;

    @Schema(description = "사용자 국적", example = "KOREA")
    private Country country;

}
