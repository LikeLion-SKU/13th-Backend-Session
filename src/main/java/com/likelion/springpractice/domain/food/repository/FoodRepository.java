package com.likelion.springpractice.domain.food.repository;

import com.likelion.springpractice.domain.food.entity.Food;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodRepository extends JpaRepository<Food, Long> {


  @Query("SELECT f FROM Food f LEFT JOIN f.likes l GROUP BY f.id ORDER BY COUNT(l) DESC")
  List<Food> findAllOrderByLikeCountDesc();


}
