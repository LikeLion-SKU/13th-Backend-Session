package com.likelion.springpractice.domain.Review.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

  REVIEW_NOT_FOUND("REVIEW_4001", "해당 리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  REVIEW_ALREADY_EXISTS("REVIEW_4002", "음식에 대한 리뷰가 이미 존재합니다.", HttpStatus.BAD_REQUEST),
  REVIEW_FORBIDDEN("REVIEW_4003", "후기 수정 권한이 없습니다.", HttpStatus.FORBIDDEN);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
