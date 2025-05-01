package com.likelion.springpractice.domain.post.week05.repository;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  //5주차때 추가한 최신순 정렬과 조회수순 정렬
  List<Post> findAllByOrderByCreatedAtDesc();

  List<Post> findAllByOrderByViewCountDesc();
}
