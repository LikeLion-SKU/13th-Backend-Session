package com.likelion.springpractice.domain.post.week05.service;

import com.likelion.springpractice.domain.post.exception.PostErrorCode;
import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.post.week05.repository.PostRepository;
import com.likelion.springpractice.global.exception.CustomException;
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

    // 유효성 검사
    if (createPostRequest.getTitle() == null || createPostRequest.getTitle().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_TITLE);
    }
    if (createPostRequest.getContent() == null || createPostRequest.getContent().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_CONTENT);
    }
    if (createPostRequest.getTitle().length() > 10) {
      throw new CustomException(PostErrorCode.TITLE_TOO_LONG);
    }

    Post post = Post.builder()
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .views(0L)
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
    // 유효성 검사
    if (postList.isEmpty()) {
      throw new CustomException(PostErrorCode.POST_NOT_FOUND); // 게시글 찾을 수 없음..
    }
    return postList.stream().map(this::toPostResponse).toList();
  }


  public PostResponse getPostById(Long id) {
    log.info("[서비스] 게시글 단일 조회 시도 : id={}", id);
    // 유효성 검사
    if (id == null || id <= 0 || !(postRepository.existsById(id))) {
      throw new CustomException(PostErrorCode.POST_NOT_FOUND);
    }
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

    // 유효성 검사
    if (updatePostRequest.getTitle() == null || updatePostRequest.getTitle().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_TITLE);
    }
    if (updatePostRequest.getContent() == null || updatePostRequest.getContent().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_CONTENT);
    }

    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 수정 실패 - 존재하지 않음: id={}", id);
          return new IllegalArgumentException("게시글을 찾을 수 없습니다");
        });

    post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());
    /* -> 이 방식 X
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
    // 유효성 검사
    if (id == null || id <= 0 || !(postRepository.existsById(id))) {
      throw new CustomException(PostErrorCode.POST_NOT_FOUND);
    }
    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 삭제 실패 - 존재하지 않음 : id={}", id);
          return new IllegalArgumentException("게시글을 찾을 수 없습니다");
        });
    postRepository.deleteById(id);
    return true;
  }

  // Post는 Mapper를 안만들어서 여기서 toPostResponse 함수를 만듦
  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder().postId(post.getId())
        .title(post.getTitle())//다른 이유는 Post에서 속성을 자료에서는 postId 인것과 다르게 id이기 때문에 id로 설정해야 함
        .content(post.getContent()).build();
  }
}
