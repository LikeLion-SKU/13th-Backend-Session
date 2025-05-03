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

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(nullable = true)
  private String writer;

  // 조회수 필드 추가
  @Column(nullable = false)
  @Builder.Default
  private Long viewCount = 0L;

  // 게시글 수정 기능 (작성자와 조회수는 수정할 수 없는 값)
  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

  // 게시글 조회 시 조회수 증가 기능
  public void increaseViewCount() {
    this.viewCount++;
  }
}
