package com.likelion.springpractice.domain.post.week04.entity;

import com.likelion.springpractice.global.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 이 클래스는 DB의 테이블과 매핑 됨.
@Getter
@Builder
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 인자를 받는 생성자 자동 생성
@Table(name = "post") // 매핑할 테이블 이름
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title; // 제목

  @Column(nullable = false)
  private String content; // 내용

  @Column
  private Long views; // 조회수

}
