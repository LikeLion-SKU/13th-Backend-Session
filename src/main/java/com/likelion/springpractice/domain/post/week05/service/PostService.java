package com.likelion.springpractice.domain.post.week05.service;

import com.likelion.springpractice.domain.post.exception.PostErrorCode;
import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.post.week05.repository.PostRepository;
import com.likelion.springpractice.global.exception.CustomException;
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

  // 게시글 생성
  @Transactional
  public PostResponse createPost(CreatePostRequest createPostRequest) {
    log.info("[서비스]게시글 생성 시도: title= {}, content= {}", createPostRequest.getTitle()
        , createPostRequest.getContent());

    validatePostRequest(createPostRequest.getTitle(), createPostRequest.getContent());

    Post post = Post.builder()
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .build();
    postRepository.save(post);
    log.info("[서비스]게시글 생성 완료: id={}, title= {}, content= {}", post.getId(), createPostRequest.getTitle()
        , createPostRequest.getContent());
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
  public PostResponse getPostById(Long id) {
    log.info("[서비스] 게시글 단일 조회 시도: id={}", id);

    if (id == null || id <= 0) {
      log.warn("[서비스] 게시글 단일 조회 실패 - 유효하지 않은 id: {}", id);
      throw new CustomException(PostErrorCode.INVALID_POST_ID);
    }

    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 조회 실패 - 존재하지 않음: id={}", id);
          return new CustomException(PostErrorCode.POST_NOT_FOUND);
        });
     return toPostResponse(post);
  }

  // 게시글 수정
  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    log.info("[서비스] 게시글 수정 시도: id={}, newTitle={}, newContent= {}", id,
        updatePostRequest.getTitle(), updatePostRequest.getContent());

    if (id == null || id <= 0) {
      log.warn("[서비스] 게시글 수정 실패 - 유효하지 않은 id: {}", id);
      throw new CustomException(PostErrorCode.INVALID_POST_ID);
    }

    validatePostRequest(updatePostRequest.getTitle(), updatePostRequest.getContent());

    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 수정 실패 - 존재하지 않음 id={}", id);
          return new CustomException(PostErrorCode.POST_NOT_FOUND);
        });

    Post updatedPost = Post.builder()
        .id(post.getId())
        .title(updatePostRequest.getTitle())
        .content(updatePostRequest.getContent())
        .build();
    postRepository.save(updatedPost);

    log.info("[서비스] 게시글 수정 완료: id={}, title={}, content={}", post.getId(),
        updatePostRequest.getTitle(), updatePostRequest.getContent());
    return toPostResponse(updatedPost);
  }

  //개사글 삭제
  @Transactional
  public Boolean deletePost(Long id) {
    log.info("[서비스] 게시글 삭제 시도: id={}", id);

    if (id == null || id <= 0) {
      log.warn("[서비스] 게시글 삭제 실패 - 유효하지 않은 id: {}", id);
      throw new CustomException(PostErrorCode.INVALID_POST_ID);
    }

    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 삭제 실패 - 존재하지 않음: id={}", id);
          return new CustomException(PostErrorCode.POST_NOT_FOUND);
        });

    postRepository.deleteById(id);
    log.info("[서비스] 게시글 삭제 완료: id={}", id);
    return true;
  }

  private PostResponse toPostResponse(Post post) {
    return PostResponse
        .builder()
        .postId(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .createdAt(post.getCreatedAt())
        .viewCount(post.getViewCount())
        .build();
  }

  // 게시글 조회 수 증가
  @Transactional
  public PostResponse getPostAndIncreaseViewCount(Long id) {

    if (id == null || id <= 0) {
      log.warn("[서비스] 게시글 조회 수 증가 실패 - 유효하지 않은 id: {}", id);
      throw new CustomException(PostErrorCode.INVALID_POST_ID);
    }

    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 조회 수 증가 실패 - 게시글 존재하지 않음 : id={}", id);
          return new CustomException(PostErrorCode.POST_NOT_FOUND);
            });

    post.increaseViewCount();
    postRepository.save(post);

    return toPostResponse(post);
  }

  // 게시글 최신 순 조회
  public List<PostResponse> getPostsSortedByLatest () {
    List<Post> posts  = postRepository.findAllByOrderByCreatedAtDesc();
    return posts
        .stream()
        .map(this::toPostResponse)
        .toList();
  }

  // 게시글 조회 많은 순 조회
  public List<PostResponse> getPostsSortedByViewCount() {
    List<Post> posts  = postRepository.findAllByOrderByViewCountDesc();
    return posts
        .stream()
        .map(this::toPostResponse)
        .toList();
  }

  // 중복되는 에러코드들 하나의 메소드로 분리함.
  private void validatePostRequest(String title, String content) {
    if (title == null || title.isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_TITLE);
    }

    if (content == null || content.isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_CONTENT);
    }

    if (title.length() > 10) {
      throw new CustomException(PostErrorCode.TITLE_TOO_LONG);
    }
  }
}
