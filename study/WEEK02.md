# 📌 API (Application Programming Interface)
API는 소프트웨어 간의 상호작용을 가능하게 하는 인터페이스이다. 한 프로그램이 다른 프로그램의 기능을 사용할 수 있도록 규칙과 도구의 집합을 제공한다.

---
## 1️⃣ API의 개념
API는 개발자가 기존 시스템을 활용하여 새로운 기능을 만들 수 있도록 해준다. 예를 들어, 날씨 애플리케이션이 기상청 데이터를 가져와서 보여준다면, 기상청에서 제공하는 API를 호출하여 정보를 받아오는 것이다.

### ✅ API의 주요 역할
- **데이터 교환**: 서로 다른 시스템 간의 데이터 송수신

- **기능 호출**: 특정 기능을 외부에서 호출하여 사용

- **추상화 제공**: 복잡한 로직을 감추고 간단한 인터페이스만 제공

---
## 2️⃣ API의 종류인 것들을 정리하면 다음과 같다.

### 🔹 Open API (공개 API)
누구나 사용할 수 있는 API

예: 카카오 지도 API, 구글 로그인 API

### 🔹 Private API (비공개 API)
특정 조직 내부에서만 사용하는 API

내부 서비스 간의 통신에 활용

### 🔹 REST API
HTTP 기반의 API로 가장 널리 사용됨

URL을 통해 리소스를 정의하고, GET/POST/PUT/DELETE 등의 HTTP 메서드로 조작

### 🔹 SOAP API
XML을 사용하여 데이터를 주고받는 방식

REST보다 무겁지만 보안이 강력함

---
## 3️⃣ REST API의 원칙
REST API는 가장 널리 사용되는 API 방식이며, RESTful API를 만들기 위해 지켜야 할 원칙들이 있다.

### ✅ 1. 클라이언트-서버 구조
클라이언트와 서버가 분리되어 독립적으로 동작해야 한다.

### ✅ 2. 무상태성 (Stateless)
API 호출 시 서버가 이전 요청의 상태를 기억하지 않아야 한다.

### ✅ 3. 캐시 가능 (Cacheable)
클라이언트가 데이터를 캐싱하여 불필요한 API 호출을 줄일 수 있어야 한다.

### ✅ 4. 계층화 (Layered System)
API 서버, 데이터베이스, 인증 서버 등이 계층적으로 설계되어야 한다.

### ✅ 5. 일관된 인터페이스 (Uniform Interface)
API URL은 명확하고 일관성 있는 구조를 가져야 한다.

GET /users → 모든 사용자 목록 조회

POST /users → 새로운 사용자 추가

GET /users/{id} → 특정 사용자 조회

---

## 6️⃣코드 예시 - SpringBoot를 이용한 REST API
멋사 2주차 세션에서 실습한 코드를 예로 들어 보겠다.

package com.likelion.springpractice.week02;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
## 📌 코드 분석
### 1️⃣ 클래스 선언 및 기본 설정
 ✅ @RestController
- 이 클래스가 REST API 컨트롤러임을 나타낸다.
- JSON 형식의 응답을 반환하는 API를 만들 때 사용된다.
- @Controller + @ResponseBody의 조합과 동일한 역할을 한다.

 ✅ @RequestMapping("/api")
- 기본 URL 경로를 /api로 설정한다.
- 즉, 이 컨트롤러의 모든 API 엔드포인트는 /api/... 형태로 호출된다.

### 2️⃣ 의존성 주입 (DI) 및 생성자
✅ private final StudentRepository studentRepository;
- StudentRepository 객체를 주입받아 사용한다.
- final 키워드를 사용하여 불변성을 보장한다.

✅ 생성자 주입 방식
- StudentController 객체가 생성될 때 StudentRepository를 주입받아 필드에 저장한다.
- Spring Boot에서는 @Autowired 없이도 단일 생성자 주입이 가능하다.

### 3️⃣ 학생 목록 조회 API
✅ @GetMapping("/students")
- GET /api/students 요청이 들어오면 getStudents() 메서드가 실행된다.
- URL 경로가 /api/students인 API 엔드포인트를 정의한다.

