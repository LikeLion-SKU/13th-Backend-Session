# 2주차 과제

## 📖API와 Rest API

### ✅Application Programming Interface

- 서로 다른 소프트웨어, 시스템이 같은 규칙으로 상호작용 하도록 만든 표준

⚠️만약 표준을 정하지 않는다면 발생하는 문제

1. 매번 다른 규칙으로 인한 학습, 개발 속도 저하
2. 다른 플랫폼과 연동이 어려워지고, 클라이언트/서버 변경시 충돌 위험
3. 유지 보수 난이도 증가
4. 재사용성 저하

### ✅Rest API

- Rest 아키텍처 스타일에 맞는 API 형식
- Rest는 자원으로 URI로 표현하고, 자원에 대한 행위는 HTTP 메소드(GET, POST, PUT, PATCH, DELETE)로 표현

### ✅Rest의 6가지 원칙

1. 클라이언트 - 서버 구조의 명확함
2. 무상태 : 각 요청은 독립적, 서버는 클라이언트 상태 저장 X
3. 캐시 가능
4. 계층화
5. 일관된 인터페이스 : URI 구조와 HTTP 메소드 사용의 일관성
6. 코드 온 디맨드(필요한 경우 서버가 클라이언트에 실행 가능한 코드 전송)

### ✅Rest API 구성요소

- 자원 : URI로 표현
- 행위 : HTTP 메소드로 표현
- 표현 : JSON, XML 등의 데이터 형식

## ☑️API정리

**Application Programming Interface**의 약자로, 소프트웨어 시스템 간의 상호작용(예: 통신, 데이터 주고받기 등)을 위해
**표준화된 규칙과 방식**을 의미합니다. API에는 다양한 종류가 있는데,
예를 들어 **REST API**, **라이브러리가 제공하는 내부 API** 등이 있습니다.
그중**REST API**는**REST 원칙**을 따르는 API로, 주로 **HTTP 기반의 통신**에서 사용됩니다.
REST API는 자원(Resource)을**URI**로 표현하고,
해당 자원에 대한 작업은 **HTTP 메서드**(GET, POST, PUT, PATCH, DELETE 등)를 사용해 수행합니다.
그리고 자원의 표현 형식으로는 주로**JSON**이나**XML**같은 데이터 포맷이 사용됩니다.

## 📖Gradle

### ✅Gradle이란?

Gradle은 스프링부트 프로젝트 **빌드 도구**(**빌드**란 코드 컴파일, 라이브러리 다운, 어플리케이션 패키징 등을 의미)
스프링부트 프로젝트는 Gradle을 통해서 **어플리케이션 실행** 또는 **jar 파일 생성**이 가능.

### ✅Gradle 폴더 및 파일의 역할

- **build.gradle** : ⭐빌드 설정 파일⭐(라이브러리, 플러그인, Java 버전 등)
- **setting.gradle** : 프로젝트 이름 설정, 다중 모듈 관리
- **gradlew** : Gradle Wrapper 스크립트 (Linux, Mac 용 Gradle 명령 실행)
- **gradlew.bat** : Gradle Wrapper 스크립트 (Windows 용 Gradle 명령 실행)
- **gradle/** : Gradle Wrapper 실행을 위한 설정 파일 저장 디렉터리
- **build/** : 빌드가 끝난 결과물 생성 디렉터리
- **.gradle/** : 빌드 캐시 저장소(Gradle이 빌드 속도 높이려고 내부적으로 사용하는 디렉터리)

## ☑️Gradle 정리

**Gradle**은 빠르고 유연하게 코드 컴파일, 라이브러리 관리, 패키징 등을 해주는 **빌드 자동화 도구**.
복잡한 프로젝트도 편하게 관리할 수 있지만,
**초기 학습 난이도**가 높은 편, 스크립트 복잡성에 주의 해야한다.
**SpringBoot** 뿐만 아니라, Java, Kotlin, Android 등에서도 사용 가능한

## 📖Bean

### ✅Bean이란?

**Bean**이란, **Spring이 관리하는 객체**
일반적으로 객체는 개발자가 **new**를 통해서 생성하고, 직접 관리.
Spring에서는 개발자 대신 객체를 생성, 관리하고 필요한 곳에 주입해줌.
이런 식으로 Spring이 관리하는 객체를 Bean이라고 함.

### ✅왜 Bean이 필요한가?

**Bean**이 필요한 이유는 단순히 객체 생성이 목적이 아님.
작은 프로그램에서는 new를 통한 생성을 해도 되지만, 규모가 커지면 심각한 문제 초래.

- ⚠️심각한 문제들

1. 누가 이 객체를 만드는가?
2. 몇 번 이 객체를 만드는가?
3. 객체를 언제 없애야 하는가?
4. 누가 이 객체를 책임지는가?
   등과 같은 문제를 **개발자 대신 Spring**이 객체 관리를 해주고
   그런 객체를 **Bean**이라고 하는 것.

### ✅ Bean의 장점

1. 객체 관리(생성/소멸) 문제 해결
2. 의존성 주입 (아래 예시 코드로 익히기)
3. 싱글톤 적용(한 번 만든 객체를 재사용, 메모리 절약)
4. 코드 일관성, 유지 보수성, 가독성, 확장성 향상

## ☑️Bean 정리

**Bean**은 Spring이 관리하는 객체들을 의미하며,
Bean의 사용 목적은 단순 객체 생성이 아닌,
객체 관리, 의존성 주입, 객체 메모리 관리 등을 위해서 사용되고,
이는 코드의 일관성, 유지 보수성, 가독성, 확장성 향상 등 다양한 장점을 가진다.
소규모 프로그램이 아닌 대규모 서비스(=엔터프라이즈 급)를 위해서 만들어진 스프링 프레임워크에
필수적인 요소이다. (@Service, @Component, @Controller 등의 어노테이션으로 Bean 생성 가능)

## 🔥예시 코드

```java

@Service //@Service 어노테이션으로 인한 Spring에서 PaymentService 객체 생성, 관리 (1개 생성)
public class PaymentService {

  public void pay() {
    System.out.println("결제 완료");
  }
}

@Service //@Service 어노테이션으로 인해서 Spring에서 OrderService 객체 생성, 관리 (1개 생성)
public class OrderService {

  private final PaymentService paymentService;

  @Autowired  // @Autowired 어노테이션으로 인해 Bean에서 객체(의존성) 주입
  public OrderService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  public void order() {
    paymentService.pay();
  }
}
```
