package com.likelion.springpractice.domain.review.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
  REVIEW_NOT_FOUND("REVIEW_4041", "해당 리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  INVALID_REVIEW_RATING("REVIEW_4001", "평점은 1~5 사이 정수입니다..", HttpStatus.BAD_REQUEST),
  INVALID_REVIEW_COMMENT("REVIEW_4002", "리뷰 내용은 필수입니다.", HttpStatus.BAD_REQUEST),
  COMMENT_TOO_LONG("REVIEW_4003", "리뷰 내용은 500자 이하로 작성해야 합니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
