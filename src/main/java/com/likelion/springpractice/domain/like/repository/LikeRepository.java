package com.likelion.springpractice.domain.like.repository;

import com.likelion.springpractice.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

}
