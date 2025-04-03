![프로필이미지](src/main/resources/static/images/github%20profile.png)

### **안녕하세요! Server를 공부 중인 서경대학교 전자컴퓨터공학과 주현준입니다.**

### 스스로 개발 공부에 있어서 적극적이고 BE 개발자로 직무를 정한 이후 개인적으로도 인프런을 통해서 계속해서 공부 하고 있습니다.

### 배우고자 하는 열망이 강하고 부족한 부분이나 피드백에 대해서 적극적으로 수용하겠습니다.

### 멈추지 않고 계속해서 배우고 성장하는 BE 개발자가 되겠습니다.

## 💻경험

- **University Makeus Challenge (UMC) 6th Spring Boot(2024.03 ~ 2024.08.27)**
- **University Makeus Challenge (UMC) 7th Spring Boot(2024.09 ~ 2024.02.21)**
- **멋쟁이사자 13기 백엔드 (2025.03 ~ )**
- **University Makeus Challenge (UMC) 8th Node.js(2025.03 ~)**

## 📝미션

<details>
  <summary>2주차 미션 (API, Gradle, Bean)</summary>

  <br/>

  <details>
    <summary>📡 API</summary>
    
    API (API, Application Programming Interface)는 둘 이상의 컴퓨터 프로그램이 서로 통신하는 방법이자 컴퓨터 사이에 있는 중계 계층을 의미합니다.
  
API라는 건 "식당 메뉴판"이라고 생각하면 된다.

메뉴판 보고 거기에 적힌 메뉴들을 정확히 주문한다. 즉, "식당의 메뉴판"이 곧 API이다.

=> 식당 주인과 음식을 주고 받기 위한 방법이라고 생각하면 된다.

## 웹서비스에서의 API

- 사용자가 서버랑 데이터를 주고 받는 정확한 방법
- 서비스를 하기 위해 만들어 놓은 메뉴판

## API가 가져야할 내용
**GET /users/articles/{artileId}**
- 위 처럼 { }(중괄호)로 감싸는 부분은 path variable을 의미

## REST API
### API Endpoint

REST API에서 API Endpoint는 해당 API를 호출하기 위한 HTTP 메소드, 그리고 URL을 포함합니다.

클라이언트 애플리케이션이 API에 요청을 보내는 특정 URL 주소를 의미합니다.

### HTTP 메소드

HTTP 메소드는 클라이언트와 서버 간 요청과 응답을 전송하는 방식을 정의한 것입니다.

HTTP 메소드는 REST 방식으로 통신 할 때 필요한 작업을 표시하는 방법으로

여러 가지가 있지만 아래의 5가지만 소개를 하겠습니다.

그리고 아래의 5개 메소드는 CRUD(생성, 조회, 갱신, 삭제) 4가지에 대응이 됩니다.

1. GET : 조회
2. POST : 생성
3. PUT : 갱신(전체)
4. PATCH : 갱신(일부)
5. DELETE : 삭제

위의 5개 메소드 중 **POST**는 **새로운 자원의 생성**도 있지만,
클라이언트가 **특정 정보를 서버로 넘기고 그에 대한 처리를 요청 하는 것**을
전부 POST로 처리 가능합니다.

### RESTful API Endpoint의 설계

이제 RESTful한 API의 설계를 위한 규칙을 알아봅시다.

RESTful한 API의 Endpoint는 **아래의 규칙**에 따라 설계가 가능합니다.

1. URI에 **동사가 포함이 되어선 안된다.**
2. URI에서 **단어의 구분이 필요한 경우 -(하이픈)을 이용**한다.
3. **자원**은 기본적으로 **복수형으로 표현**한다.
4. 단 하나의 자원을 **명시적으로 표현**을 하기 위해서는 **/users/id와 같이 식별 값을 추가로 사용**한다.
5. **자원 간 연관 관계가 있을 경우 이를 URI에 표현한다.**

## 인터페이스
인터페이스(interface)는 서로 다른 두 개의 시스템, 장치 사이에서 정보나 신호를 주고받는 경우의 접점이나 경계면입니다. 이를 통해 해당 컴퓨터의 내부서버가 어떻게 구현되어있는지는 상관없이 인터페이스를 통해 통신 등이 가능합니다.

ex) 삼성 갤럭시 UI
이러한 핸드폰의 화면을 기반으로 사용자는 휴대폰과의 상호작용을 할 수 있습니다. 앱을 실행하거나 등을 할 수 있는 것이죠.
ex) 네이버 웹툰
저희는 네이버의 웹툰의 서버가 어떻게 되어있는지. 데이터베이스가 어떻게 되어있는지 알지못합니다. 그러나 이러한 인터페이스를 기반으로 웹툰의 서비스를 즐길 수 있습니다


  </details>

  <details>
    <summary>🛠️ Gradle</summary>
    
### Gradle이 뭐야?
    
