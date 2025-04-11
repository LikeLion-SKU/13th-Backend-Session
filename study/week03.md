<details>
  
  <summary>3주차 미션 (API, Gradle, Bean)</summary>

  <details>
    <summary> 다양한 어노테이션 살펴보기</summary>

    아래는 생성자를 자동 생성해주는 어노테이션 종류이다.

- **@NoArgsConstructor : 파라미터가 없는 디폴트 생성자를 자동으로 생성**
- **@AllArgsConstructor : 모든 필드 값을 파라미터로 받는 생성자를 자동으로 생성**
- **@RequiredArgsConstructor : final이나 @NonNull으로 선언된 필드만을 파라미터로 받는 생성자를 자동으로 생성**

## @NoArgsConstructor

**@NoArgsConstructor 어노테이션은 파라미터가 없는 디폴트 생성자를 자동으로 생성한다. 이 어노테이션을 사용하면, 클래스에 명시적으로 선언된 생성자가 없더라도 인스턴스를 생성할 수 있다.**

```java
@NoArgsConstructor
public class Person {
    private String name;
    private int age;
    // getters and setters
}
```

NoArgsConstructor 사용하면 Java 코드는 다음과 같아진다.

```java
public class Person {
    private String name;
    private int age;

	public Person(){}
}
```

## @AllArgsConstructor

**@AllArgsConstructor 어노테이션은 클래스의 모든 필드 값을 파라미터로 받는 생성자를 자동으로 생성한다. 이 어노테이션을 사용하면, 클래스의 모든 필드를 한 번에 초기화할 수 있다.**

```java
@AllArgsConstructor
public class Person {
    private String name;
    private int age;
    // getters and setters
}
```

AllArgsConstructor 사용하면 Java 코드는 다음과 같아진다.

```java
public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
    	this.name = name;
        this.age = age;
    }
}
```

## @RequiredArgsConstructor[중요]

### **@RequiredArgsConstructor 어노테이션은 final이나 @NonNull으로 선언된 필드만을 파라미터로 받는 생성자를 자동으로 생성한다. 이 어노테이션을 사용하면, 클래스가 의존하는 필드를 간단하게 초기화할 수 있다.**

```java
@RequiredArgsConstructor
public class Person {
    private final String name;
    private final int age;
    private String address;
    // getters and setters
}
```

RequiredArgsConstructor 사용하면 Java 코드는 다음과 같아진다.

```java
public class Person {
    private final String name;
    private final int age;
    private String address;

	public Person(final String name, final int age) {
    	this.name = name;
      this.age = age;
    }
}
```


## nullable=false, @NotNull, @NonNull의 차이는?

**1. nullable=false**
컬럼을 NOT NULL로 설정하는 것. 엔티티와 매핑되는 테이블 생성을 위한 DDL 쿼리(create table)가 나갈 때, 컬럼에 NOT NULL 제약조건(constraints)를 걸어준다.

**이것은 데이터베이스 테이블 컬럼에 NULL 값이 삽입되는 것을 막는 것이어서, 서비스 로직에서 엔티티에 NULL이 들어오는 것은 아무런 에러를 발생시키지 않는다.**


**2. @NotNull**
Spring Boot Validation 라이브러리(build.gradle에 추가해서 사용)를 써서 유효성 검증을 수행하는 방법.

**런타임 시 확인을 수행하여 엔티티에 NULL이 들어오는 것을 막는다.**

NULL이 들어오면 RuntimeException인 IllegalArgumentException을 던진다.

**이 어노테이션을 붙일 때에도 nullable=false와 마찬가지로 테이블 생성 시 컬럼에 NOT NULL 제약조건을 걸어준다.**


**3. Lombok의 @NonNull**
같은 이름을 가진 애노테이션을 Spring에서도 제공하는데 보통 @NonNull을 이야기하면 Lombok 라이브러리가 제공하는 @NonNull을 말하는 것 같다. 이 애노테이션을 달면 NULL 체크 로직을 자동으로 생성하여 런타임 체크를 수행한다.

NULL이 들어오면 RuntimeException인 NullPointerException을 던진다.

