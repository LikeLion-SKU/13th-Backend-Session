package com.likelion.springpractice.domain.review.exception;


import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {
  REVIEW_NOT_FOUND("REVIEW_4041", "리뷰가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
  REVIEW_DELETE_FORBIDDEN("REVIEW_4031", "해당 리뷰를 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
