## 프로젝트 설명
이 프로젝트는 **JPA(Java Persistence API)**를 이용하여 `Member`, `Product`, 그리고 `MemberProduct` 엔티티 간의 **다대다 관계**를 관리하는 애플리케이션입니다. JPA에서 다대다 관계를 구현할 때, 중간 테이블을 표현하기 위해 **MemberProduct** 엔티티를 사용하여 다대다 관계를 **두 개의 다대일 관계**로 분리해 처리합니다. `MemberProduct`는 주문 수량(`orderAmount`)과 함께 멤버와 제품 간의 관계를 나타냅니다.

## 주요 기능
- **멤버와 제품의 등록**: 새로운 멤버와 제품을 데이터베이스에 저장합니다.
- **멤버-제품 관계 설정**: 멤버가 여러 제품을 주문할 수 있으며, 제품은 여러 멤버에 의해 주문될 수 있습니다. 각 주문은 `MemberProduct` 엔티티를 통해 관리됩니다.
- **멤버의 주문 조회**: 특정 멤버가 주문한 제품과 그 수량을 조회할 수 있습니다.

## 엔티티(Entity) 구조
### 1. Member (멤버)
- **Member**는 제품을 주문하는 사람을 나타냅니다.
- 필드:
  - `id` (Long): 멤버 ID (Primary Key)
  - `name` (String): 멤버 이름
  - `memberProducts` (List<MemberProduct>): 멤버가 주문한 제품들을 저장한 리스트로, **MemberProduct**와의 **One-to-Many** 관계를 가집니다.

### 2. Product (제품)
- **Product**는 주문 가능한 제품을 나타냅니다.
- 필드:
  - `id` (Long): 제품 ID (Primary Key)
  - `name` (String): 제품 이름
  - `memberproduct` (List<MemberProduct>): 이 제품을 주문한 멤버들과의 **One-to-Many** 관계를 나타냅니다.

### 3. MemberProduct (연결 엔티티)
- **MemberProduct**는 멤버와 제품 간의 주문 정보를 저장하는 **연결 엔티티**입니다.
- 필드:
  - `id` (Long): 연결 엔티티 ID (Primary Key)
  - `member` (Member): 주문을 한 멤버와의 **Many-to-One** 관계
  - `product` (Product): 주문된 제품과의 **Many-to-One** 관계
  - `orderAmount` (int): 주문 수량

## 주요 메서드
### 1. `main(String[] args)`
- 새로운 멤버와 제품을 생성하고, 멤버가 특정 제품을 몇 개 주문했는지 데이터를 데이터베이스에 저장합니다.
- 특정 멤버가 주문한 제품 목록과 해당 제품의 ID 및 이름을 출력합니다.

## 데이터베이스 테이블 관계
- **Member**와 **Product**는 **다대다 관계**로, 이를 해결하기 위해 **MemberProduct**라는 **연결 엔티티**가 사용되었습니다. 
  - `MemberProduct`는 **Member**와 **Product** 간의 **Many-to-One** 관계를 나타냅니다.
  - 각 주문에는 멤버가 몇 개의 제품을 주문했는지 나타내는 `orderAmount` 필드가 포함됩니다.

## 실행 방법
1. **JPA 설정**: `persistence.xml` 파일에서 데이터베이스 연결 정보를 설정합니다.
2. **코드 실행**: `Main` 클래스를 실행하여 `Member`, `Product`, `MemberProduct` 엔티티 간의 관계 설정 및 CRUD 작업을 수행할 수 있습니다.

## 예시 출력
프로그램 실행 후, 특정 멤버가 주문한 제품 정보가 출력됩니다:
