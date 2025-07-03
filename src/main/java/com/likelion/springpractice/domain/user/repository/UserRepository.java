package com.likelion.springpractice.domain.user.repository;

import com.likelion.springpractice.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username); // 로그인 및 조회

  // boolean existsByUsername(String username);  // 회원가입 시 닉네임 중복 검사 -> 필요 없을 듯?

  Optional<User> findByEmail(String email);   // 로그인 및 조회 

  boolean existsByEmail(String email);  // 회원가입 시 이메일 중복 검사

  Optional<User> findByRefreshToken(String refreshToken);   // refreshToken 재발급 시


}
