package com.likelion.springpractice.domain.user.dto.request;

import com.likelion.springpractice.domain.user.entity.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "SignUpRequest DTO", description = "사용자 회원가입을 위한 데이터 전송")
public class SignUpRequest {

    @NotBlank(message = "이메일 항목은 필수입니다.")
    @Schema(description = "사용자 이메일", example = "keumsiun@gmail.com")
    private String email;

    @NotBlank(message = "비밀번호 항목은 필수입니다.")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}$",
        message = "비밀번호는 최소 8자 이상, 숫자 및 특수문자를 포함해야 합니다.")
    @Schema(description = "비밀번호", example = "password123!")
    private String password;

    @NotBlank(message = "사용자 아이디 항목은 필수입니다.")
    @Schema(description = "사용자 아이디", example = "sieon")
    private String username;

    @Schema(description = "사용자 국적", example = "KOREA")
    private Country country;

    @Schema(description = "자기소개", example = "안녕하세요. 금시언입니다.")
    private String introduce;
}
