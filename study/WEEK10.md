# 🎉10주차 과제

## ✅응답 통일

어플리케이션의 API가 클라이언트로 보내는 응답 형식을 일관되게 유지하는 작업
-> 클라이언트는 다양한 API를 호출하더라도 예상 가능한 구조로 응답 받음(사용성, 유지보수성 향상)

### ➡️응답 통일의 조건

1. BaseResponse<T> : 응답 틀(형식) 정의
2. Controller에서 응답 반환값을 BaseResponse.success()로 통일
3. 예외 처리에서 BaseResponse.error()로 통일

## ✅예외 처리

💡프로그램 오류에는 두 가지가 존재.

1. 컴파일 에러(컴파일 과정에서 생긴 오류)
2. 런타임 에러(런타임 과정에서 생긴 오류)

📌예외란 런타임 에러 도중 발생할 수 있는 예기치 않은 상황으로, 컴파일 에러가 아니므로 잠재적으로 발생하는 에러인 것

### ➡️예외 처리의 방법

1. **로컬 예외 처리** : try catch 구문을 통한 예외처리. 간단한 경우에는 유용하지만 여러 곳에 중복돼서 사용하면 유지보수가 어려움.
2. **전역 예외 처리** : BaseErrorCode, CustomException , @RestControllerAdvice(GlobalExceptionHandler에 사용)
   등을
   사용해 전역 예외를 한 곳에서 처리하고, 응답 포맷을 통일함(유지보수성 증가)

- **BaseErrorCode** : 에러 정보 구조 정의 인터페이스
- **GlobalErrorCode** 및 기능별 Errorcode : 실제 에러 코드 집합 (BaseErrorCode를 구현함)
- **CustomException** : 에러 정보를 담아 예외 객체를 생성하는 클래스
- **GlobalExceptionHander** : 예외가 발생했을 때 처리 담당(에러 코드를 꺼내서 BaseResponse.error(공통 에러) json 형식으로 생성)

## ✅응답 통일 & 예외 처리 흐름

> 1. 컨트롤러, 서비스 계층에서 예외 발생
> 2. Spring이 예외 감지
> 3. 핸들러 탐색
>> - 만약 현재 클래스에 @ExceptionHandler가 붙은 메서드가 있다면 우선 수행
>> - 그렇지 않다면 @RestControllerAdvice가 붙은 클래스 내부의 @ExceptionHandle 메서드를 찾아 수행
>> - 모두 없다면 Spring 기본 예외 처리 BasicErrorController로 넘어가 수행
> 4. ErrorCode를 이용해 BaseResponse 생성
> 5. 클라이언트에게 응답 반환