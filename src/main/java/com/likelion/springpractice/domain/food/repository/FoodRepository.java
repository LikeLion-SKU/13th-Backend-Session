package com.likelion.springpractice.domain.food.repository;

import com.likelion.springpractice.domain.food.entity.Food;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

  // 음식 이름으로 전체 조회
  List<Food> findAllByFoodName(String foodName);

  // 음식 하드코딩 되어있으니깐 굳이 중복 검사 필요없을듯?
  //boolean existsByFoodName(String foodName);

  // 음식명 부분 검색
  // List<Food> findAllByFoodNameContaining(String foodName); -> ㄱㄷ
}
