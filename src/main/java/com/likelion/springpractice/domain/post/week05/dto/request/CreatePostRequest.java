package com.likelion.springpractice.domain.post.week05.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "CreatePostRequest: 게시글 생성 요청 DTO") //스웨서 문서화를 위한 어노테이션!
public class CreatePostRequest { //게시글 생성 요청 시, 클라이언트가 보낸 JSON 데이터를 담는 입력용 DTO야!!
  //클라이언트에서 POST요청으로 제목, 내용, 조회수 등을 전송하면, 컨트롤러가 그 JSON을 자동으로 이 클래스로 변환해 넘겨받음!!
  //컨트롤러가 JSON을 인자 @RequestBody CreatePostRequest createPostRequest 로 받음!!
  //여기서 @RequestBody의 역할이 클라이언트가 HTTP 요청의 body에 담아 보낸 JSON 데이터를
  //자바 객체(CreatePostRequest)로 자동 변환해주는 것!!

  @Schema(description = "게시글 제목", example = "1주차 세션???")
  private String title;

  @Schema(description = "게시글 내용", example = "내용내용내용내용")
  private String content;

  //따로 넣어줄 필요 없음!! 게시글 생성 시에는 0으로 자동 생성되어야 함!!
  //따라서, 서비스 코드에 Post.builder()에서, views(0)을 넣어줌!!
  @Schema(description = "조회수", example = "0")
  private Long views;
}
