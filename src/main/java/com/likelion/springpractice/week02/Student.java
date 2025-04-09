package com.likelion.springpractice.week02;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // 이 클래스가 JPA 엔티티임을 나타냄. 즉, 이 클래스는 DB의 테이블과 매핑됨.
@Table(name = "students") // 엔티티가 매핑할 테이블 이름을 지정.
public class Student {

  @Id // 해당 필드가 기본키 임을 나타낸다.(Id가 테이블의 기본키이다.)
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 값을 자동으로 생성하도록 설정 (자동 증가)

  private Long id;
  private String name;
  private Long age;
  private String department;
  private String student_num;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Long getAge() {
    return age;
  }

  public String getDepartment() {
    return department;
  }

  public String getStudent_num() {
    return student_num;
  }
}
