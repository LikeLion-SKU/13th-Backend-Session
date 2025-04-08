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
@RequestMapping("/api")
public class MyController {

  // 회원 관련 API
  @PostMapping("/members")
  public String recruitMember() {
    return "새로운 동아리원이 가입했습니다!";
  }

  @GetMapping("members/{memberId}")
  public String viewMember(@PathVariable Long memberId) {
    return "동아리원 #" + memberId + "의 정보를 조회했습니다.";
  }

  @PatchMapping("members/{memberId}")
  public String patchMember(@PathVariable Long memberId) {
    return "동아리원 #" + memberId + "의 정보가 일부 수정되었습니다.";
  }

  @PutMapping("members/{memberId}")
  public String updateMember(@PathVariable Long memberId) {
    return "동아리원 #" + memberId + "의 정보가 모두 수정되었습니다.";
  }

  @DeleteMapping("members/{memberId}")
  public String deleteMember(@PathVariable Long memberId) {
    return "동아리원 #" + memberId + "이 탈퇴했습니다.";
  }

  // 공지 관련 API
  @PostMapping("notices")
  public String createNotice() {
    return "새로운 공지가 등록되었습니다!";
  }

  @GetMapping("notices/latest")
  public String viewLatestNotice() {
    return "가장 최근 공지를 열람했습니다.";
  }

  @PatchMapping("notices/{noticeId}")
  public String patchNotice(@PathVariable Long noticeId) {
    return "공지 #" + noticeId + "의 일부가 수정되었습니다.";
  }

  @DeleteMapping("notices/{noticeId}")
  public String deleteNotice(@PathVariable Long noticeId) {
    return "공지 #" + noticeId + "가 삭제되었습니다.";
  }
}