✅ studentRepository.findAll()
- studentRepository를 이용해 모든 학생 정보를 조회한다.
- findAll() 메서드는 JPA에서 제공하는 기본 메서드로, 테이블의 모든 데이터를 리스트로 반환한다.

✅ 리턴 타입이 List<Student>
- JPA에서 조회한 Student 객체 목록을 그대로 반환한다.
- Spring Boot는 List를 JSON 형태로 변환하여 응답한다.

## 📌 API 동작 방식

### 1️⃣ 클라이언트가 요청을 보낸다.

GET /api/students
### 2️⃣ StudentController.getStudents() 메서드 실행
- studentRepository.findAll()이 실행되어 데이터베이스에서 학생 목록을 가져온다.

### 3️⃣ 조회된 데이터를 JSON 형태로 반환
```json
[
  {
    "id": 1,
    "name": "윤해민",
    "age": 23,
    "department": "소프트웨어학과",
    "student_num": "2023216072"
  },
  {
    "id": 2,
    "name": "",
    "age": 23,
    "department": "공공인재학부",
    "student_num": "2022213003"
  }
]
```

## 📌 API 테스트 방법

### 🔹 1. Postman 또는 웹 브라우저에서 테스트

Postman을 이용하여 GET http://localhost:8080/api/students 요청을 보내면 응답을 받을 수 있다.
웹 브라우저에서도 해당 URL을 입력하면 JSON 데이터를 확인할 수 있다.

### 🔹 2. cURL 명령어 실행 (터미널에서 테스트 가능)

```shell
curl -X GET http://localhost:8080/api/students
```


---

---

---

# 📌 Bean이란?

Bean은 Spring Framework에서 관리하는 객체를 의미한다. Spring은 IoC (Inversion of Control) 컨테이너를 통해 애플리케이션의 객체를 생성하고 관리한다. 즉, Bean은 Spring이 생성, 초기화, 관리, 소멸하는 객체를 뜻한다.

Spring 애플리케이션에서 Bean은 애플리케이션의 구성 요소로, 서비스, 레포지토리, 컴포넌트, 설정 등 다양한 형태의 객체가 될 수 있다.

---

## 1️⃣ Bean의 기본 개념

Spring에서 Bean은 기본적으로 Spring IoC 컨테이너에서 관리되는 객체이다.

Spring IoC 컨테이너는 ApplicationContext라고도 하며, 이 컨테이너는 객체를 자동으로 생성하고 주입하는 역할을 한다.

### ✅ IoC (Inversion of Control)
- 전통적인 객체 지향 프로그래밍에서는 객체를 개발자가 직접 생성하지만, Spring에서는 객체 생성 책임을 컨테이너가 대신 맡는다. 이를 통해 객체 간의 의존 관계를 관리한다.

---

## 2️⃣ Bean 등록 방식

Spring에서는 다양한 방법으로 Bean을 등록할 수 있다.

### 🔹 1. @Component 애너테이션 사용

@Component는 Spring이 관리할 객체를 정의할 때 사용하는 기본적인 애너테이션이다.
이 애너테이션을 클래스에 선언하면 해당 클래스는 Bean으로 등록된다.

```java
@Component
public class MyService {
    public void serviceMethod() {
        System.out.println("서비스 로직 실행");
    }
}
```

- @Component: 해당 클래스를 Bean으로 등록
- ing은 @Component가 붙은 클래스를 자동으로 스캔하여 Bean으로 등록한다.

### 🔹 2. @Service, @Repository, @Controller 사용

@Service, @Repository, @Controller는 각각의 특수한 역할을 가진 Bean을 정의할 때 사용된다.
- @Service: 서비스 레이어에서 비즈니스 로직을 처리하는 Bean을 정의할 때 사용
- @Repository: 데이터베이스와의 상호작용을 처리하는 Bean을 정의할 때 사용
- @Controller: 웹 요청을 처리하는 Bean을 정의할 때 사용

이 애너테이션들은 기본적으로 @Component를 포함하고 있으므로, Bean으로 등록된다.

### 🔹 3. @Configuration + @Bean 사용

