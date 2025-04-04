## 🔷 API란?

📘 API = Application Programming Interface

**Application** → 응용 프로그램

**Programming** → 프로그래밍을 통해

**Interface** → "접점", "중간 매개체", "인터페이스"

<aside>
💡

응용 프로그램 간에 `기능 호출`, `데이터 전달`, `서비스 사용`을 가능하게 하는 명세(Interface)

</aside>

- 함수, 클래스, URL, 파라미터, 응답 포맷 등 `사용 규칙과 구조`가 모두 포함됨
- 시스템 간 결합도를 낮추고, **표준화된 인터페이스**를 통해 모듈 간 독립성을 보장함

---

## 🧩 동작 환경 기준 API 분류

| 유형                                    | 설명 | 대표 예시 |
|---------------------------------------| --- | --- |
| 🧱 **Web API (웹 API)**                | HTTP/HTTPS 기반으로 네트워크를 통해 호출되며, 주로 프론트엔드 ↔ 백엔드 통신에 사용됨 | REST API, GraphQL, 공공데이터 API |
| 📦 **Library API (라이브러리 API)**        | 특정 언어나 플랫폼에서 제공하는 함수/클래스 등의 모음. 직접 코드에서 호출하여 사용 | Python `math`, Java `Collections`, NumPy |
| 🖥️ **System API (시스템 API) 운영체제 API** | 운영체제(OS)의 자원이나 기능에 접근하는 API. 앱이나 프로그램이 OS와 상호작용하는 데 사용             | Windows API, POSIX API, Android OS API |
| 📱 **Device API (디바이스 API)             하드웨어 API**                            | 하드웨어 장치(센서, 카메라 등)의 기능을 제어하거나 정보에 접근하기 위한 API | Android Camera API, GPS API, Bluetooth API |
| 🔧 **Platform API (플랫폼 API)**         | 특정 플랫폼(Android, Chrome 등)의 기능을 확장하거나 통합하는 데 사용. 보통 SDK의 형태로 제공됨 | Android SDK API, Chrome Extension API, Unity API |

일반적으로 API라고 하면 `웹 API`를 의미한다.

---

## 🧩 공개 범위 기준 API 분류

| 유형                          | 설명 | 특징                                    | 예시                                      |
|-----------------------------| --- |---------------------------------------|-----------------------------------------|
| 🔓 **Public API  (오픈 API)** | 누구나 사용할 수 있도록 공개된 API | 접근 자유로움 API 키만 있으면 사용 가능  문서화 잘 되어 있음 | 공공데이터포털 API, Kakao Maps API, GitHub API |
| 🤝 **Partner API** | 제휴된 외부 기업/개발자만 사용할 수 있는 API | 계약/승인 필요 보안 강화 API 사용량 제한 조건 많음       | 쇼핑몰 ↔ PG사 API, 배달앱 ↔ 가맹점 API            |
| 🔐 **Private API** | 내부 시스템에서만 사용하는 비공개 API | 외부에서 호출 불가                   보안 최우선  내부 서비스/모듈 간 통신용 | 내부 ERP ↔ 인사시스템 연동,MSA 내부 통신
|

---

## 🧩 **동작 방식 기준 API 유형**

| 유형 | 설명 | 특징 | 예시 |
|------|------|--------|------|
| 🌐 **REST API** | HTTP 기반의 가장 대표적인 API 스타일 | - 리소스 기반<br>- URL + 메서드(GET/POST 등)<br>- 가볍고 직관적 | `GET /users/1`, 대부분의 웹 API |
| 🧼 **SOAP API** | XML 기반의 엄격한 프로토콜 (옛날 방식) | - 표준화 철저<br>- 보안/트랜잭션 지원<br>- 무겁고 복잡함 | 금융 시스템, 정부 기관 통신 |
| 🎯 **GraphQL API** | 클라이언트가 원하는 데이터만 쿼리 형태로 요청 | - 과/부족 데이터 방지<br>- 유연성 높음<br>- 쿼리 언어 필요 | GitHub API, Shopify API |
| 🚀 **gRPC API** | Google의 고속 바이너리 통신 방식 (protobuf 기반) | - 초고속 통신<br>- 강한 타입 체크<br>- 실시간 마이크로서비스에 적합 | Kubernetes, MSA 내부 통신 |
| 💬 **WebSocket API** | 실시간 양방향 통신 (연결 지속됨) | - 채팅/게임 등 실시간 데이터 교환<br>- 상태 유지 필요 | 카카오톡, 온라인 게임 서버, 주식 앱 |


