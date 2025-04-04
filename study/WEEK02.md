# Spring Boot (Week 2)

## API

> 서로 다른 애플리케이션이 소통할 수 있도록 해주는 **인터페이스**

- 애플리케이션 간 **데이터 교환**을 위해 사용
- Spring Boot에서는 주로 **REST API** 형태로 제공

---

### RESTful API
> REST (Representational State Transfer) 아키텍처 스타일을 따르는 웹 API

- 자원을 URI로 표현하고
- HTTP 메서드를 사용해 자원의 상태를 주고받음

| HTTP 메서드 | 설명 | 예시 |
|-------------|------|------|
| `GET` | 데이터 조회 | `/users` |
| `POST` | 데이터 생성 | `/users` |
| `PUT` | 데이터 수정 | `/users/1` |
| `DELETE` | 데이터 삭제 | `/users/1` |

---

### RESTful API URL 구성

```plaintext
https://도메인/api/리소스명/{id}
```

example:
```plaintext
https://example.com/api/users/2
```

---

### API 예시

```java
@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String Hello() {
        return "Hello, World!";
    }
}
```

---

## Gradle

> **오픈 소스 빌드 자동화 도구**

- 프로젝트의 **컴파일, 테스트, 패키징, 배포**를 자동으로 수행
- **의존성 관리**, 빌드 설정 등을 손쉽게 구성할 수 있음

---

### 빌드 도구
> 소스 코드를 실행 가능한 애플리케이션으로 만들어주는 도구

- 빌드 자동화 도구를 통해 반복 작업을 줄이고 생산성을 향상시킬 수 있음

---

### Gradle 주요 파일 구조 및 역할

| 파일/디렉토리 | 설명 |
|---------------|------|
| `build.gradle` | 의존성, 플러그인, 빌드 설정 파일 |
| `settings.gradle` | 루트 프로젝트 이름 및 모듈 구성 파일 |
| `gradlew`, `gradlew.bat` | Gradle Wrapper 실행 스크립트 (Unix/Windows) |
| `gradle/wrapper/` | Gradle 버전 정보 및 Wrapper 설정 포함 디렉토리 |

**Gradle Wrapper** 덕분에 로컬에 Gradle을 설치하지 않아도 빌드 실행 가능

---

### build.gradle 예시

```groovy
plugins {
    id 'org.springframework.boot' version '3.1.0'
    id 'io.spring.dependency-management' version '1.1.0'
    id 'java'
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

---

## Bean

> Spring Container가 **생성하고 관리하는 객체**

- 일반적으로 `new` 키워드 없이 객체를 사용
- Spring이 객체를 미리 생성해두고 필요할 때 주입
- 프로젝트 전역에서 객체를 재사용하고 관리할 수 있음

---

### Bean의 장점

- **객체 재사용 가능** → 메모리 효율성
- **중앙 집중 관리** → 코드 유지보수 쉬움
- **의존성 주입**(DI)을 통해 결합도 낮춤

---

### Bean 등록 방법

#### 1. 자동 등록

Spring이 지정된 어노테이션이 붙은 클래스를 자동으로 스캔하여 Bean으로 등록

| 어노테이션 | 설명 |
|------------|------|
| `@Component` | 기본 컴포넌트 등록 어노테이션 |
| `@Service` | 서비스 로직 처리 클래스 |
| `@Controller` | 웹 요청 처리 클래스 |
| `@Repository` | DB 접근 클래스 |

> 컴포넌트 스캔 범위 안에 있어야 자동 등록됨

---

#### 2. 수동 등록

직접 Java 설정 파일을 통해 Bean으로 등록

```java
@Configuration
public class AppConfig {
    
    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

- 외부 라이브러리나 커스텀 설정 시 사용
- 자동 등록보다 복잡하고 설정 코드가 많음

---

### Bean 사용 방법

> Spring Container에 등록된 Bean은 **의존성 주입(DI)**을 통해 사용

#### 생성자 주입 (권장)

```java
@Service
public class MyService {
    public String hello() {
        return "Hello Bean!";
    }
}

@RestController
public class MyController {

    private final MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/bean")
    public String getHello() {
        return myService.hello();
    }
}
```

#### 필드 주입 (비추천)

```java
@Autowired
private MyService myService;
```

- 테스트 및 유지보수에 불리함
- 생성자 주입 방식이 가장 권장됨

---