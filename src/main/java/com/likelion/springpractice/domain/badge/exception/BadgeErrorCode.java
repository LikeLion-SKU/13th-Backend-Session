package com.likelion.springpractice.domain.badge.exception;


import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BadgeErrorCode implements BaseErrorCode {
  BADGE_ERROR_CODE("BADGE_4041", "해당 뱃지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