## **API의 장단점**

### **1) 장점**

- **개발 효율성 향상**: 기존의 기능을 재사용하여 개발 시간을 단축할 수 있습니다.
- **유연한 서비스 통합**: 다양한 서비스를 손쉽게 통합하여 제공할 수 있습니다.
- **유지보수 용이**: 모듈화된 구조로 인해 유지보수가 쉽습니다.

### **2) 단점**

- **보안 이슈**: API를 통한 데이터 교환 시 보안에 취약할 수 있습니다.
- **의존성 문제**: 외부 API에 의존할 경우, 해당 서비스의 변경이나 중단에 영향을 받을 수 있습니다

# 🌐 REST API란?

## 🔷 REST의 의미

> REST (REpresentational State Transfer)
>
>
> → `자원을 이름으로 구분`하고, 그 `자원의 상태를 주고받는` 아키텍처 스타일
>
- REST는 API 설계의 철학/원칙(스타일)
- REST **API**는 그 원칙을 따라 만든 **HTTP 기반의 API**
- RESTful API : REST 아키텍처 스타일을 **올바르게** 따르는 API

'HTTP URI(Uniform Resource Identifier)'를 통해 자원(Resource)을 명시하고,

'HTTP Method(POST, GET, PUT, DELETE, PATCH 등)'를 통해 해당 자원(URI)에 대한 'CRUD Operation'을 적용

<aside>
💡

CRUD Operation이란?

Create : 데이터 생성(POST)

Read : 데이터 조회(GET)

Update : 데이터 수정(PUT, PATCH)

Delete : 데이터 삭제(DELETE)

</aside>

---

# 🧱 REST API 구성 요소

## **1. 자원(Resource) : HTTP URI**

- 해당 소프트웨어가 관리하는 모든 것
- 모든 자원에 고유한 ID가 존재하고, 이 자원은 Server에 존재한다.
- 자원을 구별하는 ID는 ‘/groups/:group_id’와 같은 HTTP URI
- Client는 URI를 이용해서 자원을 지정하고 해당 자원의 상태(정보)에 대한 조작을 Server에 요청한다.

## **2. 행위(Verb)**

- **HTTP Method**HTTP 프로토콜의 Method를 사용한다.
- HTTP 프로토콜은 GET, POST, PUT, DELETE 와 같은 메서드를 제공한다.

## **3. 표현(Representation of Resource)**

- Client가 자원의 상태(정보)에 대한 조작을 요청하면 Server는 이에 적절한 응답(Representation)을 보낸다.
- REST에서 하나의 자원은 JSON, XML, TEXT, RSS 등 여러 형태의 응답을 받을 수 있다.
- JSON 혹은 XML를 통해 데이터를 주고 받는 것이 일반적이다.

---

# 🚀 HTTP Method와 REST API 매핑

| HTTP 메서드 | 의미 | REST에서 역할 |
| --- | --- | --- |
| **GET** | 조회 | 자원 읽기 |
| **POST** | 생성 | 자원 추가 |
| **PUT** | 전체 수정 | 자원 전체 덮어쓰기 |
| **PATCH** | 일부 수정 | 자원 일부만 변경 |
| **DELETE** | 삭제 | 자원 제거 |

---

# 📏 REST API 설계 원칙 (6가지 REST 제약 조건)

