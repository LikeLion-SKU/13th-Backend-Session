## 1. API란?

- API (Application Programming Interface)는...
- 목적: 소프트웨어끼리 **데이터나 기능을 주고받을 수 있게 함**

### 📌 핵심 개념
- Endpoint: API 요청을 보내는 URL
- Request / Response: 주고받는 데이터 구조

### 🧩 REST API란?

- REST API는 **HTTP 기반의 API 설계 방식**으로, 웹에서 가장 많이 사용되는 구조.
- REST는 **REpresentational State Transfer**의 약자로, **자원을 URI로 표현하고**, **HTTP 메서드로 행위를 정의**하는 방식임.

#### 📌 특징
- **클라이언트-서버 구조**  
  - 사용자(클라이언트)와 서버가 분리되어 있음
- **무상태성 (Stateless)**  
  - 요청 간에 서버는 클라이언트의 상태를 저장하지 않음
- **자원 기반 (Resource-Oriented)**  
  - URL은 어떤 **자원**을 가리키는지 표현함
- **표준 HTTP 메서드 사용**
  - `GET`: 자원 조회
  - `POST`: 자원 생성
  - `PUT`: 자원 전체 수정
  - `PATCH`: 자원 일부 수정
  - `DELETE`: 자원 삭제

#### ✅ 예시
```http
GET /students           → 전체 학생 목록 조회  
GET /students/1         → ID가 1인 학생 조회  
POST /students          → 새로운 학생 추가  
PUT /students/1         → ID가 1인 학생 전체 정보 수정  
DELETE /students/1      → ID가 1인 학생 삭제
```
## 2. Gradle이란?

- **Gradle**은 자바 프로젝트에서 많이 쓰이는 **빌드 자동화 도구**
- 복잡한 설정 없이 의존성 관리, 빌드, 테스트 등을 쉽게 할 수 있게 도와줌

---

### 🔧 Gradle의 특징

- **Groovy/Kotlin DSL을 사용**해 빌드 스크립트 작성
- groovy는 java기반 스크립트 언어
- kotlin은 jetsbrain에서 만든 정적 타입 언어
- 빌드 수행 시, 필요한 라이브러리(의존성)를 인터넷에서 자동으로 받아줌
- 명령 한 줄로 프로젝트 빌드 가능:  
  ```bash
  ./gradlew build
  ```

---

### 📦 의존성(Dependency) 관리

- `build.gradle` 파일에 필요한 라이브러리를 적으면, Gradle이 자동으로 다운받아줌
- 예시:
  ```groovy
  dependencies {
      implementation 'org.springframework.boot:spring-boot-starter-web'
  }
  ```

---

### ⚔️ Gradle vs Maven

| 항목         | Gradle                              | Maven                              |
|--------------|--------------------------------------|-------------------------------------|
| 빌드 언어    | Groovy/Kotlin DSL                    | XML                                 |
| 속도         | 더 빠름 (캐싱, 병렬 처리 지원)       | 상대적으로 느림                    |
| 유연성       | 매우 유연함                          | 표준화에 초점                       |
| 가독성       | DSL 기반으로 더 간결함               | XML 문법이 길고 복잡할 수 있음      |
| 설정 방식    | 코드 기반                             | 선언 기반 (설정 파일이 더 딱딱함)    |

---

### ✅ 요약

> Gradle은 **빠르고 유연한 빌드 도구**로, 특히 **Spring Boot 같은 자바 프로젝트에서 많이 사용**됨.  
> Maven보다 설정이 자유롭고, 최신 트렌드에 더 적합해서 많은 프로젝트들이 Gradle을 채택하고 있음.

## 3. Bean이란?

> **Bean은 Spring이 대신 생성하고 관리해주는 객체를 의미**함.  
> 우리가 직접 객체를 만들지 않아도, 스프링이 필요한 곳에 자동으로 주입해줌 (DI).
> DI = Dependency Injection : 의존성 주입
> 다른 객체를 직접 만들지 않고 외부에서 주입하는 방식

---

### 🏷️ Bean 등록 방법

| 어노테이션 | 설명 |
|------------|------|
| `@Component` | 일반 클래스 등록 |
| `@Service` | 비즈니스 로직을 담당하는 서비스 계층 |
| `@Repository` | DB와 연결되는 Repository 계층 |
| `@Controller`, `@RestController` | 웹 요청을 처리하는 계층 |

---

### ✅ 요약

- **Bean = Spring이 관리하는 객체**
- 필요한 곳에 자동으로 넣어줘서 (의존성 주입), 개발자가 일일이 객체를 만들 필요가 없음

