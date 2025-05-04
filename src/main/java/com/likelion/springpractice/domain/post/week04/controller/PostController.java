package com.likelion.springpractice.domain.post.week04.controller;


import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.post.week05.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//사용자의 요청을 받아서, 그에 맞는 서비스 로직을 호출하고, 결과를 응답으로 반환하는 역할!!
@RestController  //API요청을 처리하는 컨트롤러임을 명시!
@RequiredArgsConstructor
@RequestMapping("/api/vi")  //이 클래스에서 모든 API경로 앞에 공통적으로 붙을 주소 설정!!
@Tag(name = "Post", description = "게시글 관련 API")
//스웨거용 어노테이션. 이 컨트롤러가 Post관련 API임을 명시! -> API 문서에서 분류됨
public class PostController {

  private final PostService postService; //비지니스 로직 처리를 위한 PostService 필드 선언!
  //-> final키워드가 붙어있음! -> 불변 필드, 반드시 생성자에서 초기화가 필요하다!!
  //따라서, @RequiredArgsConstructor가 자동으로 생성자 코드를 만들어, PostService 빈을 자동으로 PostController에 주입해준다!

  //Operatoin 어노테이션은, 해당 API메서드에 관한 스웨거 문서 설명을 추가함!!
  @Operation(summary = "게시글 생성",  //각 API 엔드포인트에 대한 설명, 요약, 응답정보 등을 문서화해주는 Operation
      description = "게시판 페이지에서 게시글 작성 후 생성 버튼을 눌렀을 때 요청되는 API")
  @PostMapping("/posts") // HTTP POST 요청을 "/api/vi/posts" URL로 받을 때 실행됨.
  public ResponseEntity<PostResponse> createPost(
      @Parameter(description = "게시글 작성 내용")  //Swagger에서 파라미터의 의미를 설명함! 실제 동작엔 관계X
      @RequestBody CreatePostRequest createPostRequest) { //@RequestBody : JSON -> java객체로 변환!
    return ResponseEntity.ok( //HTTP상태 코드를 결정! body에 PostResponse객체를 담아서 반환함!
        postService.createPost(createPostRequest)); //비지니스 로직 처리하는 PosstService의 메서드를 호출함!
  }

  @Operation(summary = "게시글 전체 조회",  //Operation의 구성요소 중 하나로, API에 대한 한줄요약.
      description = "게시판 페이지로 이동될 때 요청되는 API") //상세설명
  @GetMapping("/posts")
  public ResponseEntity<List<PostResponse>> getAllPosts() {
    return ResponseEntity.ok(
        postService.getAllPosts()); //getAllPost()가, DB에서 게시글을 찾고, 그것을 PostResponse DTO객체로 변환해 반환하면,
  } //그 객체를 ResponseEntity.ok로 감싸서, 객체를 JSON으로 변환해 상태처리와 함께 반환한다!!

  @Operation(summary = "게시글 단일 조회",
      description = "게시판 페이지애서 특정 게시글에 접근할 때 요청되는 API")
  @GetMapping("/posts/{id}")
  public ResponseEntity<PostResponse> getPostById(
      @Parameter(description = "특정 게시글 ID") @PathVariable Long id) { //@PathVariable : URL 경로에서 {id}값을 받아옴!!
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @Operation(summary = "게시글 수정",
      description = "게시판 페이지에서 게시글 수정 후 수정 완료 버튼을 눌렀을 때 요청되는 API")
  @PutMapping("/posts/{id}")
  public ResponseEntity<PostResponse> updatePost( //HTTP 응답으로 PostResponse DTO를 JSON 형태로 반환
      @Parameter(description = "게시글 수정 내용") @RequestBody UpdatePostRequest updatePostRequest,
      @Parameter(description = "특정 게시글 ID") @PathVariable Long id) {
    return ResponseEntity.ok(postService.updatePost(id, updatePostRequest));
  }

  @Operation(summary = "게시글 삭제",
      description = "게시판 페이지에서 게시글 삭제 버튼을 눌렀을 때 요청되는 API")
  @DeleteMapping("/posts/{id}")
  public ResponseEntity<Boolean> deletePost(
      @Parameter(description = "특정 게시글 ID") @PathVariable Long id) {
    return ResponseEntity.ok(postService.deletePost(id));
  }

  @Operation(summary = "게시글 조회 많은 순 조회", description = "게시판 페이지에서 조회 많은 순 버튼을 눌렀을 때 요청되는 API")
  @GetMapping("/posts/popular")
  public ResponseEntity<List<PostResponse>> getAllPostsSortedByViews() {
    return ResponseEntity.ok(postService.getAllPostsSortedByViews());
  }

  @Operation(summary = "게시글 최신순 조회", description = "게시판 페이지에서 최신순 버튼을 눌렀을 때 요청되는 API")
  @GetMapping("/posts/latest")
  public ResponseEntity<List<PostResponse>> getAllPostsSortedByCreatedAtDesc() {
    return ResponseEntity.ok(postService.getAllPostsSortedByCreatedAt());
  }
}
