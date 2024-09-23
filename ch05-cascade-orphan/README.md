# JPA 연습 프로젝트

이 프로젝트는 Java Persistence API(JPA)를 사용한 기본적인 CRUD 동작을 연습하는 예제입니다. `EntityManager`를 사용하여 데이터베이스와의 상호작용을 처리하며, `Customer`, `Order`, `LineItem` 엔티티 간의 관계를 JPA 애너테이션을 통해 구현하였습니다.

## 프로젝트 구조

### 1. Customer 엔티티
- **설명**: 고객 정보를 담고 있는 엔티티로, `Order` 엔티티와 1:다(OneToMany) 관계를 가집니다.
- **주요 필드**:
  - `id`: 고객의 ID (Primary Key)
  - `name`: 고객 이름
  - `orders`: 고객이 보유한 주문 목록 (1:N 관계)

### 2. Order 엔티티
- **설명**: 주문 정보를 담고 있는 엔티티로, `Customer`와 다:1(ManyToOne), `LineItem`과 1:다(OneToMany) 관계를 가집니다.
- **주요 필드**:
  - `id`: 주문 ID (Primary Key)
  - `description`: 주문 설명
  - `customer`: 주문을 한 고객 (ManyToOne 관계)
  - `lineItems`: 주문 항목 목록 (1:N 관계)

### 3. LineItem 엔티티
- **설명**: 주문 항목을 담고 있는 엔티티로, `Order`와 다:1(ManyToOne) 관계를 가집니다.
- **주요 필드**:
  - `id`: 주문 항목 ID (Primary Key)
  - `productName`: 제품 이름
  - `quantity`: 제품 수량
  - `order`: 이 항목이 속한 주문 (ManyToOne 관계)

## 주요 기능

### 1. 엔티티 추가
- `Customer` 엔티티에 `Order`를 추가할 수 있으며, 각 `Order`에는 여러 `LineItem`을 추가할 수 있습니다.
- `CascadeType.ALL`을 사용하여 부모 엔티티(`Customer`, `Order`)가 추가되면 자식 엔티티도 자동으로 저장됩니다.

### 2. 엔티티 삭제 (고아 객체 처리)
- `orphanRemoval=true`를 통해 `Order` 또는 `LineItem`이 부모 엔티티와의 관계가 끊어지면 자동으로 삭제됩니다.
  
### 3. 영속성 상태 확인
- `EntityManager`의 `em.contains()` 메서드를 사용하여 엔티티가 영속성 컨텍스트에 포함되어 있는지 여부를 확인할 수 있습니다.

## 실행 방법

1. **데이터베이스 설정**: `persistence.xml` 파일에 H2 데이터베이스 설정이 포함되어 있습니다. 프로젝트는 H2 데이터베이스를 사용하며, 메모리 모드 또는 파일 모드로 설정할 수 있습니다.
    ```xml
    <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
    ```

2. **프로젝트 실행**: `Main` 클래스의 `main()` 메서드를 실행하여 JPA 동작을 확인할 수 있습니다.
    ```java
    public static void main(String ...args) throws InterruptedException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        Map<EntityClassStyle, Long> maps = setTestTables(emf);
        occurenceOrphanEntity(emf, maps);
    }
    ```

## 의존성

- **JPA**: Java Persistence API
- **Hibernate**: JPA 구현체
- **H2 Database**: 경량형 데이터베이스
