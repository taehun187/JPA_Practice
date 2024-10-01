# JPA와 QueryDSL 기반의 데이터베이스 연동 프로젝트

이 프로젝트는 JPA(Java Persistence API)와 QueryDSL을 이용해 객체지향적으로 데이터베이스와 상호작용하는 예제입니다. 엔티티 간의 연관 관계를 설정하고, QueryDSL을 통해 타입 안전하고 효율적인 쿼리를 작성하는 방법을 보여줍니다. 주어진 구조를 기반으로 확장하거나 새로운 기능을 추가할 수 있습니다.

---

## 프로젝트 개요
이 프로젝트는 다음과 같은 주요 목적을 가지고 설계되었습니다:
1. JPA를 사용하여 데이터베이스와 연동하는 엔티티 설계 및 연관관계 관리
2. QueryDSL을 활용한 타입 안전한 쿼리 작성
3. JPQL(Java Persistence Query Language)을 사용한 복잡한 쿼리와 페이징 처리
4. H2 및 MySQL과 같은 관계형 데이터베이스에서의 테스트 환경 설정

---

## 기술 스택

- **Java**: 객체지향 프로그래밍 언어
- **JPA**: Java Persistence API, 객체와 관계형 데이터베이스 간의 매핑 프레임워크
- **Hibernate**: JPA의 구현체, 데이터베이스 상호작용을 보다 쉽게 처리
- **QueryDSL**: SQL과 같은 타입 안전한 쿼리를 작성할 수 있는 도구
- **H2 Database**: 테스트용으로 간편하게 사용할 수 있는 임베디드 데이터베이스
- **MySQL**: 선택적으로 사용할 수 있는 실제 서비스용 데이터베이스
- **Maven**: 빌드 도구

---

## 프로젝트 구조

### 1. **엔티티 구조**
   각 엔티티는 데이터베이스 테이블과 매핑되며, 각 엔티티 간의 연관 관계를 정의합니다.

- **`Member`**: 회원 정보를 담고 있으며, 다수의 주소와 선호 음식을 가지고 있습니다.
- **`Team`**: 여러 회원을 포함하는 팀을 나타내며, 회원과의 1:N 관계를 가집니다.
- **`Post`**: 게시글 정보를 담고 있으며, 여러 댓글(`Comment`)과 연관됩니다.
- **`FavoriteFood`**: 회원의 선호 음식 목록을 저장하는 엔티티입니다.
- **`AddressEntity`**: 회원의 여러 주소 정보를 관리합니다.

### 2. **연관관계 설정**
   - **`Member` ↔ `Team`**: `ManyToOne` 관계로, 하나의 팀은 여러 회원을 가질 수 있습니다.
   - **`Member` ↔ `FavoriteFood`**: `OneToMany` 관계로, 하나의 회원은 여러 선호 음식을 가질 수 있습니다.
   - **`Post` ↔ `Comment`**: `OneToMany` 관계로, 하나의 게시글에는 여러 댓글이 포함될 수 있습니다.
   - **`Member` ↔ `AddressEntity`**: `OneToMany` 관계로, 하나의 회원은 여러 주소를 가질 수 있습니다.

---

## 주요 엔티티 설명

### 1. `Member` 클래스

회원에 대한 정보(이름, 나이, 주소 등)를 저장하며, 팀과 연관되어 있습니다.

- **필드**:
  - `id`: 고유 식별자
  - `name`: 회원 이름
  - `age`: 회원 나이
  - `address`: `@Embedded`로 임베디드 객체인 `Address`를 포함하여 주소를 저장
  - `team`: 다수의 회원이 하나의 팀에 속하는 `ManyToOne` 관계
  - `favoriteFoods`: 회원의 선호 음식을 저장하는 `FavoriteFood` 엔티티와 `OneToMany` 관계
  - `addressList`: 여러 주소를 관리하는 `AddressEntity` 리스트

- **특징**: 
  - `orphanRemoval = true`: 고아 객체를 자동으로 삭제하여, 관계가 끊긴 엔티티를 제거합니다.
  - **Example**:
    ```java
    Member member = new Member();
    member.setName("John Doe");
    member.setAge(30);
    ```

### 2. `Team` 클래스

팀 정보를 저장하며, 여러 명의 회원과 연관됩니다.

- **필드**:
  - `id`: 고유 식별자
  - `name`: 팀 이름
  - `memberList`: 팀에 속한 회원 목록, `OneToMany` 관계

- **특징**: 
  - **`addMember()` 메서드**를 통해 팀에 회원을 추가하고, 반대로 **`removeMember()`** 메서드로 회원을 제거할 수 있습니다.

- **Example**:
  ```java
  Team team = new Team();
  team.setName("Development Team");
  ```

### 3. `Post` 클래스

게시글 정보를 저장하며, 게시글에는 여러 개의 댓글이 달릴 수 있습니다.

- **필드**:
  - `id`: 고유 식별자
  - `title`: 게시글 제목
  - `text`: 게시글 내용
  - `commentList`: 댓글 목록, `OneToMany` 관계

- **특징**:
  - `addComment()` 및 `removeComment()` 메서드를 통해 게시글에 댓글을 추가하거나 제거할 수 있습니다.

- **Example**:
  ```java
  Post post = new Post();
  post.setTitle("New Blog Post");
  post.setText("This is the content of the blog post.");
  ```

### 4. `FavoriteFood` 클래스

회원의 선호 음식을 저장하는 엔티티입니다.

- **필드**:
  - `id`: 고유 식별자
  - `foodName`: 음식 이름
  - `member`: `ManyToOne` 관계로 회원과 연관

- **Example**:
  ```java
  FavoriteFood food = new FavoriteFood();
  food.setFoodName("Pizza");
  ```

