package com.likelion.springpractice.week03.wek03과제;

import com.likelion.springpractice.week02.Student;
import com.likelion.springpractice.week02.StudentRepository;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 클래스가 REST API controller임을 선언
@RequestMapping("/api") // API 엔드포인트 설정
public class MyController {

  private final StudentRepository studentsRepository; // 2주차 DB사용 목적

  public MyController(StudentRepository studentsRepository) {
    this.studentsRepository = studentsRepository;
  }

  @PostMapping("/studentId") // 학생 추가
  public String addStudent() {
    return "add student";
  }
/*
  @GetMapping("students") // 학생 조회
  public List<Student> getStudents() {
    return studentsRepository.findAll();
  }
*/
  @PutMapping("put-students") // 학생 전체 수정
  public String putStudent() {
    return "update all students";
  }

  @PatchMapping("patch-students") // 학생 부분 수정
  public String patchStudnet() {
    return "update students";
  }

  @DeleteMapping("delete-student") // 학생 삭제
  public String deleteStudent() {
    return "delete student";
  }

}
