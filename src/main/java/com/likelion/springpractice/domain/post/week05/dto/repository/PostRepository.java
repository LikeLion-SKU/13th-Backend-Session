package com.likelion.springpractice.domain.post.week05.dto.repository;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
