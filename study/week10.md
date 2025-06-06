# 10주차 세션

다중 선택: 매주 세션

<aside>
✏️

### 응답 통일

애플리케이션의 API가 클라이언트로 보내는 응답 형식을 일관되게 유지하는 작업

→ 클라이언트가 예상 가능한 구조로 응답 받을 수 있고 사용성, 유지보수성이 향상

</aside>

<aside>
✏️

### 프로그램 오류

프로그램 실행 중 특정 원인에 의해 프로그램이 대처하지 못하여 원래 의도한대로 작동 X or 비정상적 종료 or 에러 팝업창 뜨는 경우

- 구문 오류 : 흔히 문법을 지키지 않아서 발생하는 오류
- 논리 오류 : 문법적으로는 문제 X BUT 로직이 잘못되어서 의도한대로 결과가 나오지 않는 오류
- 컴파일 오류 : 코드가 실행되기 전에 컴파일 과정에서 발생하는 오류 ex) 구문오류
- 런타임 오류 : 프로그램 실행 중에 발생하는 오류

컴파일 에러는 컴파일러가 잡아줄 수 있으나 런타임 에러는 프로그램 실행 중의 오류이기 때문에 컴파일러가 잡아줄 수 없어서 예외처리가 필요한 것

</aside>

<aside>
✏️

### 예외처리 방법

1. 로컬 예외 처리

   ex) try-catch 문

2. 전역 예외 처리

   : 스프링에서 사용할 방법, 애플리케이션 전역에서 발생하는 예외를 한곳에서 처리하는 방식

   ex) BaseErrorCode, CustomException, @RestControllerAdvice

</aside>

<aside>
✏️

### 예외 처리 핵심 코드

```java
package com.likelion.springpractice.global.exception.model;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

  String getCode();

  String getMessage();

  HttpStatus getStatus();
}
```

```java
package com.likelion.springpractice.global.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {
  // 가장 대표적인 에러코드 세가지
  INVALID_INPUT_VALUE("GLOBAL001", "유효하지 않은 입력입니다.", HttpStatus.BAD_REQUEST),
  RESOURCE_NOT_FOUND("GLOBAL002", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  INTERNAL_SERVER_ERROR("GLOBAL003", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

  private final String code;
  private final String message;
  private final HttpStatus status;
}

```

```java
package com.likelion.springpractice.global.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final BaseErrorCode errorCode;

  public CustomException(BaseErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}

```

```java
package com.likelion.springpractice.global.exception;

import com.likelion.springpractice.global.exception.model.BaseErrorCode;
import com.likelion.springpractice.global.response.BaseResponse;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 커스텀 예외
  @ExceptionHandler(CustomException.class) // 핸들러라는것을 명시적으로 표현하는 애너테이션 
  public ResponseEntity<BaseResponse<Object>> handleCustomException(
      CustomException ex) { // ex에서 에러코드들이 나옴
    BaseErrorCode errorCode = ex.getErrorCode();
    log.error("Custom 오류 발생: {}", ex.getMessage());
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(BaseResponse.error(errorCode.getStatus().value(), ex.getMessage()));
  }

  // Validation 실패 - 유효성 처리 실패 시의 예외처리 코드
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
}

```

</aside>

<aside>
✏️

### 유효성 검사

사용자가 입력한 데이터가 조건에 부합하는지 검사하는 것

Spring에서는 클라이언트가 Controller에 요청을 보냈을때 해당 요청이 DTO에 매핑

→ 유효성 검사 자동 진행

DTO 클래스에 제약하는 어노테이션이 있어야 한다

```java

@NotBlank(message = "제목은 비어 있을 수 없습니다.")
@Schema(description = "게시글 제목", example = "1주차 세션???")
private String title;
```

이후 Controller 에서 @Valid 필요

→ 조건 만족 시 : Spring이 자동 검증 / 실패 시 : MethodArgumentNotValidException 발생

</aside>