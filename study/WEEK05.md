# 🎉5주차 과제
## 1️⃣ Repository
Repository란 JPA(자바 ORM표준)를 상속 받음으로써 
기본적인 CRUD 동작을 가능케하여 DB와 통신하는 계층

➡️ JPA vs JDBC
- JPA는 JDBC를 사용하여 간단한 ORM을 제공하는 것. 둘은 다른 개념이 아님.
- JDBC는 SQL 쿼리문을 다뤄야하므로 복잡하지만, 성능 최적화 및 쿼리문 다루는 것에 유용함.
- JPA는 복잡한 매핑 작업을 편리하게 할 수 있고, 유지보수가 쉽다. 또한 JPQL(객체지향 쿼리 언어)을 사용하므로 객체 기준으로 자료를 다루기 때문에 DB 구조가 바뀌어도 사용에 유연하다.

➡️ JPA의 유용함
1. JPA는 메소드 이름으로 쿼리를 생성하는 기본 기능을 제공한다. ex) Post findById(Long id)
2. @Query 어노테이션을 사용하여 실행할 메소드 위에 정적 쿼리 생성 가능
## 2️⃣ DTO
Data Transfer Object의 약자로, 데이터 전송 객체이다.
각 계층(Controller, Service, Repository 등)간 데이터를 주고 받을 때, 사용되는 객체.

➡️ @Builder
- Builder, 생성자를 통해 객체를 자동으로 생성해주는 어노테이션

## 3️⃣ Service
비즈니스 로직을 담당하며, 가장 복잡한 코드가 들어가는 부분.
Controller<->Service<->Repository<->DB
- Controller와 Repository 사이에 위치하여 Controller의 원하는 작동 요청을 실질적으로 처리하는 부분.
- 처리 과정에서 Repository의 JPA를 통해서 CRUD 작동을 유용하게 사용하여 실질 로직 처리.

➡️ Converter란?


