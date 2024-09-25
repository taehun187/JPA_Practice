# JPA Member & Team Management

## 프로젝트 설명
이 프로젝트는 **JPA(Java Persistence API)**를 활용하여 `Member`와 `Team` 엔티티 간의 관계를 관리하는 간단한 애플리케이션입니다. 각각의 팀에는 여러 멤버가 포함될 수 있으며, 멤버와 팀 간의 **양방향 관계**가 설정되어 있습니다. 이를 통해 기본적인 데이터베이스 작업(삽입, 조회, 관계 매핑)을 학습하고 구현할 수 있습니다.

## 주요 기능
- **멤버와 팀의 등록**: 새로운 멤버와 팀을 생성하여 데이터베이스에 저장할 수 있습니다.
- **멤버-팀 관계 설정**: 멤버를 팀에 추가하여 팀과 멤버 간의 관계를 설정할 수 있습니다.
- **멤버와 팀 조회**: 멤버 ID를 통해 멤버가 속한 팀을 조회하고, 해당 팀의 모든 멤버 목록을 확인할 수 있습니다.

## 엔티티(Entity) 구조
### 1. Member (멤버)
- `Member`는 팀에 속하는 개별 멤버를 나타냅니다.
- 필드:
  - `id` (Long): 멤버 ID (Primary Key)
  - `name` (String): 멤버 이름
  - `team` (Team): 이 멤버가 속한 팀과의 **Many-to-One** 관계

### 2. Team (팀)
- `Team`은 멤버들이 속하는 그룹을 나타냅니다.
- 필드:
  - `TeamId` (Long): 팀 ID (Primary Key)
  - `name` (String): 팀 이름
  - `members` (List<Member>): 이 팀에 속한 멤버들과의 **One-to-Many** 관계

## 주요 메서드
### 1. `saveMembersAndReturnMemberIds(EntityManagerFactory emf)`
- 새로운 멤버와 팀을 데이터베이스에 저장하고, 저장된 멤버들의 ID 리스트를 반환합니다.

### 2. `printTeamnamesforMemberids(EntityManagerFactory emf, List<Long> memIds)`
- 멤버 ID 리스트를 통해 멤버와 그들이 속한 팀 정보를 출력합니다.

### 3. `testmethod(EntityManagerFactory emf)`
- 멤버와 팀을 데이터베이스에 저장하고 관계를 설정한 후, 저장된 데이터를 조회하여 출력합니다.

## 데이터베이스 테이블 관계
- **Member**: `@ManyToOne`으로 **Team**과 연관되며, `TEAM_ID`를 외래 키로 가집니다.
- **Team**: `@OneToMany`로 **Member**와 양방향 관계를 맺으며, 각 팀은 여러 멤버를 가질 수 있습니다.

## 실행 방법
1. **JPA 설정**: `persistence.xml` 파일에서 데이터베이스 연결 정보를 설정합니다.
2. **코드 실행**: `Main` 클래스를 실행하여 `Member`와 `Team` 엔티티 간의 관계 설정 및 CRUD 작업을 수행할 수 있습니다.


