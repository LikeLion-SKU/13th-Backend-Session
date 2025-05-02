package com.likelion.springpractice.domain.post.week05.dto.service;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.repository.PostRepository;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .build();
        postRepository.save(post);

        return toPostResponse(post);
    }

    // 게시글 전체 조회
    public List<PostResponse> getAllPosts() {
        List<Post> postList = postRepository.findAll();
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

        Post updatedPost = Post.builder()
                .id(post.getId()) // 중요!! 바꾸지 않을 값은 기존 값으로 build 해줘야 함
                .title(updatePostRequest.getContent())
                .content(updatePostRequest.getContent())
                .build();
        postRepository.save(updatedPost);

        return toPostResponse(updatedPost);
    }

    // 게시글 삭제
    @Transactional
    public Boolean deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        postRepository.deleteById(id);
        return true;
    }

    // Entity를 DTO로 변환해주는 메소드
    private PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .build();
    }

    // 최신순으로 게시글 목록 조회
    public List<PostResponse> getPostsOrderByCreatedAtDesc() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toPostResponse).toList();
    }

    // 조회수 기준으로 인기 게시글 목록 조회
    public List<PostResponse> getPostsOrderByViewCountDesc() {
        return postRepository.findAllByOrderByViewCountDesc()
                .stream().map(this::toPostResponse).toList();
    }
}