@Configuration을 사용하여 Java 기반의 설정 파일을 만들고, @Bean 애너테이션을 사용하여 Bean을 등록할 수 있다. 이 방식은 주로 XML 설정 방식을 대체하기 위해 사용된다.

```java
@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

- @Configuration: 해당 클래스가 설정 클래스임을 나타낸다.
- @Bean: 메서드가 반환하는 객체를 Bean으로 등록한다.

---

## 3️⃣ Bean의 생명 주기

Spring은 Bean의 생명 주기를 관리한다. 빈은 컨테이너가 관리하는 객체이므로, 컨테이너가 빈을 생성하고 초기화하고 소멸시키는 모든 과정에서 빈의 생명 주기를 관리한다.

### ✅ 1. 생성 (Instantiation)
- Spring 컨테이너가 애플리케이션 시작 시 Bean을 생성한다.
- @Component, @Bean 등으로 정의된 클래스를 자동으로 인스턴스화한다.

### ✅ 2. 초기화 (Initialization)
- Bean이 생성된 후 초기화 작업이 이루어진다.
- 초기화 메서드는 @PostConstruct 애너테이션을 사용하여 설정할 수 있다.

### ✅ 3. 소멸 (Destruction)
- 애플리케이션 종료 시 Bean이 소멸된다.
- 소멸 메서드는 @PreDestroy 애너테이션을 사용하여 설정할 수 있다.

---

## 4️⃣ Spring Bean의 스코프

Spring Bean의 스코프는 Bean이 어떻게 생성되고 관리되는지에 대한 범위를 정의한다. 기본적으로 Singleton이 사용되지만, 다양한 스코프를 설정할 수 있다.

### ✅ 1. Singleton (기본값)
•	애플리케이션 전체에서 단 하나의 인스턴스만 존재한다.
•	Spring IoC 컨테이너가 Bean을 하나만 생성하고, 모든 요청에 동일한 인스턴스를 공유한다.

```java
@Component
public class MyService {
    // Singleton
}
```

### ✅ 2. Prototype
- Bean이 요청될 때마다 새로운 인스턴스가 생성된다.
- 요청마다 별도의 객체가 생성되어 주입된다

```java
@Component
@Scope("prototype")
public class MyService {
    // 매번 새로운 객체가 생성됨
}
```


### ✅ 3. Request
- HTTP 요청당 하나의 Bean 인스턴스가 생성된다.
- Web 애플리케이션에서 사용되며, 각각의 HTTP 요청마다 새로운 Bean을 생성한다.

### ✅ 4. Session
- HTTP 세션당 하나의 Bean 인스턴스가 생성된다.
- 사용자가 웹 애플리케이션에 로그인한 세션마다 하나의 Bean을 유지한다.

---

## 5️⃣ Bean 주입 방법

Spring에서는 Bean을 주입하는 방법이 여러 가지 있다. 대표적인 주입 방법은 생성자 주입, 세터 주입, 필드 주입이 있다.

### ✅ 1. 생성자 주입
- 권장되는 방식으로, 의존성을 생성자에서 주입한다.
- final을 사용하여 불변성을 보장할 수 있다.


```java
@Component
public class MyService {
    private final MyRepository myRepository;

    @Autowired
    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }
}
```


### ✅ 2. 세터 주입
- @Autowired를 세터 메서드에 사용하여 의존성을 주입한다.

```java
@Component
public class MyService {
    private MyRepository myRepository;

