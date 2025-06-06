package com.likelion.springpractice.domain.post.week05.service;

import com.likelion.springpractice.domain.post.exception.PostErrorCode;
import com.likelion.springpractice.domain.post.week04.entity.Post;
import com.likelion.springpractice.domain.post.week05.dto.request.CreatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.request.UpdatePostRequest;
import com.likelion.springpractice.domain.post.week05.dto.response.PostResponse;
import com.likelion.springpractice.domain.post.week05.repository.PostRepository;
import com.likelion.springpractice.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

  //컨트롤러에선, 서비스 메소드를 호출!! 서비스에선, 레포지토리 메서드를 호출한다!!
  private final PostRepository postRepository;

  // 게시글 생성
  // 클라이언트가 보낸 게시글 생성 요청을 처리!!
  @Transactional
  public PostResponse createPost(CreatePostRequest createPostRequest) { //DTO를 인자로 받아,
    log.info("[서비스]게시글 생성 시도: title= {}, content={}", createPostRequest.getTitle(),
        createPostRequest.getContent());

    if (createPostRequest.getTitle() == null || createPostRequest.getTitle().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_TITLE);
    }

    if (createPostRequest.getContent() == null || createPostRequest.getContent().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_CONTENT);
    }

    if (createPostRequest.getTitle().length() > 10) {
      throw new CustomException(PostErrorCode.TITLE_TOO_LONG);
    }

    Post post = Post.builder()  //DTO -> Entity 변환 후!
        .title(createPostRequest.getTitle()) //프론트에서 보낸 "title" 값을 Post 객체의 필드로 넣는 과정
        .content(createPostRequest.getContent())
        .views(0) //조회수 0으로 생성
        .build();
    postRepository.save(post);  //Post 객체를 DB에 저장!!

    log.info("[서비스]게시글 생성 완료: id= {}, title= {}, content={}", post.getId(), post.getTitle(),
        post.getContent());
    return toPostResponse(post); //그리고, 저장된 결과(Entity)를 DTO로 변환해서 반환!!
  }

  //게시글 전체 조회
  @Transactional
  public List<PostResponse> getAllPosts() {
    log.info("[서비스] 게시글 전체 조회 시도");
    List<Post> postList = postRepository.findAll(); //findAll을 통해 모든 게시글 Entity를 가져옴!
    log.info("[서비스] 조회된 게시글 수: {}", postList.size());
    return postList.stream().map(this::toPostResponse)
        .toList(); //가져온 리스트를 PostResponse(DTO)로 하나하나 변환해야 하므로 stream().map 사용!
  }

  //게시글 단일 조회
  @Transactional
  public PostResponse getPostById(Long id) {
    log.info("[서비스] 게시글 단일 조회 시도: id={}", id);
    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 조회 실패 - 존재하지 않음: id={}", id);
          return new CustomException(PostErrorCode.POST_NOT_FOUND);
        });
    post.increaseViews();
    log.info("[서비스] 게시글 단일 조회 성공: id={}", id);
    return toPostResponse(post);
  }

  //게시글 수정
  @Transactional
  public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
    log.info("[서비스] 게시글 수정 시도: id= {}, newTitle= {}, newContent= {}", id,
        updatePostRequest.getTitle(), updatePostRequest.getContent());

    if (updatePostRequest.getTitle() == null || updatePostRequest.getTitle().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_TITLE);
    }

    if (updatePostRequest.getContent() == null || updatePostRequest.getContent().isBlank()) {
      throw new CustomException(PostErrorCode.INVALID_POST_CONTENT);
    }
    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 수정 실패 - 존재하지 않음: id={}", id);
          return new CustomException(PostErrorCode.POST_NOT_FOUND);
        });

    post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());
    log.info("[서비스] 게시글 수정 완료: id={}, title= {}, content= {}", id, post.getTitle(),
        post.getContent());
    return toPostResponse(post);
  }

  //게시글 삭제
  @Transactional
  public Boolean deletePost(Long id) {
    log.info("[서비스] 게시글 삭제 시도: id= {}", id);
    Post post = postRepository.findById(id)
        .orElseThrow(() -> {
          log.warn("[서비스] 게시글 삭제 실패 - 존재하지 않음: id={}", id);
          return new CustomException(PostErrorCode.POST_NOT_FOUND);
        });
    postRepository.deleteById(id);
    log.info("[서비스] 게시글 삭제 완료: id= {}", id);
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
