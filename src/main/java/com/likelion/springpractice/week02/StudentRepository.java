package com.likelion.springpractice.week02;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
  // JpaRepository<Student, Long>
  // Student : 이 Repository가 다룰 엔티티 클래스
  // Long : 엔티티의 기본 키(pk) 타입
}
