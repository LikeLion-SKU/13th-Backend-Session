### ✅API란?

- API는 **Application Programming Interface**의 약자로,  
  프로그램끼리 **데이터를 주고받기 위한 약속(인터페이스)**입니다.
- 예를 들어, 웹 브라우저가 서버에 데이터를 요청하거나 앱이 서버로부터 정보를 받아올 때 API가 사용됩니다.
- REST API 같은 형태가 대표적이며, URL, HTTP 메서드(GET, POST 등), 응답 형식(JSON 등)으로 구성됩니다.

---

### HTTP메소드

-Get: 데이터 조회

-Post: 데이터 등록

-Put: 데이터 전체 수정

-Patch: 데이터 부분 수정

-Delete: 데이터 삭제

---

### API URL 구성

1: HTTP메소드(Get,Post,Delete등등)

2: 프로토콜 (http:)

3: 도메인 (localhost)

4: 포트 번호(스프링부트기본 포트 8080)

5: API 엔드 포인트 (api/예시)

### 예시:

- 클라이언트: `GET /students`
- 서버 응답: `[{ "name": "홍길동", "department": "컴공과" }]`

---

## ✅ Gradle이란?

- **Gradle**은 자바 프로젝트의 **빌드 도구**로, 라이브러리 설치, 컴파일, 테스트, 배포 등을 자동으로 처리해줍니다.
- `build.gradle` 파일을 사용해 의존성(라이브러리)을 쉽게 관리할 수 있습니다.

---

### 설명!

-gradle: gradle 버전 별 엔진 및 설정 파일

-gradle/wrapper:  Gradle을 설치하지 않아도 Gradle task를 실행할 수 있게

-build.gradle: 의존성, 플러그인 설정 등 빌드에 대한 모든 기능 정의

-gradlew&gradlew.bat: Unix & Windows용 실행 스크립트

-settings.gradle:프로젝트 설정 파일

---

### ✅ Bean이란?

- **Bean**은 Spring Container가 관리하는 **객체(인스턴스)**를 의미합니다.
- 개발자가 `new` 키워드로 직접 객체를 만들지 않아도,  
  Spring이 대신 생성하고 관리해주기 때문에 **객체 생명 주기 관리와 재사용이 용이**합니다.
- Bean은 **한 곳(Spring Container)에서 관리되기 때문에**,  
  코드 유지보수가 쉬워지고, 테스트 및 확장도 편리해집니다.

---

### 🔧 Bean 등록 방식

- Bean은 **자동 등록**과 **수동 등록**이 있습니다.

등록 방식 설명
자동 등록 클래스에 `@Component`, `@Service`, `@Repository`, `@Controller` 등을 붙이면 Spring이 자동으로 Bean으로 등록
수동 등록: 개발자가 직접 `@Bean` 어노테이션을 사용하여 등록
참고: **외부 라이브러리 클래스**는 자동 등록이 불가능하므로 수동 등록 필요

---

### 💉 의존성 주입

- Spring은 등록된 Bean을 필요한 곳에 자동으로 주입해줍니다.
- `@Autowired` 어노테이션을 통해 주입할 수 있으며, 대표적인 3가지 방식이 있습니다:

---

### 방식설명

필드 주입  `@Autowired`를 필드에 직접 붙임 (권장 ❌)
**생성자 주입** : 생성자에 주입 (가장 권장 ✅)
Setter 주입 : Setter 메서드를 통해 주입
 
---