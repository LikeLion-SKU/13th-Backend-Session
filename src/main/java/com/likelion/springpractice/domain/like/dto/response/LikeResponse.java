package com.likelion.springpractice.domain.like.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "LikeResponse DTO", description = "좋아요 리스트 변환 및")
public class LikeResponse {

  @Schema(description = "사용자 고유 번호", example = "1")
  private Long userId;

  @Schema(description = "음식 고유 id", example = "1")
  private Long foodId;

  // 좋아요 리스트 반환할때 어떤 음식에 좋아요를 눌렀는지 알기 위해서 음식이름과 음식 설명 필드를 추가
  @Schema(description = "음식 이름", example = "신전떡볶이")
  private String foodName;

  @Schema(description = "음식 설명", example = "매콤한 카레 맛 떡볶이")
  private String foodDescription;
}
