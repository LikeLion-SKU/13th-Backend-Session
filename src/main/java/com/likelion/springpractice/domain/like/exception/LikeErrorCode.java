package com.likelion.springpractice.domain.like.exception;


import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LikeErrorCode implements BaseErrorCode {
  ALREADY_LIKED("LIKE_4001", "이미 좋아요를 누른 게시글입니다.", HttpStatus.BAD_REQUEST),
  LIKE_NOT_FOUND("LIKE_4002", "좋아요를 누르지 않은 게시글입니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