| 제약 조건 | 설명 |
|------------|-------|
| ✅ **클라이언트-서버 구조** | - 사용자와 자원을 분리 (프론트와 백 분리)<br>- 클라이언트와 서버를 명확히 분리하여 각자의 역할에 집중할 수 있도록 함<br>- 클라이언트가 알아야 할 유일한 정보는 요청된 리소스의 URI |
| ✅ **Stateless (무상태)** | - 요청 간 상태 유지 없음 (서버는 클라이언트 상태 저장 안 함)<br>- 각 요청에는 처리에 필요한 모든 정보가 포함돼야 함<br>- 요청은 독립적으로 처리되어야 함 |
| ✅ **캐시 가능성 (Cacheability)** | - 응답에 캐시 가능 여부 표시<br>- 서버 또는 클라이언트가 리소스를 캐시할 수 있어야 함<br>- 성능 향상 및 서버 부하 감소 효과 |
| ✅ **계층화 구조 (Layered System)** | - 클라이언트와 서버 사이에 여러 계층 존재 가능<br>- 각 계층은 독립적으로 동작<br>- 중간 서버/프록시 등을 통해 보안, 로드 밸런싱 가능 |
| ✅ **인터페이스 일관성 (Uniform Interface)** | - URI, 메서드, 응답 방식 등에 있어 일관성 유지<br>- 요청 출처와 무관하게 동일한 방식으로 리소스를 접근해야 함 |
| ✅ **Code on Demand (선택사항)** | - 서버가 클라이언트에 실행 가능한 코드를 전송할 수 있음<br>- 예: JavaScript<br>- 거의 사용되지 않음 |

---

# ⚖️ REST API 장단점

## ✔️ 장점 (Advantages)

| 구분 | 내용 |
| --- | --- |
| ✅ **HTTP 기반으로 간단하고 직관적** | 별도의 프로토콜이나 인프라 없이 기존 HTTP만으로 구성 가능 |
| ✅ **언어/플랫폼 독립적** | HTTP만 지원하면 어떤 언어나 환경에서도 사용 가능 |
| ✅ **인프라 재사용** | 프록시, 로드 밸런서, 캐시 등 기존 HTTP 인프라를 그대로 활용 |
| ✅ **캐싱 용이** | GET 요청 등은 쉽게 캐싱 가능 (속도 향상 및 서버 부하 감소) |
| ✅ **계층 구조 지원** | 클라이언트는 서버의 내부 구성(중간 게이트웨이, 프록시 등)을 몰라도 됨 |
| ✅ **표현력 있는 URI와 메시지** | 리소스 중심의 URI 설계로 의도가 명확하고 가독성 좋음 |
| ✅ **역할 분리 구조** | 클라이언트-서버 구조 명확 → 유지보수성, 확장성 우수 |
| ✅ **문서화 쉬움** | Swagger(OpenAPI) 등 자동화된 문서화 도구와 잘 맞음 |
| ✅ **범용성 높음** | REST 원칙을 지키면 다양한 서비스 설계 문제 예방 가능 |

---

## ❌ 단점 (Disadvantages)

| 구분 | 내용 |
| --- | --- |
| ⚠️ **표준 미존재** | 엄격한 공식 표준 없음
→ 구현 방식이 서비스마다 다름 |
| ⚠️ **복잡한 쿼리에는 불리** | 필터, 정렬, 조건 조합이 많아질수록 URL이 지저분해짐 |
| ⚠️ **HTTP Method 제약** | 공식적으로는 GET, POST, PUT, DELETE만 활용 (기타 메서드는 브라우저/클라이언트가 지원 안 할 수도 있음) |
| ⚠️ **응답 데이터 과부족 문제** | 필요한 것보다 많은 데이터를 받거나, 부족한 정보로 여러 번 호출해야 하는 경우 발생 (Over-fetching / Under-fetching) |
| ⚠️ **과도한 엔드포인트 수** | 복잡한 도메인을 표현하려다 보면 리소스/버전/필터 등으로 인해 URI가 복잡해지고 많아짐 |
| ⚠️ **헤더 중심 구조의 복잡성** | 인증, 포맷 지정 등은 Header를 통해 처리 → 초보자/비개발자에게 다소 불편하게 느껴질 수 있음 |
| ⚠️ **구형 브라우저 호환 이슈** | 일부 브라우저는 PUT/DELETE 등 비표준 메서드 제대로 지원하지 않음 |

---

# ✅ 설계 Tip

## 🔷 1. **URI는 자원(Resource)을 표현해야 한다**

