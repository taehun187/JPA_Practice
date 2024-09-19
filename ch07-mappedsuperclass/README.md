```markdown
# JPA Hibernate 연습 코드

## 개요
이 프로젝트는 **JPA(Java Persistence API)**와 **Hibernate**를 사용하여 간단한 엔티티를 데이터베이스에 저장하고 조회하는 연습용 코드입니다. `Member`라는 엔티티를 생성한 후, 이를 데이터베이스에 저장하고 다시 불러오는 과정을 연습해볼 수 있습니다.

## 파일 구조
```bash
src
└── com
    └── taehun
        └── entity
            ├── BaseEntity.java
            ├── Member.java
            └── Main.java
```

- **`BaseEntity.java`**: 모든 엔티티에서 공통으로 사용할 기본 필드들을 정의한 클래스입니다.
- **`Member.java`**: 실제 데이터베이스에 저장될 `Member` 엔티티입니다.
- **`Main.java`**: `Member` 엔티티를 저장하고 조회하는 테스트 코드입니다.

## 설정 파일

### `persistence.xml`

이 연습 코드는 H2 데이터베이스를 사용합니다. 아래는 `persistence.xml` 설정 예시입니다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.2">
    <persistence-unit name="jpabook">
        <properties>
            <!-- 필수 속성들 -->
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
            <property name="javax.persistence.jdbc.user" value="sa"/>
            <property name="javax.persistence.jdbc.password" value=""/>
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
            
            <!-- 옵션 설정 -->
            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.format_sql" value="true" />
            <property name="hibernate.hbm2ddl.auto" value="create" />
        </properties>
    </persistence-unit>
</persistence>
```

### 설정 설명

- **`javax.persistence.jdbc.driver`**: 사용할 데이터베이스 드라이버 (`H2` 데이터베이스용)
- **`javax.persistence.jdbc.url`**: 데이터베이스 URL (`H2` 데이터베이스 사용)
- **`hibernate.dialect`**: Hibernate에서 사용할 SQL 방언 (H2)
- **`hibernate.show_sql`**: SQL 쿼리를 콘솔에 출력할지 여부 (`true`로 설정하면 실행된 SQL을 볼 수 있음)
- **`hibernate.hbm2ddl.auto`**: 데이터베이스 테이블 생성 및 초기화 방식 (`create`로 설정하면 실행할 때마다 테이블을 새로 생성함)

## 실행 방법

1. **프로젝트 빌드**:
   - 이 프로젝트는 Maven 또는 Gradle을 사용하여 빌드할 수 있습니다. IDE에서 해당 도구를 설정한 후 빌드하세요.

2. **H2 데이터베이스 사용**:
   - 이 연습 코드에서는 **H2 데이터베이스**를 사용합니다. 별도의 설치가 필요하지 않으며, 설정된 URL을 통해 자동으로 메모리 또는 파일 모드로 동작합니다.

3. **코드 실행**:
   - `Main.java` 파일을 실행하여 `Member` 엔티티가 데이터베이스에 저장되고 조회되는 과정을 확인해볼 수 있습니다.

4. **실행 결과**:
   - 콘솔에 SQL 쿼리가 출력되며, 데이터베이스에 저장된 `Member` 객체가 조회됩니다.

## 코드 설명

### `BaseEntity.java`
모든 엔티티에 공통적으로 사용할 필드들을 정의한 클래스입니다.

```java
package com.taehun.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String createdBy;
    
    @CreationTimestamp
    private LocalDateTime creatDate;
    
    private String lastModifiedBy;
    
    @CreationTimestamp
    private LocalDateTime lastModifiedDate;

    // Getters and Setters...
}
```

### `Member.java`
`BaseEntity`를 상속받아 실제 데이터베이스에 저장되는 `Member` 엔티티를 정의합니다.

```java
package com.taehun.entity;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;
    
    private String name;

    // Getters and Setters...
}
```

### `Main.java`
`Member` 엔티티를 생성하고 데이터베이스에 저장한 후, 다시 조회하는 연습용 메인 클래스입니다.

```java
package com.taehun.entity;

import javax.persistence.*;

public class Main {
    public static void main(String... args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            
            Member mem = new Member();
            mem.setName("Lee");
            em.persist(mem);
            
            em.flush();
            em.clear();
            
            Member foundmem = em.find(Member.class, mem.getId());
            
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
```

## 

- 이 코드는 JPA와 Hibernate를 연습하는 코드입니다.
- **H2 데이터베이스**를 가볍고 설치가 필요 없기 때문에 학습용으로 사용했습니다.
- `hibernate.show_sql`을 통해 실행된 SQL 쿼리를 콘솔에서 확인할 수 있습니다.
```
