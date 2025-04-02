package com.likelion.springpractice.week2;

import jakarta.persistence.Column;
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

  @Column(name = "department")
  private String department; //학과

  @Column(name = "studentNum")
  private Long studentNum; //학번

  public String getDepartment() {
    return department;
  }

  public Long getStudentNum() {
    return studentNum;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Long getAge() {
    return age;
  }
}
