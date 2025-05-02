package com.likelion.springpractice.week05.repository;

import com.likelion.springpractice.week04.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  //최신순 정렬
  List<Post> findAllByOrderByCreatedAtDesc();

  //조회수 많은 순으로 정렬
  List<Post> findAllByOrderByViewsDesc();

}
