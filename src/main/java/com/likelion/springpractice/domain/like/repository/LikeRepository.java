package com.likelion.springpractice.domain.like.repository;

import com.likelion.springpractice.domain.food.entity.Food;
import com.likelion.springpractice.domain.like.entity.Like;
import com.likelion.springpractice.domain.like.entity.LikeId;
import com.likelion.springpractice.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, LikeId> {

  // 이미 존재하는 좋아요인지 -> 중복 방지 
  boolean existsByUserAndFood(User user, Food food);

  // 좋아요 조회 -> 기본키가 외래키 조합이라서 기본제공 말고 쿼리 메소드 씀
  Optional<Like> findByUserAndFood(User user, Food food);
  

  // 사용자에 따른 좋아요 리스트 조회 -> for 마이페이지 좋아요 리스트
  List<Like> findAllByUser(User user);

  // 인기순 조회하려면 음식에 따른 좋아요 수
  long countByFood(Food food);
}