    @Autowired
    public void setMyRepository(MyRepository myRepository) {
        this.myRepository = myRepository;
    }
}
```

### ✅ 3. 필드 주입
- 필드에 직접 @Autowired를 사용하여 주입하는 방법이다.
- 권장되지 않음: 테스트가 어렵고, 객체의 불변성을 보장할 수 없다.



```java
@Component
public class MyService {
    @Autowired
    private MyRepository myRepository;
}
```



---

### ✅ 정리
- Bean은 Spring IoC 컨테이너에서 관리되는 객체이다.
- Bean 등록 방법: @Component, @Service, @Repository, @Controller, @Configuration, @Bean 등이 있다.
- Bean의 생명 주기는 생성, 초기화, 소멸을 관리하며, @PostConstruct, @PreDestroy를 통해 제어할 수 있다.
- Bean의 스코프는 singleton, prototype, request, session 등으로 설정할 수 있다.
- Bean 주입 방법에는 생성자 주입, 세터 주입, 필드 주입이 있다.


---

---

---

# 📌 Gradle이란?

Gradle은 오픈 소스 빌드 자동화 도구로, Java, Kotlin, Groovy, C/C++, Python 등 다양한 언어를 지원하며, 프로젝트 빌드, 테스트, 배포 등의 작업을 자동화하는 데 사용된다. Gradle은 Maven과 Ant의 장점을 결합한 도구로, 유연성과 성능에서 뛰어난 특성을 가지고 있다.

---

## 1️⃣ Gradle의 특징

### ✅ 1. 다양한 언어 지원

Gradle은 기본적으로 Java 프로젝트를 지원하지만, 다양한 언어를 위한 플러그인을 제공한다. 예를 들어, Kotlin, Groovy, C/C++, Scala 등 다양한 언어로 작성된 프로젝트를 빌드할 수 있다.

### ✅ 2. 유연한 빌드 시스템

Gradle은 Groovy나 Kotlin DSL(Domain Specific Language)을 사용하여 빌드 스크립트를 작성할 수 있다. 이를 통해 빌드 과정의 세밀한 제어가 가능하다.

### ✅ 3. Maven과의 호환성

Gradle은 Maven 중앙 저장소와 호환되므로, 기존의 Maven 라이브러리 및 플러그인을 사용할 수 있다. 또한 Gradle은 Maven POM 파일을 읽어들이거나 Maven Central에 있는 의존성들을 쉽게 사용할 수 있다.

### ✅ 4. 병렬 빌드 및 성능 최적화

Gradle은 병렬 빌드를 지원하여 빌드 시간을 단축시킨다. 또한 incremental build(증분 빌드)와 빌드 캐싱 기능을 통해 이전 빌드 결과를 재사용하고, 필요한 작업만 수행함으로써 빌드 시간을 줄인다.

### ✅ 5. 확장 가능성

Gradle은 다양한 플러그인을 제공하며, 사용자가 커스텀 플러그인을 만들어 빌드를 확장할 수 있다. 이를 통해 다양한 빌드 요구 사항을 처리할 수 있다.

---

## 2️⃣ Gradle의 주요 구성 요소

### ✅ 1. 빌드 스크립트

Gradle은 기본적으로 build.gradle 또는 build.gradle.kts(Kotlin DSL) 파일을 사용하여 빌드 작업을 정의한다. 이 스크립트에서 빌드에 필요한 의존성, 플러그인, 태스크 등을 설정한다.

**예시 (Groovy DSL)**
```gradle
plugins {
    id 'java' // Java 플러그인 적용
}

repositories {
    mavenCentral() // Maven 중앙 저장소 사용
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web:2.5.4' // 의존성 추가
}

task hello {
    doLast {
        println 'Hello, Gradle!'
    }
}
```



- plugins: 빌드할 때 사용할 플러그인들.
- repositories: 의존성을 다운로드할 저장소 설정 (예: Maven Central).
- dependencies: 프로젝트에서 사용할 외부 라이브러리들.
- task: 사용자 정의 태스크 정의.

### ✅ 2. 태스크(Task)

Gradle은 태스크 단위로 작업을 정의한다. 각 태스크는 작업의 단위로, 예를 들어 compile, test, build와 같은 기본적인 작업을 포함할 수 있다. 사용자는 자신만의 태스크를 정의하고, 의존성을 설정하여 순차적으로 실행할 수 있다.

**예시 (태스크 정의)**
```gradle
task myTask {
    doLast {
        println 'Custom task executed'
    }
}
```



- doLast: 태스크가 실행될 때 실제로 실행될 작업을 정의하는 부분이다.

### ✅ 3. 의존성 관리

Gradle은 프로젝트에서 사용할 외부 라이브러리 의존성을 repositories와 dependencies 블록을 사용하여 관리한다.

**예시 (의존성 추가)**
```java
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.1'
}
```


- implementation: 컴파일 시 필요한 의존성.
- testImplementation: 테스트에 필요한 의존성.

### ✅ 4. 플러그인(Plugins)

Gradle은 빌드에 필요한 여러 플러그인들을 제공한다. 대표적으로 Java, Spring Boot, Maven, Docker와 같은 플러그인들이 있으며, 이를 통해 다양한 빌드 작업을 자동화할 수 있다.

**예시 (Spring Boot 플러그인 적용)**
```gradle
plugins {
    id 'org.springframework.boot' version '2.5.4'
}
```



### ✅ 5. 프로파일(Profiles)

Gradle은 환경에 따른 빌드 설정을 지원한다. 예를 들어, 개발 환경에서는 로컬 데이터베이스를, 운영 환경에서는 클라우드 서비스를 사용할 수 있도록 설정을 분리할 수 있다.

**예시 (프로파일 설정)**
```gradle
task devBuild {
    doLast {
        println 'Building for Development'
    }
}

