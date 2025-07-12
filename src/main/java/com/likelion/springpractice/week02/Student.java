package com.likelion.springpractice.week02;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Long age;
  private String department;
  private String studentNum; // 카멜케이스로 생성, MySQL은 보통 student_num 처럼 스네이크 케이스로 작성

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

  public String getStudentNum() {
    return studentNum;
  }
}
