package com.likelion.springpractice.domain.post.week05.service;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.post.week05.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

  private final PostRepository postRepository;

  // Converter 메소드: Entity를 DTO로 변환해주는 메소드
  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder()
        .postId(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .build();
  }

  // 게시글 생성
  @Transactional
  public PostResponse createPost(CreatePostRequest createPostRequest) {
    log.info("[서비스] 게시글 생성 시도: title={}, content={}",
        createPostRequest.getTitle(), createPostRequest.getContent());

    Post post = Post.builder()
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .viewCount(0L) // 0으로 초기화
        .build();
    postRepository.save(post);

    log.info("[서비스] 게시글 생성 완료: id={}, title={}, content={}",
        post.getId(), createPostRequest.getTitle(), createPostRequest.getContent());

    return toPostResponse(post);
  }

  // 게시글 전체 조회 (@Transactional 사용은 데이터 변화가 있을 때 붙이므로 get에서는 사용 X)
  // readOnly: 조회만 하고 데이터 변경은 하지 않을 떄 (변경 감지를 위한 스냅샷 사용 X)
  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts() {
    log.info("[서비스] 게시글 전체 조회 시도");
    List<Post> postList = postRepository.findAll();

    log.info("[서비스] 조회된 게시글 수: {}", postList.size());
    return postList.stream().map(this::toPostResponse).toList();
  }

  // 게시글 단일 조회
  @Transactional
  public PostResponse getPostById(Long id) {
    log.info("[서비스] 게시글 단일 조회 시도: id={}", id);
    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 조회 실패 - 존재하지 않음: id={}", id);
          return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        });

    log.info("[서비스] 게시글 단일 조회 성공: id={}", id);
    post.increaseViewCount(); // 조회수 증가
    return toPostResponse(post);
  }

  // 게시글 수정
  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    log.info("[서비스] 게시글 수정 시도: id={}, newTitle={}, nowContent={}",
        id, updatePostRequest.getTitle(), updatePostRequest.getContent());
    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 수정 실패 - 존재하지 않음: id={}", id);
          return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        });
    post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());

    log.info("[서비스] 게시글 수정 완료: id={}, title={}, content={}",
        post.getId(), updatePostRequest.getTitle(), updatePostRequest.getContent());
    return toPostResponse(post);

    /* [Post 객체가 새로 생성되는 문제 발생]
     * -> 동일한 id를 사용하여 객체 저장
     * -> JPA가 이미 존재하는 데이터로 인식하여 merge() 실행
     * -> 해당 로직은 createAt을 넣어준 적이 없으므로 createdAt이 NULL 값으로 세팅

    Post updatedPost = Post.builder()
        .id(post.getId()) // 바꾸지 않을 값이라면 기존 값으로 build
        .title(updatePostRequest.getTitle())
        .content(updatePostRequest.getContent())
        .build();
    postRepository.save(updatedPost);

    return toPostResponse(updatedPost);
    */
  }

  // 게시글 삭제
  @Transactional
  public Boolean deletePost(Long id) {
    log.info("[서비스] 게시글 삭제 시도: id={}", id);
    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 삭제 실패 - 존재하지 않음: id={}", id);
          return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        });

    postRepository.deleteById(id);
    log.info("[서비스] 게시글 삭제 완료: id={}", id);
    return true;
  }

  // 게시글 조회 많은순 조회 로직
  @Transactional(readOnly = true)
  public List<PostResponse> getPostsByViewCount() {
    log.info("[서비스] 게시글 조회 많은순 시도");
    return postRepository.findAllByOrderByViewCountDesc()
        .stream()
        .map(this::toPostResponse)
        .toList();
  }

  // 게시글 최신순 조회 로직
  @Transactional(readOnly = true)
  public List<PostResponse> getRecentPosts() {
    log.info("[서비스] 최신순 조회 시도");
    return postRepository.findAllByOrderByCreatedAtDesc()
        .stream()
        .map(this::toPostResponse)
        .toList();
  }
}
