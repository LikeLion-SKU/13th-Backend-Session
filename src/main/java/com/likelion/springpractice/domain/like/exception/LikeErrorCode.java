package com.likelion.springpractice.domain.like.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum LikeErrorCode implements BaseErrorCode {
  LIKE_NOT_FOUND("Like_4001", "존재하지 않는 좋아요입니다.", HttpStatus.NOT_FOUND);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
