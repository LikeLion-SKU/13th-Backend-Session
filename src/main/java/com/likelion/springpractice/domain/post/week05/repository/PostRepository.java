package com.likelion.springpractice.domain.post.week05.repository;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  // 게시글을 작성일 기준으로 최신순(내림차순) 정렬하여 전체 조회
  List<Post> findAllByOrderByCreatedAtDesc();

  // 게시글을 조회수 기준으로 높은 순(내림차순) 정렬하여 전체 조회 (인기순 정렬)
  List<Post> findAllByOrderByViewsDesc();

}
