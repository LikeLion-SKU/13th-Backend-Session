package com.likelion.springpractice.domain.post.week05.service;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.reponse.PostResponse;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
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

  // 게시글 생성
  @Transactional
  public PostResponse createPost(CreatePostRequest createPostRequest) {
    log.info("[서비스] 게시글 생성 시도 : title={}, content={}",
        createPostRequest.getTitle(), createPostRequest.getContent());

    Post post = Post.builder()
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .views(0)
        .build();
    postRepository.save(post);
    log.info("[서비스] 게시글 생성 완료 : id={}, title={}, content={}",
        post.getId(),post.getTitle(),post.getContent());

    return toPostResponse(post);
  }

  // 게시글 전체 조회
  public List<PostResponse> getAllPosts() {
    log.info("[서비스] 게시글 전체 조회 시도");
    // postRepository에 등록되어 있는 모든 요소들을 findAll()로 불러와서 List에 저장
    List<Post> postList = postRepository.findAll();
    log.info("[서비스] 조회된 게시글 수: {}", postList.size());

    return postList.stream().map(this::toPostResponse).toList();
  }

  // 게시글 최신순으로 조회
  public List<PostResponse> getAllPostsByCreatedAtDesc() {
    List<Post> postListCreatedAtDesc = postRepository.findAllByOrderByCreatedAtDesc();
    return postListCreatedAtDesc.stream().map(this::toPostResponse).toList();
  }

  // 게시글 조회수 순으로 조회
  public List<PostResponse> getAllPostsByViewsDesc() {
    List<Post> postListViews = postRepository.findAllByOrderByViewsDesc();
    return postListViews.stream().map(this::toPostResponse).toList();
  }

  // 게시글 단일 조회
  public PostResponse getPostById(Long id) {
    log.info("[서비스] 게시글 단일 조회 시도 : id={}", id);
    Post post = postRepository.findById(id) // 특정 게시물의 객체
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 조회 실패 - 존재하지 않음 : id={}", id);
          return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        });

    log.info("[서비스] 게시글 단일 조회 성공 : id={}", id);
    post.incrementViews(); // post의 게시글 조회수 증가
    postRepository.save(post); // 변경사항 저장

    return toPostResponse(post);
  }

  // 게시글 수정
  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    log.info("[서비스] 게시글 수정 시도 : id={}, newTitle={}, newContent={}",
        id, updatePostRequest.getTitle(), updatePostRequest.getContent());

    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.info("[서비스] 게시글 수정 실패 - 존재하지 않음 : id={}", id);
          return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        });

    post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());
    // postRepository.save(post);

    log.info("[서비스] 게시글 수정 완료 : id={}, title={}, content={}",
        post.getId(), post.getTitle(), post.getContent());

    return toPostResponse(post);
  }

  // 게시글 삭제
  @Transactional
  public Boolean deletePost(Long id) {
    log.info("[서비스] 게시글 삭제 시도 : id={}", id);

    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 삭제 실패 - 존재하지 않음 : id={}", id);
          return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        });

    postRepository.deleteById(id);
    log.info("[서비스] 게시글 삭제 완료 : id={}", id);
    return true;
  }

  // Entity를 DTO로 변환해주는 메소드
  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder()
        .postId(post.getId())
        .title(post.getTitle())
        .content(post.getContent()).build();
  }

}