Gradle은 쉽게 말하면 **자동으로 프로젝트를 구성하고 실행해주는 도우미**야.
복잡한 컴파일, 라이브러리 설치, 테스트, 배포 등을 build.gradle이라는 파일 하나로 다 처리해줌.
```
plugins {
    id 'java'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    testImplementation 'junit:junit:4.13.2'
}
```
이게 build.gradle 파일의 예야. 여기서:

- plugins: 사용할 기능 선언 (java, spring-boot 등)

- repositories: 라이브러리 받을 저장소 지정 (mavenCentral이 대표적)

- dependencies: 사용할 라이브러리 추가 (spring, junit 등)

### 💡 Gradle이 하는 일
**기능	설명**

🔨 빌드	소스 코드 → 실행 가능한 .jar 또는 .class로 변환

📦 의존성 관리	필요한 라이브러리를 자동 다운로드 (Maven처럼)

🔍 테스트 실행	JUnit, Mockito 등 테스트 프레임워크 실행

🚀 배포	jar 파일을 서버에 배포하거나, Docker 이미지 생성 가능

  </details>

  <details>
    <summary>🌱 Bean</summary>

    - Bean이란 무엇인가?
    - 싱글톤이란?
    - Bean 등록방법
    - Bean 사용법

## Bean이란?
**Spring Contrainer가 관리하는 객체**
- new 키워드를 사용하지 않음
- Spring Container에 등록됨 -> 객체를 한 곳에서 관리하므로 유지보수가 쉬움
- 동일한 객체를 재사용할 수 있음(싱글톤)

## 싱글톤이란?
**프로그램에서 단 하나의 인스턴스만 존재하도록 보장하는 패턴이야.**

즉, 클래스를 여러 번 new 해도 항상 똑같은 객체 하나만 사용하게 만드는 방식이다.

### 🔧 왜 쓰는 걸까?
- 공통으로 사용하는 자원 관리
예: DB 연결, 설정 정보, 로그 시스템 등은 하나만 있어도 충분하다.

- 메모리 절약
같은 객체 여러 개 만들 필요 없으므로 하나만 만들어서 재사용!

- 일관성 유지
동일한 데이터를 공유하고 싶을 때 유리하다

🎯 스프링에서의 싱글톤
**스프링의 기본 @Service, @Component, @Repository, @Controller 전부 기본이 싱글톤 범위(scope)야. 그리고 자동으로 Bean 등록 방법이기도 하다.**

즉, 이렇게만 해도 자동으로 싱글톤이 적용돼:
```java
@Component
public class MyService {
    
}
```
### ⚠️ 주의할 점
상태(state)를 공유하면 안 된다.
- 싱글톤이 하나만 존재하니까, 필드에 값을 저장하면 여러 요청에서 꼬일 수 있다 → 그래서 싱글톤 빈은 **stateless(무상태)**하게 설계해야 한다.

## Bean 등록방법
### 수동등록
- 설정 파일에서 Bean 등록
- @Configuaration + @Bean 사용
- 외부 라이브러리도 등록 가능
- 유지보수가 어려움

### 자동등록
- 특정 어노테이션이 붙은 클래스는 자동으로 Bean에 등록됨
- @Component, @Service, @Repository, @RestController등
- @Entity는 아님. JPA의 기능이라, 스프링이 관리하지 않고,Hibernate가 EntityManager를 통해 관리해. 단지 엔티티라는걸 알려주는 어노테이션임. Repository를 Bean으로 등록하고 그걸 통해 Entity를 조회하고 저장하는거임.
- 코드가 간결해지고, 유지보수가 쉬움
- 외부 라이브러리는 자동 등록 불가

## Bean 사용 방법
**Spring Container에 등록된 Bean은 @Autowired를 사용하여 자동으로 객체에 주입됨**
즉, 사용자가 new 키워드를 사용할 필요 없이 Spring이 알아서 필요한 객체를 넣어줌.

### 생성자 주입이란?
스프링에서 의존성을 주입할 때, 생성자를 통해 필요한 Bean을 전달받는 방식이다.
가장 권장되는 방식임. (불변성 유지, 테스트 쉬움, 순환참조 방지 등 장점 많음)

```java
@Component
public class MyBean {
    public void doSomething() {
        System.out.println("Bean 동작 중!");
    }
}
```

```java
@Service
public class MyService {

    private final MyBean myBean;

    @Autowired
    public MyService(MyBean myBean) {
        this.myBean = myBean;
    }

    public void run() {
        myBean.doSomething();
    }
}
```
생성자에 MyBean을 파라미터로 받고 필드에 저장

@Autowired는 생략 가능 (스프링 4.3 이후, 생성자가 1개면 자동 주입됨)

## 팁
@RequiredArgsConstructor (Lombok) 사용하면 생성자 자동 생성 가능
```java
@Service
@RequiredArgsConstructor
public class MyService {
    private final MyBean myBean;
}
```


  </details>

</details>
