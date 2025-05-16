# 7주차 복습

다중 선택: 매주 세션

### Log

: 프로그램 실행 중에 일어나는 일들의 기록

### Logging

: 로그를 남기는 행위

- 로그를 통해 어디서 문제가 발생했는지 알 수 있어서 디버깅과 문제해결에 도움이 됨
- 배포된 서비스나 서버가 잘 작동 중인지 로그를 통해 알 수 있음
- 비정상적인 접근 시도를 감지할 수 있어서 보안 감시가 가능
- 로그인 시도, 결제 시도 등의 이벤트들을 기록하기에 이벤트 추적 가능

### SLF4J

: 자바에서 로깅을 위한 인터페이스를 제공하는 라이브러리

BUT 실제 로깅은 SLF4J의 구현체인 Logback이 수행한다 → 실제 로직 담당은 구현체가!

### Logback

: 실제로 로그를 남기는 구현체

### Log Level

로그의 심각도를 구분하기 위한 등급 체계

1. trace : 가장 자세한 로깅
2. debug : 개발 시 사용하는 상세 정보 ex) 변수 값
3. info : 일반적인 실행 정보 ex) 서버 시작, 사용자 로그인 성공
4. warn : 경고 but 계속 동작하는 잠재적 문제 ex) 잘못된 입력, 디스크 공간 부족
5. error : 에러발생 but 기능 실패 가능
6. critical : 심각한 오류 ex) 서비스 전체 중단 가능

너무 많은 로그는 성능에 악영향을 끼치기에 일반적으로 info부터 표시

```java
package com.likelion.springpractice.week07;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j // 객체 자동 생성
public class LoggingController {

  @GetMapping("/api/log-test")
  public String logTest() {
    log.trace("Trace Level 로그입니다 - 가장 상세한 정보");
    log.debug("Debug Level 로그입니다 - 디버깅용");
    log.info("Info Level 로그입니다 - 일반 정보");
    log.warn("Warn Level 로그입니다 - 경고");
    log.error("Error Level 로그입니다 - 오류 발생");

    return "로그 테스트 완료!";

  }

}

```

### Logger 객체 생성 방법

1. 클래스에서 직접 객체 생성 방법

    ```java
    public class 클래스명 {
        private static final Logger logger = LoggerFactory.getLogger(클래스명.class);
    }
    ```

    - 단, 클래스마다 객체를 생성해야하기에 번거로움

