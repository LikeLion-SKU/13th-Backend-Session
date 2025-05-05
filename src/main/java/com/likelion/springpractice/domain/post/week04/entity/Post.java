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
  private int views = 0; // 조회수

  public void incrementViews() { // 조회수 증가
    views++;
  }

  public void update(String title, String content) { // 게시글 제목, 내용 수정
    this.title = title;
    this.content = content;
  }
}
