package com.likelion.springpractice.domain.post.week05.service;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.post.week05.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
  private final PostRepository postRepository;

  // 게시글 생성
  @Transactional
  public PostResponse createPost(CreatePostRequest createPostRequest) {
    log.info("[서비스] 게시글 생성 시도: title={}, content={}", createPostRequest.getTitle(), createPostRequest.getContent());
    Post post = Post.builder()
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .views(0L)
        .build();
    postRepository.save(post);
    log.info("[서비스] 게시글 생성 완료: id={}, title={}, content={}", post.getId(), post.getTitle(), post.getContent());
    return toPostResponse(post);
  }

  // 게시글 전체 조회
  public List<PostResponse> getAllPosts() {
    log.info("[서비스] 게시글 전체 조회 시도");
    List<Post> postList = postRepository.findAll();
    log.info("[서비스] 조회된 게시글 수: {}", postList.size());
    return postList.stream().map(this::toPostResponse).toList();
  }

  // 게시글 단일 조회
  @Transactional
  public PostResponse getPostById(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
              log.warn("[서비스] 게시글 조회 실패 - 존재하지 않음: id={}", id);
              return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
            });
    post.increaseViews();
    postRepository.save(post);
    log.info("[서비스] 게시글 단일 조회 성공: id={}", id);
    return toPostResponse(post);
  }

  // 게시글 수정
  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    log.info("[서비스] 게시글 수정 시도: id={}, newTitle={}, newContent={}", id, updatePostRequest.getTitle(), updatePostRequest.getContent());

    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 수정 실패 - 존재하지 않음: id={}", id);
          return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        });

    post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());
    // postRepository.save(post);   // JAP가 변경 감지해서 자동 반영해주기 때문에 생략 가능

    log.info("[서비스] 게시글 수정 완료: id= {}, title={}, content={}", post.getId(), updatePostRequest.getTitle(), updatePostRequest.getContent());
    return toPostResponse(post);
  }

  @Transactional
  public boolean deletePost(Long id) {
    log.info("[서비스] 게시글 삭제 시도: id={}", id);
    Post post = postRepository.findById(id)
        .orElseThrow(()-> {
          log.warn("[서비스] 게시글 삭제 실패 - 존재하지 않음: id={}", id);
          return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        });

    postRepository.deleteById(id);
    log.info("[서비스] 게시글 삭제 완료: id={}", id);
    return true;
  }

  public List<PostResponse> getPostsByNewest() {
    log.info("[서비스] 게시글 최신순 조회 시도");
    List<PostResponse> result = postRepository.findAllByOrderByCreatedAtDesc()
        .stream()
        .map(this::toPostResponse)
        .collect(Collectors.toList());
    log.info("[서비스] 최신순 조회 완료 - 총 {}건", result.size());
    return result;
  }

  public List<PostResponse> getPostsByViews() {
    log.info("[서비스] 게시글 조회수순 조회 시도");
    List<PostResponse> result = postRepository.findAllByOrderByViewsDesc()
        .stream()
        .map(this::toPostResponse)
        .collect(Collectors.toList());
    log.info("[서비스] 조회수순 조회 완료 - 총 {}건", result.size());
    return result;
  }

  // Entity를 DTO로 변환해주는 메소드
  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .createdAt(post.getCreatedAt())
        .views(post.getViews())
        .build();
  }
}


