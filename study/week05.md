# 5주차 복습

다중 선택: 매주 과제

<aside>
📎

### ORM

자바 객체를 데이터베이스 테이블과 자동으로 매핑해주는 기술

### Repository란?

DB와 통신하는 계층, JPA를 상속받아서 CRUD 기능을 제공받는 인터페이스로 Repository를 이용해서 Entity 객체를 DB에 저장, 수정, 조회, 삭제의 작업들을 할
수 있다.

### JPA란?

자바 객체를 데이터베이스 테이블과 자동으로 매핑해주는 기술을 쓰기 위한 인터페이스

즉, ORM을 쓰기 위한 인터페이스

BUT 구현체는 아님 따라서 개발자는 JPA의 구현체를 사용해서 자바 객체와 데이터베이스 테이블을 매핑할 수 있다.

⇒ DB와 자바 클래스 사이의 중간관리자

### JPA 구현체

ORM 기술을 구현해서 개발자가 직접적으로 사용하는 구현체

ex) Hibernate(가장 기본으로 사용하는 JPA 구현체), EclipseLink, OpenJPA ..

### JDBC

자바에서 데이터베이스에 직접 접근하기 위한 기본적인 표준 API로 자바가 DB와 대화할 수 있게 해주는 기초적인 수단

JPA 아래에서 작동하기에, JPA가 JDBC를 감추고 개발자가 ORM 이용을 편리하게 하는것이 가능

### JPQL

JPA에서 사용하는 객체지향 쿼리 언어

</aside>

<aside>
📌

### Repository

Entity 객체를 DB에 저장, 수정, 조회, 삭제등 작업을 할 수 있게 해주는 계층

```java
package com.likelion.springpractice.domain.post.week05.repository;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
// 기본키가 Long인 Post Entity 클래스의 객체를 데이터베이스와 연결해주는 레포지터리

}
```

- JpaRepository를 상속 받았기 때문에 아무 코드 없이도 CRUD 기능을 사용 가능
- 기본적인 CRUD기능이 아닌 특별한 조건으로 데이터를 조회하고 싶을때는 쿼리 메소드 또는 쿼리문 직접 작성 필요

