package com.likelion.springpractice.week03;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

  // POST: 프로젝트 생성
  @PostMapping
  public String createProject() {
    return "프로젝트 생성";
  }

  // GET: 특정 ID의 프로젝트 조회
  @GetMapping("/{id}")
  public String readProject(@PathVariable Long id) {
    return "프로젝트 조회 - ID: " + id;
  }

  // PUT: 프로젝트 전체 정보 수정
  @PutMapping("/{id}")
  public String updatePutProject(@PathVariable Long id) {
    return "프로젝트 전체 수정 완료 - ID: " + id;
  }

  // PATCH: 프로젝트 이름만 수정 (일부 수정)
  @PatchMapping("/{id}/name")
  public String updatePatchProject(@PathVariable Long id) {
    return "프로젝트 이름 수정 완료 - ID: " + id;
  }

  // DELETE: 프로젝트 삭제
  @DeleteMapping("/{id}")
  public String deleteProject(@PathVariable Long id) {
    return "프로젝트 삭제 -  ID: " + id;
  }
}

/*
@PathVariable - URL 경로에 있는 값을 매개변수로 바인딩해준다.
{id} - URL 경로에 정의된 변수 이름
Long id - URL에서 들어오는 id값을 메서드 안에서 id라는 변수로 쓰기 위한 매개변수

ex. GET /api/projects/1
URL이 /projects/1 로 들어오면 5 라는 값을 Long id 에 바인딩해서 메서드 안에서 id 를 쓸 수 있게 해준다.
cf. URL 경로에 있는 변수 이름 {id}와 메서드 매개면수 이름 id는 같아야 한다. 다를 경우에는 명시해준다.
*/