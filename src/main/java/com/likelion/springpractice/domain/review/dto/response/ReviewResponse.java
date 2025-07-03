package com.likelion.springpractice.domain.review.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title="ReviewResponse DTO", description = "후기에 대한 데이터 반환")
public class ReviewResponse {
  @Schema(description = "후기 고유 ID", example = "1")
  private Long reviewId;

  @Schema(description = "작성자 아이디", example = "abc@naver.com")
  private String username;

  @Schema(description = "작성자 이름(별명)", example = "아이러브스파이시")
  private String name;
  
  @Schema(description = "음식 고유 ID", example = "1")
  private Long foodId;

  @Schema(description = "음식명", example = "떡볶이")
  private String foodName;

  @Schema(description = "매긴 음식 평점", example = "3.5")
  private Double rate;

  @Schema(description = "후기 내용", example = "맛있게 매워요.")
  private String content;

}
