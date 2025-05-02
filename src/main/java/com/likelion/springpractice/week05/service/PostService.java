package com.likelion.springpractice.week05.service;

import com.likelion.springpractice.week04.entity.Post;
import com.likelion.springpractice.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.week05.dto.response.PostResponse;
import com.likelion.springpractice.week05.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  //게시글 생성
  @Transactional
  public PostResponse createPost(CreatePostRequest createPostRequest) {

    Post post = Post.builder()
        .title(createPostRequest.getTitle())
        .content(createPostRequest.getContent())
        .views(0) //초기에는 조회수 0
        .build();
    postRepository.save(post);

    return toPostResponse(post);
  }

  //게시글 전체 조회
  @Transactional(readOnly = true)
  public List<PostResponse> getALlPosts() {
    List<Post> postList = postRepository.findAll();
    return postList.stream().map(this::toPostResponse).toList();
  }

  //게시글 최신순으로 조회
  @Transactional(readOnly = true)
  public List<PostResponse> getSortedPostsByCreatedAt() {
    List<Post> postListByCreatedAt = postRepository.findAllByOrderByCreatedAtDesc();
    return postListByCreatedAt.stream().map(this::toPostResponse).toList();
  }

  //게시글 조회순으로 조회
  @Transactional(readOnly = true)
  public List<PostResponse> getSortedPostsByViews() {
    List<Post> postListByViews = postRepository.findAllByOrderByViewsDesc();
    return postListByViews.stream().map(this::toPostResponse).toList();
  }


  //게시글 단일 조회
  public PostResponse getPostById(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

    //조회수 1증가
    post.increaseViews();
    postRepository.save(post);
    return toPostResponse(post);
  }

  //게시글 수정
  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

    Post updatedPost = Post.builder()
        .id(post.getId()) //중요!! 바꾸지 않을 값은 기존 값으로 build 해줘야 함
        .title(updatePostRequest.getTitle())
        .content(updatePostRequest.getContent())
        .build();
    postRepository.save(updatedPost);

    return toPostResponse(updatedPost);
  }

  //게시글 삭제
  @Transactional
  public Boolean deletePost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

    postRepository.delete(post);
    return true;
  }

  //Entity를 DTO로 변환해주는 메소드
  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder().postId(post.getId()).title(post.getTitle())
        .content(post.getContent()).views(post.getViews()).build();
  }

}
