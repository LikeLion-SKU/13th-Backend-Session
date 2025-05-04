package com.likelion.springpractice.domain.post.week05.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "PostResponse: 게시글 응답 DTO")
public class PostResponse { //클라이언트에 응답을 보낼 때 사용되는 DTO 클래스!!
  //따라서, 엔티티 객체를 받아, 클라이언트에 보낼 때, 보낼 수 있는 형태로 가공하는 데에 사용됨!!
  //이 과정은 보통 Service 계층에서 DTO로 변환하는 로직에서 이뤄짐! (Service -> Controller) (Entity -> DTO)

  @Schema(description = "게시글 ID", example = "1")
  private Long postId;

  @Schema(description = "게시글 제목", example = "5주차 세션")
  private String title;

  @Schema(description = "게시글 내용", example = "이번주 세션도 화이팅!")
  private String content;

  @Schema(description = "조회 수", example = "4")
  private Long views;
}
