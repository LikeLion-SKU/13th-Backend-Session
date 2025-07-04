package com.likelion.springpractice.domain.Like.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LikeErrorCode implements BaseErrorCode {

  LIKE_NOT_FOUND("LIKE_4001", "해당 좋아요를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  LIKE_ALREADY_EXISTS("LIKE_4002", "이미 좋아요를 누른 항목입니다.", HttpStatus.CONFLICT);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