**하지만 이는 애플리케이션 레벨에서의 NULL 체크여서 DB 스키마 생성에는 영향을 끼치지 않는다고 한다. 따라서 엔티티 필드 컬럼을 NOT NULL로 지정하는 역할로는 쓸 수 없다.**


**결론: 엔티티 필드는 @NotNull을 쓰자 (DDL 컬럼 설정이 필요하고 서비스 로직에서도 NULL값이 들어가는지 확인 필요)**



---

### ✅ `@RequestParam`
- **설명:** 쿼리 파라미터나 폼 데이터의 **단일 값 바인딩**
- **예제:**
```java
@GetMapping("/hello")
public String hello(@RequestParam String name) {
    return "Hello " + name;
}
// /hello?name=jun → "Hello jun"
```

---

### ✅ `@ModelAttribute`
- **설명:** 폼 데이터나 쿼리 파라미터를 **객체에 바인딩**
- **예제:**
```java
@PostMapping("/join")
public String join(@ModelAttribute User user) {
    return "가입: " + user.getName();
}
```

---

### ✅ `@RequestBody`
- **설명:** **JSON 요청 바디**를 자바 객체로 변환
- **예제:**
```java
@PostMapping("/user")
public String createUser(@RequestBody User user) {
    return "Created " + user.getName();
}
// 요청 JSON: { "name": "jun" }
```

---

### ✅ `@ResponseBody`
- **설명:** 반환값을 **JSON 등 HTTP 응답 본문**으로 전달
- **예제:**
```java
@GetMapping("/user")
@ResponseBody
public User getUser() {
    return new User("jun");
}
// JSON 응답: { "name": "jun" }
```

---

### ✅ `@Valid`
- **설명:** 객체 유효성 검사를 수행
- **예제:**
```java
@PostMapping("/user")
public String save(@Valid @RequestBody User user, BindingResult result) {
    if (result.hasErrors()) return "에러!";
    return "저장됨";
}
```
## @Valid만으로 검증(BingdingResult 안쓰는 경우)
---

### ✅ 예를 들어

#### 🔹 DTO에 유효성 어노테이션 적용
```java
public class MemberDto {

    @Max(value = 100, message = "나이는 최대 100까지 가능합니다.")
    private int age;

    // Getter, Setter, 기본 생성자
}
```

#### 🔹 Controller에서 검증
```java
@PostMapping("/member")
public String create(@Valid @RequestBody MemberDto memberDto) {
    // age가 101이면 여기까지 안 오고 예외 터짐!
    return "정상 등록";
}
```

### 🔥 이 경우:
- `age = 101` 같은 유효성 실패가 발생하면  
  → `MethodArgumentNotValidException`이 발생  
  → 바로 예외로 튕김  
  → 즉, 메서드 바디 실행 안 됨

---

### ✅ `BindingResult` 없이도 유효성 검사는 된다.
- **검증 자체는 항상 됨** → `@Valid` 덕분에
- **차이점은 예외 처리 흐름**

---

### 💡 정리

| 항목 | 설명 |
|------|------|
| `@Valid`만 사용 | 검증 실패 시 즉시 예외 발생 |
| `@Valid + BindingResult` | 검증 실패해도 메서드 계속 실행됨 → 직접 에러 처리 가능 |
| `@Max`, `@NotNull` 등 | DTO에 붙여놓으면 `@Valid` 통해 검사됨 |

---

예외를 직접 처리하지 않아도 되는 경우에는 `BindingResult` 생략해도 되고,  
에러 메시지 커스터마이징이나 다국어 처리 등 고급 처리를 원하면 `BindingResult` 또는 전역 예외 핸들러(`@ControllerAdvice`) 같이 쓰면 돼.



---

### ✅ `@Validated`
- **설명:** `@Valid`와 비슷하나, **그룹 지정** 가능
- **예제:**
```java
@PostMapping("/user")
public String save(@Validated(User.Create.class) @RequestBody User user) {
    return "그룹 검증 OK";
}
```

---

### ✅ `@Transactional`
- **설명:** 메서드에 트랜잭션 적용 → 실패 시 롤백
- **예제:**
```java
@Transactional
public void saveAll() {
    userRepository.save(user1);
    userRepository.save(user2); // 실패 시 둘 다 롤백
}
```

