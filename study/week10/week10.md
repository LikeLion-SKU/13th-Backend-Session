## 응답 통일

1. BaseResponse<T>로 응답 틀 정의
2. Controller에서 BaseResponse.success()로 통일
3. 예외 처리에서 BaseResponse.error()로 통일

## 예외 처리

- 프로그램 실행 시점에서 발생하는 에러로 컴파일러는 컴파일 시점에서 문법 오류나 오타같은 컴파일 시점에서 예측 가능한 오류는 잡아줄 수 있지만 실행 중 발생하는 잠재적인 에러까진 잡을 수 없음
- 로컬 예외 처리
    - try-catch 문 사용
- 전역 예외 처리
    - BaseErrorCode
    - CustomException
    - @RestControllerAdvice
    - → 응답 포멧 통일
- 예외와 에러는 다름
    - 예외는 런타임시 발생
        - 프로그램 실행 도중 개발자가 직접 해결x
    - 에러는 컴파일 시 발생
        - 발생 가능한 에러를 예측하여 오류 로그를 남겨줌, 개발자가 해결 가능

### BaseErrorCode

- 에러 정보 구조 정의
- 인터페이스 형태로 정의

### GlobalErrorCode

- 실제 에러 코드 집합
- 에러 종류 정의
- BaseErrorCode의 구현체

### CustonException

- 예외 객체
- 예외를 발생 시키며 에러 코드를 담음
- BaseErrorCode를 품고 있음

### GlobalExceptionHandler

- 예외가 발생했을 때 로지글 처리해줌
- 에러 코드를 꺼내서 BaseResponse를 생성
    - 예외도 객체다!

## 예외 처리 흐름도

controller, service 계츠에서 예외 발생 → Spring 예외 감지 → 핸들러 탐색 → GlobalExceptionHandler에서 예외 처리 → ErrorCode를 이용해 BaseResponse 생성 → 클라이언트에게 응답 반환

- 대표적인 예외 처리
    - @Valid : 유효성 검사
        - 사용자가 보낸 데이터가 조건에 맞는지 검사
        - DTO 클래스에 제약 어노테이션이 있어야 함
            - @NotBlank, @Email . . .