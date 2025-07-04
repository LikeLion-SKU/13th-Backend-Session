package com.likelion.springpractice.domain.Food.repository;

import com.likelion.springpractice.domain.Food.entity.Food;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

  // 베스트 인기 순위
  @Query("SELECT f FROM Food f ORDER BY f.likeCount DESC")
  List<Food> findFoodsByLikeCountDesc();

  // 음식 전체 조회
  List<Food> findAll();

  // 검색어 조회 (LIKE 쿼리문 작성 필요)
  @Query("SELECT f FROM Food f WHERE f.name LIKE %:keyword% OR f.description LIKE %:keyword%")
  List<Food> searchByKeyword(@Param("keyword") String keyword);

  // 음식 상세(단일) 조회
  Optional<Food> findById(Long id);
}
