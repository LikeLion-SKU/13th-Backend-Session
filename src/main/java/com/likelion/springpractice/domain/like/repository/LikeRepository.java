package com.likelion.springpractice.domain.like.repository;

import com.likelion.springpractice.domain.like.entity.Like;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

  Optional<Like> findByUserIdAndFoodId(Long userId, Long foodId);

  void deleteByUserIdAndFoodId(Long userId, Long foodId);

  List<Like> findAllByUserIdAndLikeStatusTrue(Long userId);
}
