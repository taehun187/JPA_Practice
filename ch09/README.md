# 프로젝트 설명

이 프로젝트는 `JPA`를 사용하여 `Member`, `Team`, `Address`, `FavoriteFood`와 같은 엔티티 간의 관계를 다루는 예제 프로젝트입니다. `EntityManagerFactory`를 활용하여 데이터베이스 연동을 처리하고, 샘플 데이터를 생성하고 조회하는 기능을 포함하고 있습니다.

## 프로젝트 구조

- **com.taehun.entity**
  - `Address`: 임베디드 타입으로, 주소 관련 필드를 담고 있습니다.
  - `Member`: 회원 엔티티로, 이름, 팀, 즐겨찾는 음식 및 주소 목록을 관리합니다.
  - `Team`: 팀 엔티티로, 여러 회원과 연관되어 있습니다.
  - `BaseEntity`: 모든 엔티티가 상속받는 공통 엔티티로, 생성자 및 수정자를 포함하고 있습니다.

- **com.taehun.utilities**
  - `JpaBooks`: 샘플 데이터를 생성하고, 임의의 팀 및 회원을 배정하는 유틸리티 클래스입니다.

- **com.taehun**
  - `Main`: 프로젝트의 메인 실행 클래스입니다. 샘플 데이터를 생성하고 회원의 정보를 업데이트하는 로직을 포함하고 있습니다.

## 주요 기능

1. **샘플 데이터 생성**: `initMemberTeamSampleData` 메소드를 통해 여러 팀과 회원 데이터를 생성합니다.
2. **즐겨찾는 음식 추가**: 특정 회원에게 즐겨찾는 음식을 추가하는 `insertFavoriteFood` 메소드가 구현되어 있습니다.
3. **즐겨찾는 음식 조회**: 특정 회원의 즐겨찾는 음식을 조회하는 `searchFavoriteFood` 메소드가 있습니다.
4. **즐겨찾는 음식 업데이트**: 회원의 즐겨찾는 음식 목록에서 특정 음식을 제거하고 새로운 음식을 추가하는 `updateFavoriteFood` 메소드가 있습니다.
5. **주소 및 주소 목록 추가**: 회원에게 기본 주소와 주소 목록을 추가하는 `insertAddressAndAddressList` 메소드가 있습니다.

## 클래스 설명

### 1. `Address`

- 주소 정보를 담는 임베디드 타입입니다.
- 필드: `street`, `city`, `zipcode`

### 2. `Member`

- 회원 엔티티로, 여러 팀에 속할 수 있으며, 즐겨찾는 음식과 주소 목록을 가지고 있습니다.
- 필드: `id`, `name`, `team`, `favoriteFood`, `address`, `addressList`

### 3. `Team`

- 팀 엔티티로, 여러 회원과 연결됩니다.
- 필드: `id`, `name`, `memberList`

### 4. `BaseEntity`

- 생성일자와 수정일자, 생성자와 수정자를 관리하는 공통 엔티티입니다.
- 필드: `createBy`, `creationData`, `lastModifiedBy`, `lastModified`

### 5. `JpaBooks`

- 샘플 데이터를 생성하는 유틸리티 클래스입니다.
- `initMemberTeamSampleData` 메소드를 통해 팀과 회원의 샘플 데이터를 생성하고, 각 회원이 랜덤한 팀에 배정됩니다.
- 또한, `generateRandomId`와 같은 메소드로 임의의 ID를 생성하여 다양한 샘플 데이터 관리 기능을 제공합니다.

### 6. `Main`

- 프로젝트의 메인 클래스입니다. 이 클래스는 `EntityManager`를 사용하여 데이터베이스와의 트랜잭션을 관리하며, 샘플 데이터를 생성하고 회원의 정보를 업데이트하는 기능을 제공합니다.
  
#### 주요 메소드
1. **`main` 메소드**:
   - 프로젝트의 진입점으로, `EntityManagerFactory`를 통해 `EntityManager`를 생성한 후 트랜잭션을 시작합니다.
   - `JpaBooks.initMemberTeamSampleData`를 통해 10개의 팀과 10명의 회원을 데이터베이스에 추가하고, 각 회원이 임의의 팀에 배정됩니다.
   - 생성된 회원의 ID를 사용하여 `insertFavoriteFood`를 호출해 회원의 즐겨찾는 음식을 추가합니다.
   - 이후, `searchFavoriteFood`로 회원의 즐겨찾는 음식을 조회하고, `updateFavoriteFood`로 회원의 즐겨찾는 음식을 업데이트합니다.
   - 마지막으로, `insertAddressAndAddressList`를 통해 회원에게 주소와 주소 목록을 추가하고, 이를 조회합니다.

