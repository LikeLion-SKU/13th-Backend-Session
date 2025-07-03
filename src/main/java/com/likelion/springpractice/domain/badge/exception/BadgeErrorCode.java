package com.likelion.springpractice.domain.badge.exception;


import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BadgeErrorCode  implements BaseErrorCode {
  BADGE_NOT_FOUND("BADGE_4001", "존재하지 않는 배지입니다.", HttpStatus.NOT_FOUND);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
