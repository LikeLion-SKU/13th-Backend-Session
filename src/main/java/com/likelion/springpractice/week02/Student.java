package com.likelion.springpractice.week02;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Long age;


  private String department;

  @Column(name = "student_num")
  private Long studentNum;

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

  public Long getStudentNum() {
    return studentNum;
  }
}