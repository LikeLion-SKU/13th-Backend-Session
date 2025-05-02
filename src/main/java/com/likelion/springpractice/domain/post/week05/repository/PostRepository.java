package com.likelion.springpractice.domain.post.week05.repository;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

  // List<Post> findByTitleAndWriter(String title, String writer);

  List<Post> findTop2ByTitle(String title);

  @Query("SELECT p FROM Post p WHERE p.title= :title")
  List<Post> findByTitle(@Param("title") String title);

  List<Post> findAllByOrderByCreatedAtDesc();

  List<Post> findAllByOrderByViewsDesc();
}
