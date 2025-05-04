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

//이 엔티티 객체는 DB에서 조회,저장,삭제,수정 될 수 있다!!
//엔티티 클래스는, 실제 DB테이블과 매칭될 클래스이다!!
@Entity  //이 클래스가 엔티티 클래스임을 밝히는 어노테이션!! Spring Data JPA에선 반드시 추가해야한다.
@Getter
@Builder //빌더 패턴을 적용할 수 있게 해줌!!
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")  //이 엔티티가 메핑될 실제 데이터베이스 테이블을 지정!!
public class Post extends BaseTimeEntity { //Post클래스는 게시글 하나를 표현함!!
  //BaseTimeEntity를 상속받아서, 생성일/수정일 필드도 함께 상속받음!!

  @Id  //해당 필드를 기본키로 지정해주기 위한 어노테이션!!
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  //자동으로 ID를 생성해주는 어노테이션!! IDENTITY는, AUTO_INCREMENT처럼 자동으로 번호를 붙인다!!
  private Long id;

  //title이라는 멤버변수를, DB테이블의 하나의 열(Column)으로 매핑하라는 뜻!!
  @Column(nullable = false) //nullable = false이면, 이 컬럼엔 절대 null이 들어가면 안된다는 것을 뜻함!!
  private String title;  //제목

  @Column(nullable = false)
  private String content;  //내용

  @Column(nullable = false)
  private Integer views; //조회수

  //게시글 수정시 사용하는 메소드
  public void update(String title, String content) {
    this.title = title;
    this.content = content;
    //this.views = views;  인자 마지막에 Long views
  }

  //게시글 단일 조회시 조회수 증가 메소드
  public void increaseViews() {
    //this.views = views + 1; , 인자 Long views (수정 전 코드)
    this.views++;
  }
}
