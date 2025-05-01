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

  public List<PostResponse> getAllPosts() {
    List<Post> posts = postRepository.findAll();
    return posts.stream().map(this::toPostResponse).toList();
  }

  public PostResponse getPostById(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));
    return (PostResponse) toPostResponse(post);
  }

  // 모든 게시글을 최신순으로 조회하는 메소드
  public List<PostResponse> getLatestPosts() {
    List<Post> posts = postRepository.findAllByCreateAt_Time();
    return posts.stream().map(this::toPostResponse).toList();
  }

  // 모든 게시글을 조회수 많은 순으로 조회하는 메소드
  public List<PostResponse> getManyViewsPosts() {
    List<Post> posts = postRepository.findAllByViews();
    return posts.stream().map(this::toPostResponse).toList();
  }

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

  @Transactional
  public Boolean deletePost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다"));
    postRepository.deleteById(id);
    return true;
  }

  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder().postId(post.getId())
        .title(post.getTitle())//다른 이유는 Post에서 속성을 자료에서는 postId 인것과 다르게 id이기 때문에 id로 설정해야 함
        .content(post.getContent()).build();
  }
}
