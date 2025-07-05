package com.likelion.springpractice.domain.mission.repository;

import com.likelion.springpractice.domain.mission.entity.Likes;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {

  Likes findByUserIdAndFoodId(Long userId, Long foodId);

  List<Likes> findAllByUserId(Long userId);

}
