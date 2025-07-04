package com.likelion.springpractice.domain.user.dto.response;

import com.likelion.springpractice.domain.user.entity.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "SignUpResponse DTO", description = "사용자 회원가입에 대한 응답 반환")
public class SignUpResponse {

    @Schema(description = "회원가입된 사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "사용자 이메일", example = "keumsiun@gmail.com")
    private String email;

    @Schema(description = "비밀번호", example = "password123!")
    private String password;

    @Schema(description = "회원가입된 사용자 이름", example = "sieon")
    private String username;

    @Schema(description = "사용자 국적", example = "KOREA")
    private Country country;

    @Schema(description = "자기소개", example = "안녕하세요. 금시언입니다.")
    private String introduce;
}
