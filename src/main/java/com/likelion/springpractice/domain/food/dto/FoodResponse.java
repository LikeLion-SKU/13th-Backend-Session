package com.likelion.springpractice.domain.food.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "FoodResponse: 음식 조회 응답 DTO")
public class FoodResponse { //클라이언트에 응답을 보낼 때 사용되는 DTO 클래스!!
  //따라서, 엔티티 객체를 받아, 클라이언트에 보낼 때, 보낼 수 있는 형태로 가공하는 데에 사용됨!!
  //이 과정은 보통 Service 계층에서 DTO로 변환하는 로직에서 이뤄짐! (Service -> Controller) (Entity -> DTO)

  @Schema(description = "음식 ID", example = "1")
  private Long foodId;

  @Schema(description = "음식 이름", example = "불닭볶음면")
  private String name;

  @Schema(description = "음식 설명", example = "한국의 매운 볶음식 라면입니다.")
  private String description;

  @Schema(description = "좋아요 수", example = "4")
  private Integer like_count;

  @Schema(description = "맵기 평점", example = "3.5")
  private Double avg_rating;


}