task prodBuild {
    doLast {
        println 'Building for Production'
    }
}
```
--- 
## 3️⃣ Gradle vs Maven

Gradle과 Maven은 빌드 도구로서 매우 유사한 점이 많지만, 몇 가지 차이점도 있다.

### ✅ Maven
- XML 기반 설정: Maven은 XML 파일을 사용하여 빌드를 정의한다. XML은 구조적이지만 길고 복잡할 수 있다.
- 의존성 관리: Maven은 pom.xml 파일을 사용하여 의존성을 관리한다.
- 규칙 기반: Maven은 규칙에 맞게 작업을 수행하는 방식이어서 명시적인 설정을 요구한다.

### ✅ Gradle
- Groovy/Kotlin 기반 설정: Gradle은 Groovy 또는 Kotlin DSL을 사용하여 빌드 스크립트를 작성할 수 있어 유연성이 높다.
- 성능: Gradle은 병렬 빌드 및 증분 빌드 기능을 통해 빌드 성능이 뛰어나다.
- 유연성: Gradle은 매우 유연한 빌드 시스템을 제공하여 사용자가 원하는 방식으로 구성 가능하다.

### ✅ 결론:
- Maven은 구성 관리가 쉽고 명시적인 빌드 도구로, 전통적인 프로젝트에 적합하다.
- Gradle은 빠르고 유연한 빌드 시스템을 제공하여 복잡한 빌드 요구사항을 처리하는 데 적합하다.

---

## 4️⃣ Gradle을 사용하는 이유
1.	속도: Gradle은 병렬 빌드와 증분 빌드를 지원하여 빌드 속도가 빠르다.
2.	유연성: 다양한 플러그인과 설정 방식으로 빌드를 자유롭게 구성할 수 있다.
3.	확장성: 필요에 따라 사용자 정의 플러그인을 만들어 빌드를 확장할 수 있다.
4.	Kotlin DSL: Kotlin을 이용한 빌드 스크립트 작성이 가능해, 더 간결하고 가독성 높은 설정을 할 수 있다.

--- 

## 5️⃣ Gradle 빌드 과정
1.	빌드 스크립트 실행: gradle build 명령어를 실행하면 Gradle은 build.gradle 파일을 읽고 설정에 맞게 빌드를 시작한다.
2.	의존성 다운로드: 필요한 라이브러리 의존성을 중앙 저장소(Maven Central, JCenter 등)에서 다운로드한다.
3.	태스크 실행: 빌드 과정에서 정의된 태스크들을 순차적으로 실행한다.
4.	결과물 생성: 최종적으로 빌드 결과물(예: JAR, WAR 파일)을 생성한다.

---

## ✅ 정리
- Gradle은 Java 및 다양한 언어를 위한 빌드 자동화 도구로, 유연성, 성능, 확장성에서 강점을 가진다.
- 빌드 스크립트를 통해 의존성 관리, 플러그인 적용, 사용자 정의 태스크 등을 설정할 수 있다.
- Maven과 비교했을 때 속도와 유연성에서 뛰어나며, 특히 복잡한 빌드 시스템을 요구하는 프로젝트에서 유리하다.

Gradle을 사용하면 자동화된 빌드, 의존성 관리, 테스트, 배포 작업을 효율적으로 처리할 수 있다.