### 5. `AddressEntity` 클래스

회원이 가진 여러 주소 정보를 관리하는 엔티티입니다. 

- **필드**:
  - `id`: 고유 식별자
  - `address`: 임베디드 `Address` 객체를 포함
  - `member`: 회원과의 `ManyToOne` 관계

---

## QueryDSL 설정 및 사용

QueryDSL은 타입 안전한 쿼리를 지원하는 라이브러리입니다. 프로젝트에서 QueryDSL을 사용하여 다양한 쿼리를 실행할 수 있습니다.

### QueryDSL 주요 메서드

1. **`testQueryDSLSelect()`**  
   특정 회원을 ID를 기준으로 선택하는 메서드:
   ```java
   List<Member> memberList = queryFactory
      .selectFrom(QMember.member)
      .where(QMember.member.id.eq(member.getId()))
      .fetch();
   ```

2. **`testQueryDSLInsert()`**  
   QueryDSL을 사용하여 새로운 `Member`를 데이터베이스에 추가:
   ```java
   Member member = new Member();
   member.setName("1stQueryDSLInsert");
   member.setAge(40);
   em.persist(member);
   ```

3. **`testQueryDSLUpdate()`**  
   회원 이름을 업데이트하는 쿼리:
   ```java
   long affectedRows = queryFactory
      .update(QMember.member)
      .set(QMember.member.name, "New Name")
      .where(QMember.member.id.eq(member.getId()))
      .execute();
   ```

4. **`testQueryDSLDelete()`**  
   회원을 삭제하는 쿼리:
   ```java
   long affectedRows = queryFactory
      .delete(QMember.member)
      .where(QMember.member.id.eq(member.getId()))
      .execute();
   ```

5. **`testFetchJoinByQueryDsl()`**  
   Fetch Join을 사용하여 회원과 팀 정보를 동시에 가져오는 메서드:
   ```java
   List<Member> memberList = queryFactory
      .selectFrom(QMember.member)
      .join(QMember.member.team, QTeam.team).fetchJoin()
      .fetch();
   ```

### JPQL과의 차이점
- **JPQL**은 Java Persistence Query Language로 SQL과 유사한 문법을 사용하지만, QueryDSL은 컴파일 시점에 쿼리 오류를 방지하는 타입 안전한 쿼리를 제공합니다.
- **QueryDSL**은 코드 자동 생성기(컴파일러 플러그인)를 통해 엔티티 클래스를 기반으로 동적 SQL 빌더를 생성합니다.

---

## JPQL 예시

이 프로젝트에서는 JPQL(Java Persistence Query Language)을 사용하여 동적 쿼리 및 집계 쿼리를 작성합니다.

1. **`queryMemberOfTypedQuery()`**  
   JPQL을 사용하여 모든 회원을 조회:
   ```java
   TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
   List<Member> members = query.getResultList();
   ```

2. **`testAggregateFunction()`**  
   JPQL을 사용하여 집계 함수(SUM, AVG 등)를 실행:
   ```java
   Long totalAge = em.createQuery("SELECT SUM(m.age) FROM Member m", Long.class).getSingleResult();
   Double averageAge = em.createQuery("SELECT AVG(m

.age) FROM Member m", Double.class).getSingleResult();
   ```

---

## 데이터베이스 설정

### H2 Database

기본적으로 H2 데이터베이스를 사용하여 로컬 환경에서 쉽게 테스트할 수 있도록 설정되어 있습니다.

- **설정 파일**: `persistence.xml`
- **설정 내용**:
  ```xml
  <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
  <property name="javax.persistence.jdbc.user" value="sa"/>
  <property name="javax.persistence.jdbc.password" value=""/>
  <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
  <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
  ```

### MySQL 설정
주석 처리된 MySQL 설정을 활성화하여 실제 데이터베이스로 연결할 수 있습니다. MySQL을 사용하려면 해당 부분의 주석을 해제하고, 데이터베이스 사용자 이름과 비밀번호를 설정합니다.

---

## 실행 방법

1. **프로젝트 빌드**:  
   Maven을 사용하여 프로젝트를 빌드합니다.
   ```bash
   mvn clean install
   ```

2. **애플리케이션 실행**:  
   `Main` 클래스를 실행하여 JPA와 QueryDSL을 사용한 쿼리를 테스트할 수 있습니다.

3. **H2 콘솔 접속**:  
   웹 브라우저에서 `http://localhost:8082`로 접속하여 H2 콘솔에서 데이터베이스 상태를 확인할 수 있습니다.

---

## 주요 기능 요약

- JPA를 사용한 엔티티 연관관계 관리
- QueryDSL을 통한 타입 안전한 쿼리
- JPQL 및 페이징 처리
- H2 및 MySQL 데이터베이스 지원
- 다양한 집계 함수 및 Join 쿼리 처리

---

## 확장 가능성

1. **QueryDSL 확장**: 더 복잡한 조건을 추가하여 QueryDSL을 활용한 동적 쿼리를 확장할 수 있습니다.
2. **엔티티 확장**: 엔티티 간의 연관 관계를 더 복잡하게 설정하거나 새로운 필드를 추가하여 확장할 수 있습니다.
3. **서비스 레이어 추가**: 비즈니스 로직을 처리하는 서비스 계층을 추가하여 실제 애플리케이션에 사용할 수 있습니다.

---

## 결론

이 프로젝트는 JPA와 QueryDSL을 사용하여 객체-관계형 매핑과 동적 쿼리를 처리하는 데 중점을 둡니다. 추가적인 기능을 통해 다양한 비즈니스 요구사항을 충족할 수 있도록 확장할 수 있습니다.

---
