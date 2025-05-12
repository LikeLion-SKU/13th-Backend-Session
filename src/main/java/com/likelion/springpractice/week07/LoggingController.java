package com.likelion.springpractice.week07;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoggingController {

  @GetMapping("/api/log-test")
  public String logTest() {
    log.trace("trace Level 로그입니다. - 가장 상세한 정보");
    log.debug("Debug Level 로그입니다. - 디버깅용");
    log.info("Info Level 로그입니다. - 일반 정보");
    log.warn("warn Level 로그입니다. - 경고");
    log.error("Error Level 로그입니다. - 오류 발생");

    return "로그 테스트 완료!";
  }

}
