package com.likelion.springpractice.domain.BadgeHistory.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BadgeHistoryErrorCode implements BaseErrorCode {
  
  BADGE_USER_UNAUTHORIZED("BADGE_4001", "배지 조회를 위해 사용자 정보가 필요합니다.", HttpStatus.UNAUTHORIZED);

  private final String code;
  private final String message;
  private final HttpStatus status;

}
