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
    log.info("[서비스] 게시글 생성 시도:title={},content={}", createPostRequest.getTitle(),
        createPostRequest.getContent());
    Post post = Post.builder()
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .viewCount(0)
        .build();

    postRepository.save(post);
    log.info("[서비스]게시글 생성 완료: id={},title={},content={}", post.getId(), post.getTitle(),
        post.getContent());
    return toPostResponse(post);
  }


  public List<PostResponse> getAllPosts() {
    log.info("[서비스] 게시글 전체 조회 시도");
    List<Post> postList = postRepository.findAll();
    log.info("[서비스] 조회된 게시글 수: {}", postList.size());
    return postList.stream().map(this::toPostResponse).toList();
  }

  //5주차에 transactional추가함
  @Transactional
  public PostResponse getPostById(Long id) {
    log.info("[서비스] 게시글 단일 조회 시도: id={}", id);
    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 조회 실패 -존재하지않음: id={}", id);

          return new IllegalArgumentException("게시글을 찾을수없습니다.");
        });
//    5주차때 조회수 증가 할수있는거넣음
    log.info("[서비스] 게시글 단일 조회 성공: id={}", id);
    post.plusViewCount();
    return toPostResponse(post);
  }

  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    log.info("[서비스] 게시글 수정 시도: id={},newTitle={},newContent={}", id, updatePostRequest.getTitle(),
        updatePostRequest.getContent());
    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 수정 실패 -존재하지않음: id={}", id);
          return new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        });

    post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());

    return toPostResponse(post);
  }

  @Transactional
  public Boolean deletePost(Long id) {
    log.info("[서비스] 게시글 삭제 시도: id={}", id);
    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 삭제 실패 - 존재하지 않음: id={}", id);
          return new IllegalArgumentException("게시글을 찾을수 없습니다.");
        });
    postRepository.deleteById(id);
    return true;
  }

  //5주차때 viewcount추가햇어
  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder().id(post.getId()).title(post.getTitle())
        .content(post.getContent()).viewCount(post.getViewCount()).build();
  }

  //  5주차때 만든 최신순과 조회수많은순 service 만든거
  public List<PostResponse> getPostsByLatest() {
    return postRepository.findAllByOrderByCreatedAtDesc()
        .stream()
        .map(this::toPostResponse)
        .toList();
  }

  public List<PostResponse> getPostsByPopularity() {
    return postRepository.findAllByOrderByViewCountDesc()
        .stream()
        .map(this::toPostResponse)
        .toList();
  }
}