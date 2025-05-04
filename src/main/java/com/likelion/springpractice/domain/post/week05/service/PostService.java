package com.likelion.springpractice.domain.post.week05.service;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.post.week05.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {

  //컨트롤러에선, 서비스 메소드를 호출!! 서비스에선, 레포지토리 메서드를 호출한다!!
  private final PostRepository postRepository;

  // 게시글 생성
  // 클라이언트가 보낸 게시글 생성 요청을 처리!!
  @Transactional
  public PostResponse createPost(CreatePostRequest createPostRequest) { //DTO를 인자로 받아,
    Post post = Post.builder()  //DTO -> Entity 변환 후!
        .title(createPostRequest.getTitle()) //프론트에서 보낸 "title" 값을 Post 객체의 필드로 넣는 과정
        .content(createPostRequest.getContent())
        .views(0) //조회수 0으로 생성
        .build();
    postRepository.save(post);  //Post 객체를 DB에 저장!!

    return toPostResponse(post); //그리고, 저장된 결과(Entity)를 DTO로 변환해서 반환!!
  }

  //게시글 전체 조회
  public List<PostResponse> getAllPosts() {
    List<Post> postList = postRepository.findAll(); //findAll을 통해 모든 게시글 Entity를 가져옴!
    return postList.stream().map(this::toPostResponse)
        .toList(); //가져온 리스트를 PostResponse(DTO)로 하나하나 변환해야 하므로 stream().map 사용!
  }

  //게시글 단일 조회
  public PostResponse getPostById(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    post.increaseViews();
    return toPostResponse(post);
  }

  //게시글 수정
  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    //post.update(updatePostRequest.getTitle(), updatePostRequest.getContent(),
    //    updatePostRequest.getViews());

    post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());
    //이렇게 하면 DB에서 title, content 값만 바뀌고, 나머지 값들(createAt)등은 그대로 유지됨!

//    Post updatedPost = Post.builder()
//        .id(post.getId()) //중요!! 바꾸지 않을 값은 기존 값으로 build해줘야 함!!
//        .title(updatePostRequest.getTitle())
//        .content(updatePostRequest.getContent())
//        .build();
//    postRepository.save(updatedPost);
//    return toPostResponse(updatedPost);
    return toPostResponse(post);
  }

  //게시글 삭제
  @Transactional
  public Boolean deletePost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    postRepository.deleteById(id);
    return true;
  }

  //게시글 조회순 조회
  public List<PostResponse> getAllPostsSortedByViews() {
    List<Post> postList = postRepository.findAllByOrderByViewsDesc();
    return postList.stream().map(this::toPostResponse).toList();
  }

  //게시글 최신순 조회
  public List<PostResponse> getAllPostsSortedByCreatedAt() {
    List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
    return postList.stream().map(this::toPostResponse).toList();
  }

  //Entity를 DTO로 변환해주는 메소드
  private PostResponse toPostResponse(Post post) {
    return PostResponse.builder().postId(post.getId()).title(post.getTitle())
        .content(post.getContent()).views(post.getViews()).build();
  }
}
