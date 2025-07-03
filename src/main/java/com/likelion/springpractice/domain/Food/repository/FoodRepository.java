package com.likelion.springpractice.domain.Food.repository;

import com.likelion.springpractice.domain.Food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {

}