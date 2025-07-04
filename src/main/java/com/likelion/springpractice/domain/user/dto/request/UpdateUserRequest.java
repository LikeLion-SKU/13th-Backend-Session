package com.likelion.springpractice.domain.user.dto.request;

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
@Schema(title = "UpdateUserRequest DTO", description = "사용자 정보수정을 위한 데이터 전송")
public class UpdateUserRequest {

  @NotBlank(message = "이름은 필수입니다.")
  @Schema(description = "사용자 이름", example = "심서현")
  private String name;

  @NotBlank(message = "비밀번호는 필수입니다.")
  @Schema(description = "비밀번호", example = "password1234!!")
  private String password;

  @NotBlank(message = "국적은 필수입니다.")
  @Schema(description = "국적", example = "KOREA")
  private String nationality;

}
