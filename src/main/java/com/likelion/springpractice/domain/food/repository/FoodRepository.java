package com.likelion.springpractice.domain.food.repository;

import com.likelion.springpractice.domain.food.entity.Food;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
  Optional<Food> findByName(String name);
  List<Food> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String nameKeyword, String descKeyword);
}
