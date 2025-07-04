package com.likelion.springpractice.domain.Like.repository;

import com.likelion.springpractice.domain.Like.entity.Like;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

  // 내가 누른 좋아요 전체 조회 (삭제되지 않은 것만)
  List<Like> findAllByUserIdAndIsDeletedFalse(Long userId);

  Optional<Like> findByUserIdAndFoodId(Long userId, Long foodId);
}