2. **`insertFavoriteFood(EntityManager em, List<Long> memberIds)`**:
   - 임의로 선택된 회원에게 3개의 즐겨찾는 음식을 추가합니다: `짬뽕`, `떡볶이`, `바닐라아이스라떼`.
   - 이를 통해 회원 객체의 `favoriteFood` 필드에 값이 추가되며, 변경 사항이 데이터베이스에 저장됩니다.
   - 회원의 ID를 반환하여 이후의 로직에서 사용할 수 있게 합니다.

3. **`searchFavoriteFood(EntityManager em, Long memberId)`**:
   - 특정 회원의 즐겨찾는 음식 목록을 출력하는 메소드입니다.
   - `Member` 엔티티에서 해당 회원의 `favoriteFood` 컬렉션을 가져와 각 음식을 출력합니다.

4. **`updateFavoriteFood(EntityManager em, Long memberId)`**:
   - 회원의 즐겨찾는 음식 중에서 `바닐라아이스라떼`를 삭제하고, `아이스 아메리카노`를 추가하는 메소드입니다.
   - `favoriteFood` 컬렉션에서 직접 수정하여 데이터베이스에 반영됩니다.

5. **`insertAddressAndAddressList(EntityManager em, List<Long> memberIds)`**:
   - 특정 회원에게 기본 주소와 주소 목록을 추가하는 메소드입니다.
   - `Member` 엔티티의 `address` 필드에 기본 주소를 설정하고, `addressList` 컬렉션에 여러 개의 주소를 추가합니다.
   - 변경 사항을 데이터베이스에 반영한 후, 추가된 주소 목록을 출력하여 확인합니다.

#### 실행 흐름
1. `main` 메소드가 실행되면 먼저 10개의 팀과 10명의 회원이 생성됩니다.
2. 각 회원에게 임의의 팀이 배정되며, 샘플 데이터가 데이터베이스에 저장됩니다.
3. 임의로 선택된 회원에게 즐겨찾는 음식이 추가됩니다.
4. 해당 회원의 즐겨찾는 음식 목록을 조회하고, 특정 음식을 제거하고 새로운 음식을 추가합니다.
5. 회원의 주소와 주소 목록을 설정한 후, 이를 조회하여 출력합니다.

### 예시 실행 결과
- 10개의 팀과 10명의 회원이 생성되고, 각 회원에게 임의의 팀이 배정됩니다.
- 선택된 회원에게 `짬뽕`, `떡볶이`, `바닐라아이스라떼`가 즐겨찾는 음식으로 추가됩니다.
- 이후 `바닐라아이스라떼`를 제거하고 `아이스 아메리카노`가 추가됩니다.
- 회원의 주소가 `123 Main street, Daegu, 12345`로 설정되고, 추가 주소 목록이 출력됩니다.


#### 주요 메소드

1. **`main` 메소드**:
   - 프로젝트의 진입점으로, `EntityManager`와 트랜잭션을 관리합니다.
   - `JpaBooks.initMemberTeamSampleData`를 통해 팀과 회원의 샘플 데이터를 생성합니다.
   - 생성된 회원의 ID를 이용해 즐겨찾는 음식을 추가하고, 주소 및 즐겨찾는 음식 목록을 업데이트합니다.

2. **`insertFavoriteFood`**:
   - 임의로 선택된 회원에게 즐겨찾는 음식을 추가합니다.
   - `짬뽕`, `떡볶이`, `바닐라아이스라떼`를 추가한 후, 해당 회원의 ID를 반환합니다.

3. **`searchFavoriteFood`**:
   - 특정 회원의 즐겨찾는 음식 목록을 출력합니다.

4. **`updateFavoriteFood`**:
   - 특정 회원의 즐겨찾는 음식 중 `바닐라아이스라떼`를 제거하고, `아이스 아메리카노`를 추가합니다.

5. **`insertAddressAndAddressList`**:
   - 특정 회원에게 기본 주소와 여러 개의 주소 목록을 추가합니다.
   - 추가된 주소 목록을 출력하여 확인합니다.

### 예시 실행 결과

1. 10개의 팀과 10명의 회원이 생성됩니다.
2. 각 회원에게 임의로 팀이 배정되고, 회원의 즐겨찾는 음식과 주소가 관리됩니다.
3. 특정 회원의 즐겨찾는 음식 목록이 수정되고, 주소가 업데이트됩니다.

## 실행 방법

1. `Main` 클래스를 실행하여 데이터베이스와의 연결을 설정하고, 샘플 데이터를 생성합니다.
2. 생성된 회원의 ID를 이용해 즐겨찾는 음식, 주소를 추가하고 업데이트할 수 있습니다.

## 추가 정보

- **JPA**: 이 프로젝트는 `JPA`를 사용하여 ORM 매핑을 구현하였습니다.
- **Lombok**: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor` 등의 Lombok 어노테이션을 사용하여 코드의 간결성을 유지하였습니다.
