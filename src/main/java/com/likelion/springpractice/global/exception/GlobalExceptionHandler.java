package com.likelion.springpractice.global.exception;

import com.likelion.springpractice.global.Response.BaseResponse;
import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 커스텀 예외
  @ExceptionHandler(CustomException.class)
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

  // 잘못된 HTTP Method 사용 처리
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<BaseResponse<Object>> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException ex) {
    log.warn("지원하지 않는 HTTP Method: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(BaseResponse.error(HttpStatus.METHOD_NOT_ALLOWED.value(), "지원하지 않는 HTTP Method입니다."));
  }

  // 예외 핸들링 3개 추가
  // 1. AccessDeniedException – 권한 없음으로 인한 인가 실패
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<BaseResponse<Object>> handleAccessDeniedException(
      AccessDeniedException ex) {
    log.warn("접근 권한 없음: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(BaseResponse.error(HttpStatus.FORBIDDEN.value(), "접근 권한이 없습니다."));
  }

  // 2. MissingServletRequestParameterException - 필수 쿼리 파라미터 누락
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<BaseResponse<Object>> handleMissingRequestParam(
      MissingServletRequestParameterException ex) {
    String errorMessage = String.format("필수 요청 파라미터 누락: %s", ex.getParameterName());
    log.warn(errorMessage);
    return ResponseEntity.badRequest()
        .body(BaseResponse.error(HttpStatus.BAD_REQUEST.value(), errorMessage));
  }

  // 3. MissingPathVariableException - @PathVariable 값 누락
  @ExceptionHandler(MissingPathVariableException.class)
  public ResponseEntity<BaseResponse<Object>> handleMissingPathVariable(
      MissingPathVariableException ex) {
    log.warn("PathVariable 누락: {}", ex.getVariableName());
    return ResponseEntity.badRequest()
        .body(BaseResponse.error(400, "필수 경로 변수(" + ex.getVariableName() + ")가 누락되었습니다."));
  }

  // 4. MethodArgumentTypeMismatchException - 파라미터 타입 불일치
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