쿼리
메소드 : [https://velog.io/@seongwon97/Spring-Boot-Query-Method](https://velog.io/@seongwon97/Spring-Boot-Query-Method)

### Query Method

JPA가 메소드 이름으로 쿼리문을 생성하기 위해 제공하는 기능

Repository에서 그냥 JpaRepository를 상속받기만 하면 자동으로 제공되는 CRUD 기능이 아닌 특별한 조건으로 데이터를 조회하려고 할때는 쿼리문이나 쿼리 메소드를
작성해야한다. 이때 쿼리 메소드를 이용하면 직접 쿼리문을 작성할 필요가 없어서 더 간편하다.

</aside>

<aside>
📌

### DTO

계층 간 데이터 전송을 위한 객체

DTO 클래스에 Swagger 내용이 들어감 - 주로 DTO 클래스와 Controller 클래스에 Swagger 내용을 나누어서 작성하기 떄문

```java
package com.likelion.springpractice.domain.post.week05.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "CreatePostRequest: 게시글 생성 요청 DTO")
public class CreatePostRequest {

  @Schema(description = "게시글 제목", example = "1주차 세션???")
  private String title;

  @Schema(description = "게시글 내용", example = "내용내용내용내용")
  private String content;

}
```

- Request 패키지 안에 DTO 클래스 : 클라이언트 → 서버 방향으로의 데이터 전송을 위한 객체(DTO)를 만들기 위한 클래스라고 구분해둔것

```java
package com.likelion.springpractice.domain.post.week05.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "UpdatePostRequest: 게시글 수정 요청 DTO")
public class UpdatePostRequest {

  @Schema(description = "게시글 제목", example = "5주차 세션")
  private String title;
  @Schema(description = "게시글 내용", example = "이번주 세션도 화이팅!")
  private String content;

}
```

```java
package com.likelion.springpractice.domain.post.week05.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder // Builder 자동 생성해주는 어노테이션
@Schema(title = "PostResponse: 게시글 응답 DTO")
public class PostResponse {

  @Schema(description = "게시글 ID", example = "1")
  private Long postId; // 요창 DTO 클래스에는 없음

  @Schema(description = "게시글 제목", example = "5주차 세션")
  private String title;

  @Schema(description = "게시글 내용", example = "이번주 세션도 화이팅!")
  private String content;

}
```

- Reponse 패키지 안에 있는 DTO 클래스 : 서버 → 클라이언트 방향으로의 데이터 전송을 위한 객체(DTO)를 만들기 위한 클래스

🔒*@Data 는 지양 : 여러 메소드를 자동 생성해줘서 편리하지만 다양한 문제 존재*

객체 생성시 생성자 or Setter or Builder 를 이용하는 방법들이 존재

이때, @Builder를 이용하면 해당 클래스 객체를 생성할때 필드이름을 명시적으로 쓸 수 있어서 가독성이 높아진다

```java
// 생성자를 사용했을때
Post updatePost = new Post(post.getId(), updatePostRequest.getTitle(),
        updatePostRequest.getContent());

// Builder 를 사용했을때
Post updatedPost = Post.builder()
    .id(post.getId())
    .title(updatePostRequest.getTitle())
    .content(updatePostRequest.getContent())
    .build();
```

</aside>

<aside>
📌

### Service

비즈니스 로직을 담당하는 계층으로 실질적으로 프로그램이 해야하는 일의 로직이 작성되어야 하는 클래스

```java
package com.likelion.springpractice.domain.post.week05.service;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.post.week05.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  // 게시글 생성
  @Transactional
  public PostResponse createPost(CreatePostRequest createPostRequest) {
    Post post = Post.builder() // build로 post 객체 생성
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .build();
    postRepository.save(post); // 생성한 post 객체를 DB에 저장

    return toPostResponse(post); // post Entity를 DTO로 변환해서 반환
  }

  // 게시글 전체 조회
  public List<PostResponse> getAllPosts() {
    List<Post> posts = postRepository.findAll();
    return posts.stream().map(this::toPostResponse).toList();
  }

  // 게시글 단일 조회
  public PostResponse getPostById(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));
    return (PostResponse) toPostResponse(post);
  }

  // 게시글 수정
  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));

    Post updatedPost = Post.builder()
        .id(post.getId())
        .title(updatePostRequest.getTitle())
        .content(updatePostRequest.getContent())
        .build();
    postRepository.save(updatedPost);

    return toPostResponse(updatedPost);
  }

  // 게시글 삭제
  @Transactional
  public Boolean deletePost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));
    postRepository.deleteById(id);
    return true;
  }

  // Entity를 DTO로 변환해주는 메소드 => Converter
  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder()
        .postId(post.getId())
        .title(post.getTitle())
        .content(post.getContent()).build();
  }
}

```

### Converter

DTO <—>Entity 변환해주는 클래스나 메소드

### Converter 생성 방법

1. Service 클래스 내부에 메소드로 생성
2. Converter 패키지 생성 후 클래스로 생성
3. Spring의 Converter<S, T> 인터페이스 사용

- 이 게시판 프로그램에서는 1번 방식으로 생성함

### 트렌젝션

데이터 거래에서 안정성을 확보하기 위한 방법

데이터 처리 과정에서 오류가 발생하면 작업을 원상태로 복구함

### 트렌젝션 선언

1. 클래스 단에서 @Transactional 적용하는 법
2. 메소드 단에서 해당 메소드레만 @Transactional 적용하는 방법
    - 해당 메소드에만 적용되고 따라서 구체적으로 설정이 가능
    - 우선순위가 더 높다

</aside>

<aside>
📌

### Controller

```java
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

// swagger 명세서 작성
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {

  private final PostService postService;

  // 게시글 생성 API
  @Operation(summary = "게시글 생성", description = "게시판 메이지에서 게시글 작성 후 생성 버튼을 눌렀을때 요청되는 API")
  @PostMapping("/posts")
  public ResponseEntity<PostResponse> createPost(
      @Parameter(description = "게시글 작성 내용") @RequestBody CreatePostRequest createPostRequest) {
    return ResponseEntity.ok(postService.createPost(createPostRequest));
  }

  // 게시글 전체 조회 API
  @Operation(summary = "게시글 전체 조회",
      description = "게시판 페이지로 이동할때 요청되는 API")
  @GetMapping("/posts")
  public ResponseEntity<List<PostResponse>> getAllPosts() {
    return ResponseEntity.ok(postService.getAllPosts());
  }

  // 게시글 단일 조회 API
  @Operation(summary = "게시글 단일 조회",
      description = "게시판 페이지에서 게시글에 접근할때 요청되는 API")
  @GetMapping("/posts/{id}")
  public ResponseEntity<PostResponse> getPostByID(
      @Parameter(description = "특정 게시글 ID") @PathVariable Long id) {
    return ResponseEntity.ok(postService.getPostById(id));

  }

  // 게시글 수정 API
  @Operation(summary = "게시글 수정",
      description = "게시판 페이지에서 게시글 수정 후 수정 완료 버튼을 눌렀을때 요청되는 API")
  @PutMapping("/posts/{id}")
  public ResponseEntity<PostResponse> updatePost(
      @Parameter(description = "게시글 수정 내용") @RequestBody UpdatePostRequest updatePostRequest,
      @Parameter(description = "특정 게시글 ID") @PathVariable Long id) {
    return ResponseEntity.ok(postService.updatePost(id, updatePostRequest));
  }

  // 게시글 삭제 API
  @Operation(summary = "게시글 삭제", description = "게시판 페이지에서 게시글 삭제 버튼을 눌렀을때 요청되는 API")
  @DeleteMapping("/posts/{id}")
  public ResponseEntity<Boolean> deletePost(
      @Parameter(description = "특정 게시글 ID") @PathVariable Long id) {
    return ResponseEntity.ok(postService.deletePost(id));
  }

}

```

- 서로 다른 기능의 API 이지만 엔드포인트는 같을 수 있음 → RESTful API 특징
  즉, URI 는 고정하되, HTTP 메서드를 바꿔서 다양한 동작을 나타낸다.

### ResponseEntity

개발자가 직접 결과 데이터와 HTTP 상태 코드를 제어할 수 있는 클래스

</aside>