- URI는 반드시 **“자원”을 식별하는 명사**여야 하며, **행위(동사)는 포함하지 않는다**.

| 잘못된 예 | 올바른 예 |
| --- | --- |
| `/delete-user/1` | `DELETE /users/1` |
| `/getUser/1` | `GET /users/1` |

🔹 **요약 원칙**

- URI에는 동사❌, 명사✅
- 자원의 **컬렉션** → 복수형 사용 (`/users`)
- 자원의 **인스턴스** → 식별자 사용 (`/users/1`)

---

## 🔷 2. **HTTP 메서드로 행위를 표현한다**

| 동작 | 메서드 | URI 예시 |
| --- | --- | --- |
| 조회 | GET | `/students/1` |
| 생성 | POST | `/students` |
| 수정 | PUT / PATCH | `/students/1` |
| 삭제 | DELETE | `/students/1` |

> 💡 URI에 insert, update, delete 같은 단어가 나오면 RESTful하지 않은 설계입니다.
>

---

## 🔷 3. **소문자를 사용한다**

- URI 경로에는 **소문자**만 사용 (RFC 3986 표준)
- 대문자는 사용하지 않음 (호환성, 오류 위험 증가)

| 잘못된 예 | 올바른 예 |
| --- | --- |
| `/Users/1` | `/users/1` |

---

## 🔷 4. **단어 구분에는 하이픈(-)을 사용한다**

- 가독성을 위해 단어 사이에 **하이픈() 사용**
- **언더바(`_`)는 사용하지 말 것**

| 잘못된 예 | 올바른 예 |
| --- | --- |
| `/user_profile` | `/user-profile` |

---

## 🔷 5. **URI는 복수형 명사를 사용한다**

- 컬렉션은 복수형 → `/posts`, `/comments`
- 개별 리소스는 식별자와 함께 → `/posts/123`

> 단수형은 보통 사용하지 않음 (/post/123 ❌)
>

---

## 🔷 6. **슬래시(`/`)는 계층 관계 표현에만 사용한다**

- `/A/B` 구조는 **B가 A에 종속된 리소스일 때만 사용**
- "has-a" 관계에서 활용

| 예시 | 의미 |
| --- | --- |
| `/users/1/orders` | “1번 사용자가 가진 주문 목록” |

---

## 🔷 7. **URI 마지막에 슬래시(`/`)를 포함하지 않는다**

- REST에서는 `/posts`와 `/posts/`를 **다른 자원**으로 취급할 수 있으므로, 혼동 방지용으로 슬래시 제거
- URI 끝은 **항상 슬래시 없이**

| 잘못된 예 | 올바른 예 |
| --- | --- |
| `/posts/` | `/posts` |

---

## 🔷 8. **파일 확장자는 포함하지 않는다**

- `.json`, `.xml`, `.jpg` 등은 URI에 넣지 말고, **Accept 헤더로 표현 포맷을 지정**

| 잘못된 예 | 올바른 예 |
| --- | --- |
| `/photo.jpg` | `GET /photo` + `Accept: image/jpeg` |

---

## 🔷 9. **변하는 부분은 고유 식별자(id)로 표현한다**

- 리소스의 특정 인스턴스는 `/{id}` 형태로 접근

| 의미 | URI 예시 |
| --- | --- |
| 사용자 조회 | `/users/15` |
| 특정 게시글의 댓글 | `/posts/77/comments` |

---

## 🔷 10. **리소스 간 관계는 계층 구조로 표현**

- "A가 B를 소유한다" 관계는 계층 구조 사용
- 예: `/users/{userId}/devices`

---

## 📌 전체 요약표

| 원칙 | 설명 | 예시 |
| --- | --- | --- |
| 명사 사용 | 리소스는 명사로 표현 | `/users` |
| 소문자 | URI는 소문자만 | `/students/1` |
| 하이픈 | 단어 구분자는 `-` | `/user-profile` |
| 슬래시 | 계층 구조 표현 | `/users/1/orders` |
| 슬래시 제거 | 마지막에 `/` 포함 ❌ | `/posts` ✅, `/posts/` ❌ |
| 확장자 제거 | `.json`, `.jpg` 등 사용 ❌ | `Accept` 헤더 사용 |
| 행위 제외 | URI에 동사 포함 ❌ | `/delete-user` ❌, `DELETE /users/1` ✅ |
| ID 경로 | 특정 리소스는 `/{id}` | `/students/22` |
| 관계 표현 | `/A/{id}/B` 구조 | `/users/1/devices` |


