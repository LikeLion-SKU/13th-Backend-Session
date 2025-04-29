package com.likelion.springpractice.domain.post.week05.repository;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByViewsDesc();

    List<Post> findAllByOrderByCreatedAtDesc();
    
}
