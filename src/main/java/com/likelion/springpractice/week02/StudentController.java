package com.likelion.springpractice.week02;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 클래스가 REST API controller임을 선언
@RequestMapping("/api") // API 엔드포인트 설정
public class StudentController {

  private final StudentRepository studentRepository;

  public StudentController(StudentRepository studentRepository) { // 생성자
    this.studentRepository = studentRepository;
  }

  @GetMapping("/students") // http GET 요청을 처리
  public List<Student> getStudents() {
    return studentRepository.findAll(); // 레포지토리에 있는 모든 요소들이 담긴 리스트를 리턴
  }
}