---

</details>

<details>
  <summary> 3주차 세션 정리</summary>

## API 명세서
**API의 동작방식, Endpoint, 요청 및 응답 구조, 인증 방식 등을 설명하는 문서**
### 1. API의 명세서의 첫 부분에는 다음과 같은 기본 정보가 포함되어야 한다.
- API의 간단한 이름, 설명
- API의 현재 버전
- 기본 URL(API의 기본 URL) ex) api.example.com/test

### 2. API 사용 시 필요한 인증 방식을 명시해야 한다.
- API 키 인증
- OAuth2 : Bearer {access_token}
- 기타 : Basic Auth 또는 JWT 등을 사용하는 경우 명확한 내용 명시

### 3. API 명세서의 핵심은 각 API의 Endpoint와 HTTP Method이다. [중요]
- 엔드포인트 URI : /users, /users/{id}
- HTTP Method : POST, GET, PUT, PATCH, DELETE
- 설명 : 해당하는 API의 기능과 목적에 대한 설명

### 4. 각 Endpoint에 대한 요청 Parameter를 설명한다.
- 경로(PathVariable): /users/{id}
- 쿼리(RequestParam): ?id=1
- 헤더(RequestHeader):  Content-Type: application/json
- 본문(RequestBody): JSON 형식

### 5. API가 반환하는 응답구조를 명확하게 설명한다.
- 응답 형식: 주로 JSON 형식으로 작
- 상태 코드: 200 OK, 201 Created ,404 Not Found 
- 응답 예시: 실제 응답 데이터의 예시

### 6. API 사용 중 발생할 수 있는 오류상황과 대응방법을 명시한다.
- 상태 코드 
  -  예: 400 - Bad Request, 401 - Unauthorized, 500 - Internal Server Erro
- 오류 메세지 형식: 오류 발생 시  반환되는 JSON 메세지의 형식과 예시 작성

 ## 어노테이션
 - @Configuration: 해당하는 클래스가 설정 클래스임을 나타내며, Spring 애플리케이션의  구성 요소들을 설정할 수 있도록 해준다.
 - @Value: 외부 property의 값이나 환경 변수를 Spring bean의 필드에 주입할 때 사용된다.
 - @Bean: Spring의 IoC 컨테이너에 객체를 빈(bean)으로 수동 등록하는 데 사용된다

## Put vs Patch
### Put
- Put은 요청된 Body로 덮어쓸 데이터가 위치해야 하며,기존의 리소스가 해당 데이터로 완전히 덮어씌워진다. 
- 그렇기 때문에 동일한 요청을 여러번 보내더라도 항상 같은 데이터로 덮어씌워지기에 멱등성을 가진다

### Patch
- 요청 Body에 꼭 덮어쓸 데이터가 있을 필요가 없다.
- 덮어쓸 데이터가 아닌 동작을 지정해줄 수 있는 것이다.
- 동일한 요청을 여러번 보내면, 매 요청마다 값이 변경된다.
- 즉, 멱등성을 가지지 않는 것이다

## Transactional
**Transaction: 하나의 작업 단위를 의미하며, 데이터베이스에서 여러 개의 작업을 하나로 묶어서 처리하는 방법**
데이터베이스에서 발생하는 여러 작업을 하나의 트랜잭션으로 묶어 관리하고, 예외 발생 시 자동으로  
롤백되도록 처리할 수 있다. 또한, 트랜잭션의 전파 방식, 격리 수준, 롤백 조건 등을 세부적으로 설정하여 복잡한 비즈니스 로직도 쉽게 처리할 수 있다

### Transaction의 특징(ACID)
- A (Atomicity): 트랜잭션은 원자적으로 처리되어야 한다. 즉, 트랜잭션 내의 모든 작업은 모두 성공하거나 모두 실패해야 한다
- C (Consistency): 트랜잭션이 완료되면 데이터는 일관된 상태여야 한다
- I (Isolation): 트랜잭션은 서로 독립적으로 실행되어야 하며, 동시에 실행되는 다른 트랜잭션이 영향을 미치지 않아야 한다
- D (Durability): 트랜잭션이 완료되면 그 결과는 영구적으로 저장되어야 한다


</details>    



  
