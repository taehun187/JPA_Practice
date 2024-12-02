# JPA One-to-One Relationship Examples

이 프로젝트는 JPA(Java Persistence API)를 사용하여 **1:1 관계(One-to-One Relationship)**를 구현하는 예제를 보여줍니다. 

## 목차
1. [프로젝트 설명](#프로젝트-설명)
2. [실행 방법](#실행-방법)
3. [구현된 예제](#구현된-예제)
   - [양방향 관계 예제](#1-양방향-1:1-관계)
   - [단방향 관계 예제](#2-단방향-1:1-관계)
4. [구성 요소](#구성-요소)
5. [학습 포인트](#학습-포인트)

---

## 프로젝트 설명
이 프로젝트는 JPA를 사용하여 **1:1 관계**를 구현하는 두 가지 예제를 포함합니다:
1. **양방향 1:1 관계** (`bidirectional` 패키지)
2. **단방향 1:1 관계** (`unidirectional` 패키지)

각 예제는 실제 데이터베이스에 데이터를 저장하고 가져오는 과정을 보여줍니다.

---

## 실행 방법
1. `persistence.xml` 파일에서 데이터베이스 설정을 확인합니다.
2. 프로젝트를 실행하여 두 가지 메인 클래스를 실행합니다:
   - `com.taehun.jpaexs.one2one.bidrectional.Main` (양방향 관계)
   - `com.taehun.jpaexs.one2one.unidirectional.Main` (단방향 관계)
3. 결과를 데이터베이스 또는 콘솔에서 확인합니다.

---

## 구현된 예제

### 1. 양방향 1:1 관계
**패키지**: `com.taehun.jpaexs.one2one.bidrectional`

#### 관계 설명:
- **Traveler**와 **PassPort**는 양방향 1:1 관계를 가집니다.
- 양방향 관계에서:
  - `Traveler`는 `PassPort`를 참조하고,
  - `PassPort`도 `Traveler`를 참조합니다.

#### 주요 클래스:
- `Traveler`: 여행자를 나타내는 엔티티.
- `PassPort`: 여권 정보를 나타내는 엔티티.

#### 주요 코드:
```java
@OneToOne(mappedBy = "passport")
private Traveler travler;

@OneToOne
@JoinColumn(name = "PASSPORT_ID", unique = true)
private PassPort passport;