2. Lombok을 이용해서 자동으로 객체 생성하는 법

    ```java
    import lombok.extern.slf4j.Slf4j; // lombok 라이브러리
    //...
    
    @Slf4j // 객체 자동 생성
    public class LoggingController {
    
      @GetMapping("/api/log-test")
      public String logTest() {
        log.trace("Trace Level 로그입니다 - 가장 상세한 정보");
        log.debug("Debug Level 로그입니다 - 디버깅용");
        log.info("Info Level 로그입니다 - 일반 정보");
        log.warn("Warn Level 로그입니다 - 경고");
        log.error("Error Level 로그입니다 - 오류 발생");
    
        return "로그 테스트 완료!";
      }
    }
    ```

    - @slf4j 어노테이션을 이용하면 Logger 객체 자동 생성 가능
    - Lombok 라이브러리 필요

   ### PostService

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
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    
    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class PostService {
    
      private final PostRepository postRepository;
    
      @Transactional
      public PostResponse createPost(CreatePostRequest createPostRequest) {
        log.info("[서비스]게시글 생성 시도: title ={}, context = {}", createPostRequest.getTitle(),
            createPostRequest.getContent());
        Post post = Post.builder()
            .title(createPostRequest.getTitle())
            .content(createPostRequest.getContent())
            .build();
        postRepository.save(post);
        log.info("[서비스]게시글 생성 완료 : id ={}, title={}, context={}", post.getId(), post.getTitle(),
            post.getContent());
    
        return toPostResponse(post);
      }
    
      public List<PostResponse> getAllPosts() {
        log.info("[서비스] 게시글 전체 조회 시도");
        List<Post> postList = postRepository.findAll();
        log.info("[서비스] 조회된 게시글 수 : {}", postList.size());
        return postList.stream().map(this::toPostResponse).toList();
      }
    
      public PostResponse getPostById(Long id) {
        log.info("[서비스] 게시글 단일 조회 시도 : id={}", id);
        Post post = postRepository.findById(id)
            .orElseThrow(() -> {
              log.warn("[서비스] 게시글 조회 실패 - 존재하지 않음: id={}", id);
              return new IllegalArgumentException("게시글을 찾을 수 없습니다");
            });
        log.info("[서비스] 게시글 단일 조회 성공 : id ={}", id);
        return (PostResponse) toPostResponse(post);
      }
    
      // 모든 게시글을 최신순으로 조회하는 메소드
    //  public List<PostResponse> getLatestPosts() {
    //    List<Post> posts = postRepository.findAllByCreateAt_Time();
    //    return posts.stream().map(this::toPostResponse).toList();
    //  }
    
    //  // 모든 게시글을 조회수 많은 순으로 조회하는 메소드
    //  public List<PostResponse> getManyViewsPosts() {
    //    List<Post> posts = postRepository.findAllByViews();
    //    return posts.stream().map(this::toPostResponse).toList();
    //  }
    
      @Transactional
      public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
        log.info("[서비스] 게시글 수정 시도 : id={}, newTitle={}, newContext={}", id,
            updatePostRequest.getTitle(), updatePostRequest.getContent());
    
        Post post = postRepository.findById(id)
            .orElseThrow(() -> {
              log.warn("[서비스] 게시글 수정 실패 - 존재하지 않음: id={}", id);
              return new IllegalArgumentException("게시글을 찾을 수 없습니다");
            });
    
        post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());
        /*
        Post updatedPost = Post.builder()
            .id(post.getId())
            .title(updatePostRequest.getTitle())
            .content(updatePostRequest.getContent())
            .build();
        postRepository.save(updatedPost);
    
         */
        log.info("[서비스] 게시글 수정 완료 : id={}, title={}, context={}", post.getId(),
            updatePostRequest.getTitle(), updatePostRequest.getContent());
    
        return toPostResponse(post);
      }
    
      @Transactional
      public Boolean deletePost(Long id) {
        log.info("[서비스] 게시글 삭제 시도 : id={}", id);
        Post post = postRepository.findById(id)
            .orElseThrow(() -> {
              log.warn("[서비스] 게시글 삭제 실패 - 존재하지 않음 : id={}", id);
              return new IllegalArgumentException("게시글을 찾을 수 없습니다");
            });
        postRepository.deleteById(id);
        return true;
      }
    
      private PostResponse toPostResponse(Post post) {
        return PostResponse.builder().postId(post.getId())
            .title(post.getTitle())//다른 이유는 Post에서 속성을 자료에서는 postId 인것과 다르게 id이기 때문에 id로 설정해야 함
            .content(post.getContent()).build();
      }
    }
    
    ```

### 자주 발생하는 오류 로그 5가지

1. java.lang.IllegalStateException : Failed to load ApplicationContext

   테스트나 애플리케이션 실행 시 의존성 빈을 찾지 못했을 때 발생

2. org.springframework.beans.factory.UnsatisfiedDependencyException

   의존성 주입 실패. 해당 타입의 빈이 등록되어있지 않을때 발생

3. org.springframework.boot.web.server.PortInUseException

   이미 다른 프로새스가 해당 포트를 사용중일때 발생

4. java.lang.IllegalArgumentException: Not a managed type

   JPA에서 @Entity로 등록되지 않은 클래스를 사용하려고 할 때 발생

5. org.springframework.jdbc.CannotGetJdbcConnectionException

   데이터베이스와 연결 실패 시 발생