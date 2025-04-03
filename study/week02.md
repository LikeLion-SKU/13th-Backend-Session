# **목차**

1. [API](#api)   

2. [Gradle](#gradle)

3. [Bean](#bean)   

&nbsp;
# API
### 🌐 **API란?**

API(Application Programming Interface)는 프로그램 간 소통을 돕는 인터페이스

프로그램은 API를 통해 다른 프로그램이나 서비스에 원하는 작업을 요청하고 결과를 받을 수 있다.

&nbsp;
### ☁️ **RESTful API란?**

API 작동 방식에 대한 조건을 부과하는 소프트웨어 아키텍쳐인 REST(Representational State Transfer) 아키텍쳐를 따르는 웹 서비스로,

인터넷 같은 복잡한 네트워크에서 통신을 관리하기 위해 등장하였다.

> **HTTP URI**를 통해 어떤 자원에 접근할 건지 명시하고, → `/student`, `/user`: 명사  
> **HTTP Method**를 통해 해당 자원에 대한 어떤 동작을 할 건지 구분하여   
> **CRUD 기능**을 설계 → `POST`(Create), `GET`(Read), `PUT, PATCH`(Update), `DELETE`(Delete) : 동사

&nbsp;
### 💯 **Spring Boot에서 RESTful API 만드는 법**

> `GET /api/students`를 요청하면 학생 전체 목록을 JSON으로 반환하는 API

```java
@RestController
@RequestMapping("/api")
public class StudentController {

  private final StudentRepository studentRepository;

  public StudentController(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @GetMapping("/students")
  public List<Student> getStudents() {
    return studentRepository.findAll();
  }
}
```

**설명**

💡 `@RestController` - REST API를 만들고 JSON으로 반환하는 클래스 (`@Controller + @ResponseBody` 조합)

💡 `@RequestMapping("/api")` - 기본 경로: 메서드마다 붙는 URL 앞에 `/api` 자동 추가

💡 생성자 주입 - StudentRepository는 Spring이 관리하는 Bean이므로 생성자에 자동 주입 DI⭐

💡 `@GetMapping("/students")` - 전체 경로: `/api/students`에 GET 요청이 오면 이 메서드 실행

💡 `studentRepository.findAll()` - DB에 저장된 학생 목록을 리스트⭐로 반환

**정리**

> 클라이언트가 `GET http://localhost:8080/api/students` 로 요청을 보내면,   
> 컨트롤러의 getStudents() 메서드가 실행되고,   
> DB에서 학생 목록을 JSON 형식의 응답으로 반환한다.

&nbsp;
### ❓ 근데 왜 우리가 쓰는 URL에는 GET같은 HTTP 메서드가 없을까?

> URL에는 HTTP 메서드가 보이지 않는데, 정보가 어떻게 전달되는 거지🤔

그 이유는 HTTP 요청이 전달되는 과정을 통해 알 수 있다!

1️⃣ URL `http://localhost:8080/api/students` 입력

2️⃣ 브라우저가 HTTP 요청 메세지인 패킷 작성

3️⃣ TCP/IP를 통해 패킷으로 전송    
HTTP(애플리케이션) → TCP(전송) → IP(인터넷) → 이더넷(물리) 계층을 감싸며 패킷 형태로 이동

4️⃣ 서버가 수신하고 해석    
TCP/IP 헤더 제거 → HTTP 요청 메세지 해석 → 메서드(GET)와 경로(/api/students)를 알게 된다!

&nbsp;
### 📝 **API 테스트 & 문서화 도구**
• Postman: API 요청 테스트 도구

• Swagger: Spring API 문서화 도구

&nbsp;
### 💚 **네이버 로그인 API 예시**

사용자가 네이버 아이디로 다른 사이트에 별도 회원가입 없이 간편 로그인 가능

1️⃣ 사용자가 ‘네이버로 로그인’ 클릭

2️⃣ 네이버 로그인 페이지로 이동

3️⃣ 로그인 완료 시, **엑세스 토큰** 발급

4️⃣ 서버가 네이버에 **사용자 정보 요청**

5️⃣ 네이버가 **사용자 정보 응답**

📘 읽어볼 것: [네이버 API 공통 가이드](https://developers.naver.com/docs/common/openapiguide/README.md)

&nbsp;
### 📚 공부하고 싶은 내용

• [추가 자료 - RESTful 웹 API 디자인](https://learn.microsoft.com/ko-kr/azure/architecture/best-practices/api-design)

• OAuth2

• 토큰(Access/Refresh) vs 쿠키 vs 세션

&nbsp;
# Gradle
### 🐘 Gradle이란?

>🤔 IntelliJ에서 Spring Boot 프로젝트를 시작하면 생기는 Gradle이 뭘까?

실행 가능한 어플리케이션으로 만들어주는 **오픈소스 빌드 자동화 도구**

✔️ 의존성 관리: 필요한 외부 라이브러리 자동 다운로드

✔️ 빌드: 코드를 컴파일하고 JAR 파일로 패키징

✔️ 앱 실행: `./gradlew bootRun`

✔️  테스크 관리: 필요한 작업들을 모듈처럼 정의하고 실행 가능

&nbsp;
>🤔 JAR 파일을 만들지 않아도 프로젝트가 실행이 되는거 같은데?

우선, JAR(Java ARchive) 파일이란 여러 클래스 파일과 리소스를 **하나로 묶은 실행 가능한 패키지 파일**이다.



IntelliJ같은 IDE는 내부적으로 컴파일 + 실행 클래스를 자동으로 처리하기 때문에

JAR 파일을 만들지 않아도 프로젝트를 실행할 수 있지만,

**배포 또는 독립 실행**(다른 PC, 서버에서도 실행)을 위해 반드시 JAR 파일이 필요하다!

&nbsp;
### 📁 Gradle 관련 파일

• `gradle`

Gradle 버전 별 엔진 및 설정 파일

• `gradle/wrapper`

Gradle을 설치하지 않아도 Gradle task를 실행할 수 있게 함

• `gradlew` & `gradlew.bat`

Unix & Windows용 실행 스크립트

• `setting.gradle`

프로젝트의 이름 정의, 모듈 등록

• `build.gradle` ⭐⭐⭐

프로젝트의 **핵심 설정 파일**로, 의존성이나 플러그인 설정 등 빌드에 대한 모든 기능 정의

예시

```jsx
plugins { // 환경 구성에 필요한 플러그인(기능) 설정
    id 'java'
    id 'org.springframework.boot' version '3.4.4'
    id 'io.spring.dependency-management' version '1.1.7'
}

// 프로젝트의 식별자와 버전 정보
group = 'com.likelion'
version = '0.0.1-SNAPSHOT'

java { // Java 버전 설정 -> 컴퓨터에 여러 버전이 있어도 Gradle이 알아서 맞는 버전 사용
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories { // 빌드에 필요한 의존성(라이브러리)을 다운받을 저장소
    mavenCentral()
}

dependencies {// 프로젝트에 필요한 의존성 설정
    implementation 'org.springframework.boot:spring-boot-starter'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    runtimeOnly 'com.mysql:mysql-connector-j'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test') {// JUnit5 기반의 테스트 실행
    useJUnitPlatform()
}
```
&nbsp;
### 🥊 Maven vs Gradle

>🤔 서적에 Maven이 많이 나오는 이유가 뭘까?

Spring Boot 사용 초기에는 Maven이 많이 사용되었고 문서화가 잘 되어 있기 때문!

여전히 많은 레거시 시스템에서 Maven이 사용되긴 하지만 최근 Gradle을 사용하는 비율이 매우 늘었다고 한다.

그 이유는 다음과 같다.

이유 1️⃣ 빌드 속도 차이

Gradle은 캐시 빌드(이미 빌드한 결과 기억), 병렬 빌드(작업 동시에 처리) 지원

이유 2️⃣ 설정 방식의 유연함과 간결성

Maven은 XML 형식의 `pom.xml` 파일 사용하기 때문에 설정이 많아지면 읽고 수정하기 불편

Gradle은 Groovy 기반의 스크립트 방식(`build.gradle`)을 사용하여 간결하고 유연

이유 3️⃣ Spring Boot 전용 플러그인 제공

간단한 명령어로 애플리케이션 실행부터 배포용 빌드까지 쉽게 수행 가능

&nbsp;
# Bean
### 🫘 **Bean이란?**

**Spring Container가 생성하고 관리**하는 객체

객체를 직접 만들면 → `new`로 생성할 때마다 메모리 낭비 & 유지보수 불편 (클래스 안에서 변경 필요 = 낮은 확장성)    
Spring이 대신 만들어주면 → 객체 생성/주입/소멸 자동 & 유지보수 용이 (한 곳에서 관리)

&nbsp;
### ♻️ **Bean의 생명주기**

1️⃣ **객체 생성** - 파라미터를 받고 메모리를 할당하여 인스턴스 생성

2️⃣ **의존관계 주입** - 필요한 다른 객체들(의존성)을 찾아서 연결

3️⃣ **초기화 콜백** - 생성된 값들을 활용하여 무거운 동작 수행

4️⃣ **사용**

5️⃣ **소멸전 콜백** - 애플리케이션 종료 또는 Bean 제거 시, 자원 해제를 위해 호출

> 생명주기 콜백 방법으로 `@PostConstruct`과 `@PreDestroy` 어노테이션이 주로 사용되지만   
> 라이브러리에는 적용할 수 없으므로 이땐 @Bean의 initMethod, destroyMethod 사용!!

&nbsp;
### **🔍 Bean 등록 방법**

방법1 | **자동 등록**

Spring이 `@SpringBootApplication`에 포함된 `@ComponentScan` 덕분에
해당 클래스가 존재하는 패키지 전체를 스캔하여 Bean을 찾아 자동으로 등록해주어 무지 편리    
but 외부 객체를 등록하거나 복잡한 설정은 제한이 있기 때문에 이때는 방법2를 사용

> 위치: 클래스 위에 `@Service` `@Controller` `@Repository` 등 내부적으로 `@Component`인 어노테이션 작성

방법2 | **수동 등록**

내맘대로 직접 객체 생성 로직을 작성하고 Bean으로 등록 가능

> 위치: `@Configuration`내부 메서드에 `@Bean`  작성

&nbsp;
### 💉 **Bean 사용 방법** = 의존성 주입 방법

1️⃣ **필드 주입** → `final` 불가

```java
@Component
public class Game {
	@Autowired 
	private Weapon w; // 필드에서 객체 주입
	
	public void gameRun()
		w.fire();
}
```

2️⃣ **생성자 주입** ⭐⭐⭐

```java
@Component
public class Game {
	private final Weapon w;
	
	@Autowired
	public Game(Weapon w) // 생성자로 객체 주입
		this.w = w;
}
```

3️⃣ **setter 주입** → 변경 위험 O

```java
@Component
public class Game {
	private final Weapon w;
	
	@Autowired
	public void setWeapon(Weapon w) // setter로 객체 주입
		this.w = w;
}
```

&nbsp;
### 💯 **예제 코드로 보는 실행 흐름**

```java
@Service
public class GreetingService {
    public String greet(String name) {
        return "안녕하세요, " + name + "님!";
    }
}

@RestController
public class HelloController {

    private final GreetingService greetingService;

    public HelloController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/hello")
    public String hello() {
        return greetingService.greet("니경");
    }
}
// 코드 출처는 GPT...
```
1️⃣ Spring Boot 실행 → `@ComponentScan`

2️⃣🔍`@Service`가 붙은 Greeting Service가 Bean으로 자동 등록

3️⃣ `@RestController`가 붙은 HelloController도 자동 등록,   
💉생성자 주입을 통해 GreetingService를 주입받음

4️⃣ ‘/hello’로 요청이 들어오면 hello() 메서드 실행

5️⃣ GreetingService.greet(”나경”) 호출

6️⃣ 응답으로 “안녕하세요 나경님!” 반환