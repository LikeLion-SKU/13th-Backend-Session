# SpringBoot 정리

#### [프로그래밍 명명 규칙]  
**camelCase**
- 변수, 메소드 이름에 사용
  PascalCase
- 클래스, 타입 등의 이름에 사용
  SNAKE_CASE
- 모든 단어의 문자가 소문자 or 대문자
- 상수명에 사용
---
### ✅ API
- **Application Programming Interface**의 약자
- 서로 다른 애플리케이션들이 소통할 수 있게 해주는 **중간 인터페이스**RESTful API

### ✅ RESTful API
- REST 아키텍처 스타일을 따르는 웹 API
- HTTP 메서드를 사용하여 자원의 상태를 주고받음

#### <HTTP 메소드>  
`GET` 데이터 조회   
`POST` 데이터 등록        
`PUT` 전체 수정        
`PATCH` 부분 수정       
`DELETE` 데이터 삭제   

---
### ✅ Gradle
- **오픈소스 빌드 자동화 도구**
- Java 프로젝트의 컴파일, 테스트, 배포 등을 자동으로 수행

### ✅ Gradle 주요 파일 구조
.gradle - gradle 버전 별 엔진 및 설정 파일
gradle/wrapper - Gradle을 설치하지 않아도 Gradle task를 실행할 수 있게 함
build.gradle - 의존성, 플러그인 설정 등 빌드에 대한 모든 기능 정의
gradlew & gradlew.bat - Unix&Windows용 실행 스크립트
settings.gradle - 프로젝트 설정 파일
---
### ✅ Bean
- **Spring Container**가 관리하는 객체
- 직접 `new` 키워드로 생성하지 않고, Spring이 객체를 주입함
- Spring Container에 등록됨(객체를 한 곳에서 관리하므로 유지보수가 쉬움)
- 동일한 객체를 재사용할 수 있음

#### <Bean 등록 방법>  
**수동 등록**
- 설정 파일에서 Bean 등록
- `@Configuration` + `@Bean` 사용
- 외부 라이브러리도 등록 가능
- 유지보수가 어려움

**자동 등록**
- 특정 어노테이션이 붙은 클래스는 자동으로 Bean에 등록됨
- 클래스에 `@Component`, `@Service`, `@Controller` 등을 붙이면 Spring이 자동 등록
- 코드가 간결해지고 유지보수가 쉬움
- 외부 라이브러리는 자동 등록 불가

#### <Bean 사용 방법>
Spring Container에 등록된 Bean은 `@Autowired`를 사용하여 자동으로 객체에 주입됨
사용자가 `new` 키워드를 사용할 필요 없이 Spring이 알아서 필요한 객체를 넣어줌
---
#### [MySQL]  
가장 널리 사용되고 있는 오픈소스 관계형 데이터베이스(RDBMS)  
테이블 - 관계형 데이터베이스 안에서 실제 데이터가 저장되는 형태
