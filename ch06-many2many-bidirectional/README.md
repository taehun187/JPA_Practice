# JPA Member, Product & MemberProduct Management

## 프로젝트 설명
이 프로젝트는 **JPA(Java Persistence API)**를 사용하여 `Member`, `Product`, 그리고 `MemberProduct` 간의 **다대다 관계**를 관리하는 애플리케이션입니다. `Member`와 `Product`는 **다대다 관계**를 갖고 있으며, 이를 해결하기 위해 **연결 엔티티**인 `MemberProduct`가 사용됩니다. 이 프로젝트는 기본적인 데이터베이스 연산(삽입, 조회 등)을 처리하며, 각 멤버가 주문한 제품 정보와 주문 수량을 관리할 수 있습니다.

## 주요 기능
- **멤버와 제품의 등록**: 새로운 멤버와 제품을 데이터베이스에 저장할 수 있습니다.
- **멤버-제품 관계 설정**: 특정 멤버가 특정 제품을 주문하는 관계를 설정하고, 이를 저장할 수 있습니다.
- **멤버의 주문 조회**: 특정 멤버가 주문한 제품과 주문 수량을 조회할 수 있습니다.

## 엔티티(Entity) 구조
### 1. Member (멤버)
- `Member`는 제품을 구매하는 사용자를 나타냅니다.
- 필드:
  - `id` (Long): 멤버 ID (Primary Key)
  - `name` (String): 멤버 이름
  - `memberProducts` (List<MemberProduct>): 멤버가 주문한 제품 정보를 담고 있는 리스트로 **다대다 관계를 해결하는 연결 엔티티**인 `MemberProduct`와 연관됨

### 2. Product (제품)
- `Product`는 구매 가능한 제품을 나타냅니다.
- 필드:
  - `id` (Long): 제품 ID (Primary Key)
  - `name` (String): 제품 이름
  - `member` (List<MemberProduct>): 이 제품을 주문한 멤버들과의 관계를 나타내는 리스트

### 3. MemberProduct (연결 엔티티)
- `MemberProduct`는 `Member`와 `Product`의 **다대다 관계를 해결하기 위한 연결 엔티티**입니다.
- 필드:
  - `id` (Long): 연결 엔티티 ID (Primary Key)
  - `meber` (Member): 주문한 멤버와의 **Many-to-One** 관계
  - `product` (Product): 주문된 제품과의 **Many-to-One** 관계
  - `orderAmount` (int): 주문 수량

## 주요 메서드
### 1. `main(String[] args)`
- 새로운 멤버, 제품을 생성하고, 이들 간의 관계를 설정하여 데이터베이스에 저장합니다. 특정 멤버가 주문한 제품 목록을 출력합니다.

### 2. `flush()`와 `clear()`
- JPA의 `flush()` 메서드는 영속성 컨텍스트의 변경 내용을 데이터베이스에 즉시 반영하고, `clear()` 메서드는 영속성 컨텍스트를 초기화합니다. 이를 통해 데이터가 확실히 저장되고, 캐시된 데이터를 제거하여 다음 작업에서 조회 결과가 새로 반영됩니다.

## 데이터베이스 테이블 관계
- **Member**와 **Product**는 **다대다 관계**이므로 이를 해결하기 위해 **MemberProduct**라는 연결 엔티티를 사용하여 **다대일(Many-to-One)** 관계로 구현하였습니다.
  - `Member`와 `Product`는 `MemberProduct`를 통해 관계를 맺습니다.
  - `MemberProduct`는 각 멤버가 어떤 제품을 몇 개 주문했는지를 나타냅니다.

## 실행 방법
1. **JPA 설정**: `persistence.xml` 파일에서 데이터베이스 연결 정보를 설정합니다.
2. **코드 실행**: `Main` 클래스를 실행하여 `Member`, `Product`, `MemberProduct` 엔티티 간의 관계 설정 및 CRUD 작업을 수행할 수 있습니다.

## 예시 출력
프로그램을 실행하면, 특정 멤버가 주문한 제품 목록과 해당 제품의 ID 및 이름을 출력합니다.
