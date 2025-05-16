# 📌SpringBoot에서 Logging

## ➡️Loging이란?

로그를 남기는 행위 또는 기술을 뜻 함. 소프트웨어 시스템에서 발생하는 모든 행위 및 이벤트 정보를 기록하는 작업.

## ➡️SLF4J + Logback

스프링 부트에서는 SLF4J를 통해서 로깅 API를 통일하고, 실제 로깅 구현체는 Logback을 통해서 사용.

```java
// build.gradle
implementation 'org.springframework.boot:spring-boot-starter'
```

## ➡️Log Level

1. tace : 가장 상세한 로깅
2. debug : 디버깅 목적의 정보
3. info : 일반적인 정상 흐름
4. warn : 경고, 하지만 서비스는 계속 동작
5. error : 예외 발생, 기능 실패 등

* 운영환경에서 너무 많은 로그는 성능에 악영향. 따라서 Info 이상만 출력.
* 개발 환경에서는 자세한 로그가 필요. 따라서 Debug, trace까지 활성화하기도 함.

## ➡️AOP란?

Aspect Oriented Programming(관점 지향 프로그래밍)의 약자로,
핵심 비즈니스 로직과 별개의 부가 기능들의 관점에서 코드를 분리하여 재사용성과 유지보수성을 높이는 방식

# 📌SpringBoot에서 자주 발생하는 오류 로그

### 1. BeanCreationException, NoSuchBeanDefinitionException

- 의존성 주입 실패 오류, Bean이 컨테이너에 등록되지 않음
- Bean이 등록되지 않았거나, 잘못된 타입
- 💡Bean 등록 어노테이션 확인

### 2. HttpMessageNotReadableExceptio

- JSON 형식 파싱 실패 시 발생
- 💡 요청 본문 구조가 DTO와 일치하는지 확인

### 3. DataIntegrityViolationException

- DB 제약조건 위반
- 💡입력값 확인, DB컬럼 제약조건과 맞는지 확인

### 4. NullPointerException

- null 객체에 메서드나 필드를 접근할 떄
- 💡해당 오류가 발생하는 문장 확인하기

### 5. IndexOutOfBoundsException

- List, 배열에서 유효하지 않은 인덱스를 참조할 때
- 💡해당 오류가 발생하는 문장 확인, List 범위 확인