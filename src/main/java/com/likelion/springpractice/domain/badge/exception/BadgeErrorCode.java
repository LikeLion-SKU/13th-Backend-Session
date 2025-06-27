package com.likelion.springpractice.domain.badge.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BadgeErrorCode implements BaseErrorCode {
  BADGE_NOT_FOUND("BADGE_404_01", "해당 배지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  ALREADY_GRANTED("BADGE_400_01", "이미 획득한 배지입니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;
}