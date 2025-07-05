package com.likelion.springpractice.domain.mission.repository;

import com.likelion.springpractice.domain.mission.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

}
