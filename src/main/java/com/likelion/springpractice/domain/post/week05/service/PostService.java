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
    Post post = Post.builder()
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .view(0L)
        .build();
    postRepository.save(post);

    return toPostResponse(post);
  }


  // 게시글 전체 조회
  public List<PostResponse> getAllPosts() {
    List<Post> postList = postRepository.findAll();
    return postList.stream().map(this::toPostResponse).toList();
  }

  // 전체 게시글 최신순 조회
  public List<PostResponse> getAllPostsLatest(){
    List<Post> postList = (List<Post>) postRepository.findAllByOrderByCreatedAtDesc();
    return postList.stream().map(this::toPostResponse).toList();
  }

  // 전체 게시글 조회 많은 순 조회
  public List<PostResponse> getAllPostsPopular(){
    List<Post> postList = (List<Post>) postRepository.findAllByOrderByViewDesc();
    return postList.stream().map(this::toPostResponse).toList();
  }

  // 게시글 단일 조회
  public PostResponse getPostById(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    return toPostResponse(post);
  }


  // 게시글 수정
  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

    post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());

    return toPostResponse(post);
  }


  // 게시글 삭제
  @Transactional
  public boolean deletePost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

    postRepository.deleteById(id);
    return true;
  }





  // Entity를 DTO로 변환해 주는 메소드
  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder().postId(post.getId()).title(post.getTitle())
        .content(post.getContent()).build();
  }

}
