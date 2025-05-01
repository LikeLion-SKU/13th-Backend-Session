package com.likelion.springpractice.domain.post.week05.service;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.respose.PostResponse;
import com.likelion.springpractice.domain.post.week05.repository.PostRepository;
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
        .build();
    postRepository.save(post);
    return toPostResponse(post);
  }

  //게시글 전체 조회
  public List<PostResponse> getAllPosts(){
    List<Post> postList = postRepository.findAll();
    return postList.stream().map(this::toPostResponse).toList();
  }

  //게시글 단일 조회
  public PostResponse getPostById(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    post.viewCountUp();
    return toPostResponse(post);
  }

  //게시글 수정
  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    //내용 수정
    post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());

    return toPostResponse(post); // post는 JPA의 dirty checking으로 자동 저장됨
  }


  //게시물 삭제
  @Transactional
  public Boolean deletePost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    postRepository.deleteById(id);
    return true;
  }

  //Entity를 DTO로 변환해주는 메소드
  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder().postId(post.getId()).title(post.getTitle())
        .content(post.getContent()).build();
  }

  //게시글 최신순 조회
  public List<PostResponse> getPostsOrderByCreatedAtDesc(){
    return postRepository.findAllByOrderByCreatedAtDesc().stream()
        .map(this::toPostResponse)
        .toList();
  }

  //게시글 조회수순 조회
  public List<PostResponse> getPostsOrderByViewCountDesc(){
    return postRepository.findAllByOrderByViewCountDesc().stream()
        .map(this::toPostResponse)
        .toList();
  }
}

