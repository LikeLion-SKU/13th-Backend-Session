package com.likelion.springpractice.global.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import com.likelion.springpractice.global.response.BaseResponse;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice //이 클래스는 프로젝트 전체에서 던져진 CustomException을 자동으로 잡아서 JSON 응답으로 내려줌
public class GlobalExceptionHandler {

  // 커스텀 예외
  @ExceptionHandler(CustomException.class) //특정 예외 타입을 잡아서 처리하는 메서드 지정
  public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
    BaseErrorCode errorCode = ex.getErrorCode();
    log.error("Custom 오류 발생: {}", ex.getMessage());
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(BaseResponse.error(errorCode.getStatus().value(), ex.getMessage()));
  }

  // Validation 실패
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BaseResponse<Object>> handleValidationException(
      MethodArgumentNotValidException ex) {
    String errorMessages =
        ex.getBindingResult().getFieldErrors().stream()
            .map(e -> String.format("[%s] %s", e.getField(), e.getDefaultMessage()))
            .collect(Collectors.joining(" / "));
    log.warn("Validation 오류 발생: {}", errorMessages);
    return ResponseEntity.badRequest().body(BaseResponse.error(400, errorMessages));
  }

  // 예상치 못한 예외
  @ExceptionHandler(Exception.class)
  public ResponseEntity<BaseResponse<Object>> handleException(Exception ex) {
    log.error("Server 오류 발생: ", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(BaseResponse.error(500, "예상치 못한 서버 오류가 발생했습니다."));
  }

  //예외 핸들링 3개 추가

  //10주차 과제 3-1. HttpMessageNotReadableException 잘못된 JSON 형식으로 인한 파싱 실패
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<BaseResponse<Object>> handleInvalidJson(
      HttpMessageNotReadableException ex) {
    log.warn("잘못된 요청 본문(JSON 파싱 실패): {}", ex.getMessage());
    return ResponseEntity.badRequest().body(BaseResponse.error(400, "요청 본문 형식이 잘못되었습니다."));
  }

  //10주차 과제 3-2. AccessDeniedException – 권한 없음으로 인해 인가 실패
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<BaseResponse<Object>> handleAccessDeniedException(
      AccessDeniedException ex) {
    log.warn("접근 권한 없음: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(BaseResponse.error(HttpStatus.FORBIDDEN.value(), "접근 권한이 없습니다."));
  }


  //10주차 과제 3-3 MethodArgumentTypeMismatchException - 파라미터 타입 불일치
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<BaseResponse<Object>> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex) {
    String paramName = ex.getName();
    String requiredType =
        ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "알 수 없음";
    String errorMessage = String.format("요청 파라미터 '%s'는 '%s' 타입이어야 합니다.", paramName, requiredType);

    log.warn("파라미터 타입 불일치: {}", ex.getMessage());

    return ResponseEntity.badRequest()
        .body(BaseResponse.error(HttpStatus.BAD_REQUEST.value(), errorMessage));
  }
}