---
---

## Gradle

- 오픈 소스 빌드 자동화 도구
- 프로젝트의 컴파일, 테스트, 패키징, 배포 등을 수행

빌드 도구란?

- 소스 코들르 실행 가능한 어플리케이션으로 만들어주는 도구

## Gradle 파일 구조 및 역할

- .gradle

  버전 별 엔진 및 설정 파일

- gradle/wrapper

  Gradle을 설치하지 않아도 Gradle task를 실행할 수 있게 함

- build.gradle

  의존성, 플러그인 설정 등 빌드에 대한 모든 기능 정의

- gradlew & gradlew.bat

  Unix & Windows용 실행 스크립C

- settings.gradle

  프로젝트 설정 파일


### build.gradle 파일 분석

환경 구성에 필요한 프로그인 설정

```powershell
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.4.4'
    id 'io.spring.dependency-management' version '1.1.7'
}
```

옵션(groupId, 어플리케이션 버전 등)

```powershell
group = 'com.likelion'
version = '0.0.1-SNAPSHOT'
```

Java 버전 설정

```powershell
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
```

빌드에 필요한 의존성을 다운받을 저장소

```powershell
repositories {
    mavenCentral()
}
```

프로젝트에 필요한 의존성 설정

```powershell
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.cloud:spring-cloud-function-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    runtimeOnly 'com.mysql:mysql-connector-j'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
```

JUnit 5 기반의 테스트 설정

---
---

## 🌱 Bean이란?

- **Spring Container가 관리하는 객체**
- 우리가 `new`로 직접 생성하지 않고, Spring이 대신 생성하고 관리함
- 장점:
    - 객체를 **중앙에서 관리**하므로 유지보수가 쉬움
    - 동일한 객체를 **재사용** 가능 (싱글톤으로 관리되는 경우가 많음)

---

## 🧾 Bean 등록 방법

### ✅ 1. 수동 등록

- **직접 설정 파일**에 Bean을 등록
- `@Configuration` 클래스에서 `@Bean` 메서드를 사용
- **외부 라이브러리**도 등록 가능
- 단점: 코드가 길어지고 유지보수가 어려움

```java
java
복사편집
@Configuration
public class AppConfig {
    @Bean
    public Weapon weapon() {
        return new Weapon();
    }
}

```

---

### ✅ 2. 자동 등록

- 클래스에 어노테이션 붙이면 자동으로 Bean 등록
- 대표 어노테이션: `@Component`, `@Service`, `@Repository`, `@Controller`
- 코드가 간결하고 유지보수 쉬움
- 단점: 외부 라이브러리는 자동 등록 불가

```java
java
복사편집
@Component
public class Weapon {
    public void fire() {
        System.out.println("총을 발사합니다!");
    }
}

```

---

## ⚙️ Bean 사용 방법

### 🔁 의존성 주입 (DI, Dependency Injection)

- Spring에 의해 Bean이 **필요한 곳에 자동으로 주입**됨
- `@Autowired`를 사용해서 객체를 자동 주입 가능

### 📌 주입 방법 3가지

### 1. **필드 주입**

```java
java
복사편집
@Component
public class Game {
    @Autowired
    private Weapon w;

    public void gameRun() {
        w.fire();
    }
}

```

### 2. **생성자 주입** (추천 방식 👍)

```java
java
복사편집
@Component
public class Game {
    private final Weapon w;

    @Autowired
    public Game(Weapon w) {
        this.w = w;
    }

    public void gameRun() {
        w.fire();
    }
}

```

### 3. **Setter 주입**

```java
java
복사편집
@Component
public class Game {
    private Weapon w;

    @Autowired
    public void setWeapon(Weapon w) {
        this.w = w;
    }

    public void gameRun() {
        w.fire();
    }
}

```