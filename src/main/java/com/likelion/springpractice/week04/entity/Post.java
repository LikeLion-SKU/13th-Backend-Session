package com.likelion.springpractice.week04.entity;

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
import org.hibernate.annotations.ColumnDefault;

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
  private String title; //제목

  @Column(nullable = false)
  private String content; //내용

  @ColumnDefault("0")
  @Column(nullable = false)
  private Integer views;

  //조회수 1 증가
  public void increaseViews() {
    this.views += 1;
  }

  //수정
  public void updatePost(String title, String content) {
    this.title = title;
    this.content = content;
  }


}
