![image](https://github.com/user-attachments/assets/45c5068c-ebe0-4a20-84d5-cf3a9401a718)<details>
  
  <summary>10주차 미션</summary>

  <details>
    <summary>10주차 정리</summary>

## 응답 통일이란?
- 응답 통일은 애플리케이션의 API가 클라이언트(프론트엔드나 외부 서비스)로 
보내는 응답 형식을 일관되게 유지하는 작업
- 이를 통해 클라이언트는 다양한 API를 호출하더라도 예상 가능한 구조로 응답을 받을 수 있고, 
사용성과 유지보수성이 크게 향상된다.

### 응답 통일 조건
다음 3가지 요소가 함께 동작해야 함
1. BaseResponse<T> : 응답 틀 정의
2. Controller에서 BaseResponse.success(...)로 통일
3. 예외처리에서 BaseResonse.error(...)로 통일

### 프로그램 오류란?
프로그램 실행 중 어떤 원인에 의해 프로그램이 해당 상황에 대처를 하지 못할 경우 비정상적으로 종료되거나 에러 팝업창이 뜨는 경우
![image](https://github.com/user-attachments/assets/e4ccc57c-390f-4993-bca2-812757538c00)

## 예외 처리
### 예외란?
**예외(Runtime Error): 프로그램 실행 도중 발생할 수 있는 예기치 않은 상황**
- **프로그램 실행 시점에서 발생하는 에러**로 컴파일러는 컴파일 시점에서 문법 오류나 오타같은 
컴파일시점에서 예측가능한 오류는 잡아줄 수 있지만, **실행 중 발생할 수 있는 잠재적인 에러까지 잡을 순 없다.**

## 예외처리 방법 2가지
### 로컬 예외 처리
```java
로컬 예외 처리 (try-catch)
 try {
 // 예외가 발생할 수 있는 코드
} catch (Exception e) {
 // 예외 처리 로직
}
```
**간단한 경우엔 유용하지만, 여러 곳에서 중복될 수도 있고 유지보수가 어려움**
### 전역 예외 처리
- BaseErrorCode,CustomException, @RestControllerAdvice등을 사용해 애플리케이션 전역에서 발생하는 예외를 한 곳에서 처리하고, 응답 포맷을 통일

![image](https://github.com/user-attachments/assets/98a4c987-92b9-4a10-aece-1fdd29ed90f4)
![image](https://github.com/user-attachments/assets/d80f7187-12f5-4832-a269-c17a36a48049)

### 예외 처리 핵심 코드
- BaseErrorCode
- GlobalErrorCode
- CustomException
- GlobalExceptionHandler

### BaseErrorCode
![image](https://github.com/user-attachments/assets/5694fa6a-3236-4d9f-84ae-d57c8be89369)

### GlobalErrorCode
![image](https://github.com/user-attachments/assets/b80def63-099c-489a-b3b1-0b7956605167)

### CustomException
![image](https://github.com/user-attachments/assets/b781a269-ec3d-4e34-ab51-c756a7bb6894)

### GlobalExceptionHandler
![image](https://github.com/user-attachments/assets/3403f26c-c8ba-4331-ae30-78df05276f84)
![image](https://github.com/user-attachments/assets/5ef1d1e8-79ff-41e9-b3e1-c1bd68d9be9b)
![image](https://github.com/user-attachments/assets/b207f699-b824-4206-aced-c1309259ca5c)

## 예외 처리 흐름도
**컨트롤러, 서비스 계층에서 예외 발생 -> Spring이 예외 감지 -> 핸들러 탐색 -> GlobalExceptionHandler에서 예외 처리 -> ErrorCode를 이용해 BaseResponse 생성 -> 클라이언트에게 응답 반환**

- **CustomException은 PostErrorCode, GlobalErrorCode 같은 에러 정보를 담는 그릇, CustomException이 에러코드를 Handler에게 넘겨줌**
- **throw new CustomException(...)하면 -> 예외와 함께 에러코드가 GlobalExceptionHandler로 전달**
- **GlobalExceptionHandelr는 거기서 코드, 메시지, 상태값 꺼내서 -> JSON 응답을 만듬**

## 대표적인 예외 처리
### 유효성 검사(@Valid)
- 사용자가 보낸 데이터가 조건에 맞는지 검사하는 것
- Spring에서는 클라이언트가 Controller에 요청을 보냈을 때, 요청 본문이 DTO에 매핑되면서 유효성 검사가 동시에 자동으로 진행
- implementation 'org.springframework.boot:spring-boot-starter-validation'
- **DTO 클래스에 제약 어노테이션이 있어야 함 ex) @NotBlank, @Email, @Min, @Size..**
- **Controller에서 @Valid를 사용해야 함**
- **조건 만족 시 Spring이 자동으로 검증함 → 실패 시 MethodArgumentNotValidException 발생**

![image](https://github.com/user-attachments/assets/af822b5a-46b8-42e1-8ac1-40df6eeef742)
![image](https://github.com/user-attachments/assets/fcf4ded6-3f3f-45e9-b3f4-4b84fbd293f3)
![image](https://github.com/user-attachments/assets/c469cb57-1cbb-4023-a0c7-b40c19c95b13)
![image](https://github.com/user-attachments/assets/f952c10c-e407-452a-a9ff-04aef9916d4c)

  </details>
</details>
