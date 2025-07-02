package com.likelion.springpractice.domain.review.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

  REVIEW_NOT_FOUND("REVIEW_4041", "해당 후기를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
  INVALID_REVIEW_CONTENT("REVIEW_4001", "리뷰 내용은 공백 불가합니다", HttpStatus.BAD_REQUEST),
  INVALID_REVIEW_SCORE("REVIEW_4002", "리뷰 점수는 0 ~ 5 사이입니다", HttpStatus.BAD_REQUEST);
  private final String code;
  private final String message;
  private final HttpStatus status;
}
