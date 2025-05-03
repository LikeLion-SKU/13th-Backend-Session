package com.likelion.springpractice.domain.post.week05.repository;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllByOrderByViewDesc();

  List<Post> findAllByOrderByCreatedAtDesc();


}
