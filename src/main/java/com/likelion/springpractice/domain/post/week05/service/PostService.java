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


  @Transactional
  public PostResponse createPost(CreatePostRequest createPostRequest) {
    Post post = Post.builder()
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .build();
    postRepository.save(post);
    return toPostResponse(post);
  }

  //  5주차에 Transactional이랑 postList.forEach(Post::plusviewcount)추가함
  @Transactional
  public List<PostResponse> getAllPosts() {
    List<Post> postList = postRepository.findAll();
    postList.forEach(Post::plusViewCount);
    return postList.stream().map(this::toPostResponse).toList();
  }

  //5주차에 transactional추가함
  @Transactional
  public PostResponse getPostById(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을수없습니다."));
//    5주차때 조회수 증가 할수있는거넣음
    post.plusViewCount();
    return toPostResponse(post);
  }

  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    Post updatePost = Post.builder()
        .id(post.getId())
        .title(updatePostRequest.getTitle())
        .content(updatePostRequest.getContent())
        .build();
    postRepository.save(updatePost);
    return toPostResponse(updatePost);
  }

  @Transactional
  public Boolean deletePost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을수 없습니다."));
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