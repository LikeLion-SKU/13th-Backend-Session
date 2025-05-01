package com.likelion.springpractice.domain.post.week05.repository;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  // 최신순으로 모든 게시물을 조회하는 쿼리 메소드 생성
  List<Post> findAllByCreateAt_Time();

  // 조회수 많은순으로 모든 게시물을 조회하는 쿼리 메소드 생성
  List<Post> findAllByViews();
}
