package com.likelion.springpractice.domain.post.week05.repository;

import com.likelion.springpractice.domain.post.week04.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

  //JPA는 메소드 이름으로 쿼리를 생성하는 "쿼리 메서드" 기능을 제공함!!
  //따라서, 메소드의 이름을 분석해, JPQL 쿼리를 실행함!!
  //따라서, 메소드의 이름을 규칙에 맞게 잘 작성해야하고, 복잡한 경우엔 @Query를 쓴다.
  List<Post> findAllByOrderByViewsDesc();

  List<Post> findAllByOrderByCreatedAtDesc();
}
