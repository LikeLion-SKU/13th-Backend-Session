package com.likelion.springpractice.domain.post.week05.service;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.post.week05.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<PostResponse> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(this::toPostResponse).toList();
    }
    //게시글 단일 조회
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return toPostResponse(post);
    }
    //게시글 수정
    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Post updatedPost = Post.builder()
                .id(post.getId())
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
                .orElseThrow(()->new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        postRepository.deleteById(id);
        return true;
    }
    //Entity를 DTO로 변환해주는 메소드
    private PostResponse toPostResponse(Post post) {
        return PostResponse.builder().postId(post.getId())
                .title(post.getTitle()).content(post.getContent()).build();
    }
}
