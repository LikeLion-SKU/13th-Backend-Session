package com.likelion.springpractice.domain.user.dto.request;

import com.likelion.springpractice.domain.user.entity.Nation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(title = "UserRequest DTO", description = "사용자 데이터 전송")
public class UserRequest {

  @Getter
  @Builder
  public static class SelfIntroRequest {

    String selfIntro;
  }

  @Getter
  @Builder
  public static class SettingsRequest {

    Nation nation;
    String nickName;
  }
}
