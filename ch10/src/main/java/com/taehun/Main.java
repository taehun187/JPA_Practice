package com.taehun;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.taehun.entity.Member;
import com.taehun.entity.Team;
import com.taehun.entity.UserDTO;
import com.taehun.qentity.QMember;
import com.taehun.qentity.QTeam;
import com.taehun.utilities.JpaBooks;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.Tuple;
import static com.taehun.qentity.QMember.member;
import static com.taehun.qentity.QTeam.team;

public class Main {

    static final int TEAM_NUMBERS = 10;

    static final int MEMBER_NUMBERS = 100;

    static final int POST_NUMBERS = 10;
    static final int COMMENT_NUMBERS = 10;

    static final long POST_STRING_MAX_SIZE = 1500L;

    static final long COMMENT_STRING_MAX_SIZE = 300L;

    private static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("jpabook");

    public static void main(String... args) throws InterruptedException {


//        List<Long> membersIds = JpaBooks.initMemberTeamSampleData(emf,
//                TEAM_NUMBERS,
//                MEMBER_NUMBERS);

//        List<Long> postIds = JpaBooks.initPostCommentSampleData(emf,
//                 POST_NUMBERS,
//                POST_STRING_MAX_SIZE,
//                COMMENT_NUMBERS,
//                COMMENT_STRING_MAX_SIZE);
        // 1페이지, 한 번에 10개씩 가져오기
        EntityManager em = emf.createEntityManager();
        getPagedPosts(em, 1, 10);

        em.close();
        emf.close();
        emf.close();
    }
    public static void getPagedPosts(EntityManager em, int pageNumber, int pageSize) {
        // JPQL 쿼리 작성
        TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p ORDER BY p.title ASC", Post.class);

        // 페이징 처리 (첫 번째 결과와 가져올 최대 개수 설정)
        query.setFirstResult((pageNumber - 1) * pageSize);  // 시작 인덱스 설정
        query.setMaxResults(pageSize);  // 한 번에 가져올 결과 개수 설정

        // 결과 리스트 조회
        List<Post> postList = query.getResultList();

        // 결과 출력
        for (Post post : postList) {
            System.out.println("Post ID: " + post.getId() + ", Title: " + post.getTitle());
        }
    }


    public static void queryMemberOfTypedQuery() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // JPQL을 사용하여 Member 엔티티 조회
            TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
            List<Member> members = query.getResultList();

            // 조회된 결과 출력
            for (Member m : members) {
                System.out.printf("Member ID: %d, Name: %s \n", m.getId(), m.getName());
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }
    }

    public static void queryColumnsOfQuery() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Query query = em.createQuery("SELECT m.name, m.age FROM Member m");
            List<Object[]> resultList = query.getResultList();

            for (Object[] result : resultList) {
                String name = (String) result[0];
                Integer age = (Integer) result[1];
                System.out.printf("name: %s, age: %d \n", name, age);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }
    }

    public static void queryParameterBounding(List<Long> membersIds) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Random memberId로 Member 엔티티 조회
            Long memberId = JpaBooks.generateRandomID(membersIds);
            Member foundMember = em.find(Member.class, memberId);
            em.clear();

            // 찾은 멤버의 이름으로 파라미터 바인딩
            String userNameParam = foundMember.getName();
            System.out.println("찾은 회원 이름: " + userNameParam);

            // JPQL을 사용한 파라미터 바인딩 쿼리
            List<Member> members = em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
                    .setParameter("name", userNameParam)  // 파라미터 바인딩
                    .getResultList();

            // 조회된 결과 출력
            for (Member m : members) {
                System.out.printf("조회된 회원 이름: %s \n", m.getName());
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }
    }

    public static void useUserDTO() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TypedQuery<UserDTO> query = em.createQuery(
                    "SELECT new com.jinsu.entity.UserDTO(m.name, m.age)FROM Member m",
                    UserDTO.class);

            List<UserDTO> resultList = query.getResultList();
            for (UserDTO d : resultList) {
                System.out.println("name = " + d.getName());
                System.out.println("age = " + d.getAge());
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }
    }

    public static void testJPAcriteria(List<Long> membersIds) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Long memberId = JpaBooks.generateRandomID(membersIds);
            Member foundMember = em.find(Member.class, memberId);
            em.clear();

            String name = foundMember.getName();
            System.out.println("name=" + name);
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> cq = cb.createQuery(Member.class);

            Root<Member> member = cq.from(Member.class);
            cq.
                    select(member).
                    where(cb.equal(member.get("neme"), name));

            List<Member> members = em.createQuery(cq).getResultList();
            for (Member m : members) {
                System.out.println("member name: " + m.getName());
            }


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }
    }

    public static void getSingleRelationalShipEntity() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();


            TypedQuery<Team> query = em.createQuery("SELECT m.team FROM Member m", Team.class);

            List<Team> teamList = query.getResultList();
            for (Team t : teamList) {
                System.out.printf("Team name:" + t.getName());
            }


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }


    }

    public static void testInnerJoin() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            List<Team> teamList = em.createQuery(
                    "Select distinct t from Member m join m.team t",
                    Team.class).getResultList();

            for (Team t : teamList) {
                System.out.println("team = " + t.getName());
            }


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }

    }

    public static void testLeftOuterJoin() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
//
//             List<Member> memList = em.createQuery(
//            		 				"Select m from Member m left outer join m.team t",
//            		 				Member.class).getResultList();
//
//             for(Member m: memList) {
//            	 System.out.println("Member name = " + m.getName());
//             }
//
            @SuppressWarnings("unchecked")
            List<Object[]> objsList =
                    em.createQuery("Select m, t from Member m left outer join m.team t")
                            .getResultList();

            for (Object[] objs : objsList) {
                Member m = (Member) objs[0];
                Team t = (Team) objs[1];
                System.out.printf("member name:%s, team name:%s \n",
                        m.getName(), t.getName());
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }
    }

    public static void testCrossJoin() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            String query = "select m, t from Member m, Team t";

            @SuppressWarnings("unchecked")
            List<Object[]> objsList =
                    em.createQuery(query)
                            .getResultList();

            for (Object[] objs : objsList) {
                Member m = (Member) objs[0];
                Team t = (Team) objs[1];
                System.out.println("member:" + m + "team:" + t);


            }


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }
    }

    public static void testAggregateFunction() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TypedQuery<Long> sumQuery =
                    em.createQuery("select sum(m.age) from Member m", Long.class);

            Long totalAge = sumQuery.getSingleResult();
            System.out.println("Sum of Age:" + totalAge);

            Double averageAge =
                    em.createQuery("select avg(m.age) from Member m", Double.class)
                            .getSingleResult();
            System.out.println("Avg of Age:" + averageAge);

            Integer maxValue =
                    em.createQuery("select max(m.age) from Member m", Integer.class)
                            .getSingleResult();
            System.out.println("max of Age:" + maxValue);

            Integer minValue =
                    em.createQuery("select min(m.age) from Member m", Integer.class)
                            .getSingleResult();
            System.out.println("min of Age:" + minValue);


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }

    }


    public static void testGroupbyHavingOrderby() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            String query =
                    "SELECT t.name, AVG(m.age) " +        // 평균 나이가 25세 이상인 팀을을 쿼리
                            "FROM Team t JOIN t.memberList m " +  // 팀에 속한 멤버들에서부터 시작.
                            "GROUP BY t.name " +               // 팀 이름별로 그룹핑
                            "HAVING AVG(m.age) > 30 " +        // 평균 나이가 30세 이상
                            "ORDER BY AVG(m.age) DESC ";

            @SuppressWarnings("unchecked")
            List<Object[]> objsList =
                    em.createQuery(query)
                            .getResultList();

            for (Object[] objs : objsList) {
                String teamName = (String) objs[0];
                Double avgAge = (Double) objs[1];
                System.out.println("Team" + teamName + ", Average Age: " + avgAge);
            }


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }
    }

    public static void setSubQueryInSelect() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 각 Member의 name과 해당 Member가 속한 Team의 총 Member 수를 구하는 쿼리
            String query =
                    "SELECT m.name, " +
                            "(SELECT COUNT(subM) FROM Member subM WHERE subM.team = m.team) " +
                            "AS teamMemberCount " +
                            "FROM Member m ";

            @SuppressWarnings("unchecked")
            List<Object[]> objsList =
                    em.createQuery(query)
                            .getResultList();

            for (Object[] objs : objsList) {
                String memName = (String) objs[0];
                Long count = (Long) objs[1];
                System.out.println("Member name: " + memName + ", count: " + count);
            }


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }
    }

    public static void testSubQueryInWhere() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Team의 가장 나이가 많은 Member를 찾는 쿼리
            String query =
                    "SELECT m " +
                            "FROM Member m " +
                            "WHERE m.age = (SELECT MAX(subM.age) FROM Member subM WHERE subM.team = m.team)";


            List<Member> memList =
                    em.createQuery(query, Member.class)
                            .getResultList();

            for (Member m : memList) {
                System.out.println("Team: " + m.getTeam() +
                        ", Member: " + m.getName() +
                        ", Max Age: " + m.getAge());
            }


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }
    }

    /*SELECT m.name, subQuery.teamName
    FROM Member m,
         (SELECT t.TEAM_ID, t.name AS teamName
          FROM Team t
          JOIN Member m ON t.TEAM_ID = m.TEAM_ID
          GROUP BY t.TEAM_ID, t.name
          HAVING AVG(m.age) > 30) AS subQuery
    WHERE m.TEAM_ID = subQuery.TEAM_ID;*/
    public static void testSubQueryInFromAlternate() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 네이티브 SQL 쿼리로 팀의 멤버 평균 나이가 30 이상인 팀 이름과 그 팀에 속한 멤버들의 이름을 출력
            String query =
                    "SELECT m.name, subQuery.teamName " +
                            "FROM Member m " +
                            "JOIN (SELECT t.TEAM_ID AS team_id, t.name AS teamName " +
                            "      FROM Teams t " +
                            "      JOIN Member m ON t.TEAM_ID = m.TEAM_ID " +
                            "      GROUP BY t.TEAM_ID, t.name " +
                            "      HAVING AVG(m.age) > 30) subQuery " +
                            "ON m.TEAM_ID = subQuery.team_id";

            // 네이티브 쿼리 실행
            Query nativeQuery = em.createNativeQuery(query);
            @SuppressWarnings("unchecked")
            List<Object[]> objsList = nativeQuery.getResultList();

            for (Object[] objs : objsList) {
                String memName = (String) objs[0];
                String teamName = (String) objs[1];
                System.out.println("Member name: " + memName + ", Team name: " + teamName);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();  // EntityManager 닫기
        }
    }

    public static void testQueryDSLSelect() {
        EntityManager em = emf.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        EntityTransaction tx = em.getTransaction(); // 트랜잭션 가져오기

        try {
            tx.begin();  // 트랜잭션 시작

            Member member = new Member();
            member.setName("sungwon");
            member.setAge(20);

            em.persist(member);  // 데이터 저장
            em.flush();
            em.clear();

            QMember qMember = QMember.member;
            List<Member> memberList = queryFactory
                    .selectFrom(qMember)  // selectFrom으로 변경
                    .where(qMember.id.eq(member.getId()))
                    .fetch();

            for (Member mem : memberList) {
                System.out.println("Member name: " + mem.getName());
            }

            tx.commit();  // 트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();  // 에러 발생 시 롤백
            }
        } finally {
            if (em.isOpen()) {
                em.close();  // EntityManager 닫기
            }
        }
    }

    public static void testQueryDSLInsert() {
        EntityManager em = emf.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        EntityTransaction tx = em.getTransaction(); // 트랜잭션 가져오기

        try {
            tx.begin();  // 트랜잭션 시작

            Member member = new Member();
            member.setName("1stQueryDSLInsert");
            member.setAge(40);

            em.persist(member);  // 데이터 저장

            tx.commit();  // 트랜잭션 커밋
            System.out.println("Insert complete: " + member.getName());
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();  // 에러 발생 시 롤백
            }
        } finally {
            if (em.isOpen()) {
                em.close();  // EntityManager 닫기
            }
        }
    }

    public static void testQueryDSLUpdate() {
        EntityManager em = emf.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        EntityTransaction tx = em.getTransaction(); // 트랜잭션 가져오기

        try {
            tx.begin();  // 트랜잭션 시작

            Member member = new Member();
            member.setName("1stQueryDSLUpdate");
            member.setAge(30);

            em.persist(member);  // 데이터 저장
            em.flush();
            em.clear();

            QMember qMember = QMember.member;
            String newName = "New Name";
            long affectedRows = queryFactory
                    .update(qMember)
                    .set(qMember.name, newName)
                    .where(qMember.id.eq(member.getId()))
                    .execute();

            tx.commit();  // 트랜잭션 커밋
            System.out.println("Updated Rows: " + affectedRows);
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();  // 에러 발생 시 롤백
            }
        } finally {
            if (em.isOpen()) {
                em.close();  // EntityManager 닫기
            }
        }
    }

    public static void testQueryDSLDelete() {
        EntityManager em = emf.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        EntityTransaction tx = em.getTransaction(); // 트랜잭션 가져오기

        try {
            tx.begin();  // 트랜잭션 시작

            Member member = new Member();
            member.setName("sungwon");
            member.setAge(30);

            em.persist(member);  // 데이터 저장
            em.flush();
            em.clear();

            QMember qMember = QMember.member;
            long affectedRows = queryFactory
                    .delete(qMember)
                    .where(qMember.id.eq(member.getId()))
                    .execute();

            tx.commit();  // 트랜잭션 커밋
            System.out.println("Deleted Rows: " + affectedRows);
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();  // 에러 발생 시 롤백
            }
        } finally {
            if (em.isOpen()) {
                em.close();  // EntityManager 닫기
            }
        }
    }

    public static void testQueryDSLTeamMember() {
        EntityManager em = emf.createEntityManager();  // EntityManagerFactory에서 EntityManager 생성
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);  // JPAQueryFactory 생성
        EntityTransaction tx = em.getTransaction();  // 트랜잭션 가져오기

        try {
            tx.begin();  // 트랜잭션 시작

            QTeam team = QTeam.team;  // QTeam 객체 생성
            QMember member = QMember.member;  // QMember 객체 생성

            // JPAQueryFactory를 사용하여 쿼리 작성
            // QUeryDSL에서 정의한 클래스.
            // 파이썬에서 Tuple을 지원함: List이다. but 불변객체(immutable)
            List<Tuple> result = queryFactory
                    .select(team.name, member.age.avg())
                    .from(team)
                    .join(team.memberList, member)  // 팀과 멤버의 관계를 조인
                    .groupBy(team.name)
                    .having(member.age.avg().goe(30))  // 평균 나이가 30 이상인 팀
                    .orderBy(member.age.avg().desc())  // 평균 나이가 높은 순으로 정렬
                    .fetch();

            // 결과 출력
            for (Tuple tuple : result) {
                String teamName = tuple.get(team.name);
                Double avgAge = tuple.get(member.age.avg());
                System.out.println("Team: " + teamName + ", Avg Age: " + avgAge);
            }

            tx.commit();  // 트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();  // 에러 발생 시 롤백
            }
        } finally {
            if (em.isOpen()) {
                em.close();  // EntityManager 닫기
            }
        }
    }

    public static void testPagingAPIByJPQL() {
        EntityManager em = emf.createEntityManager();  // EntityManagerFactory에서 EntityManager 생성
        EntityTransaction tx = em.getTransaction();  // 트랜잭션 가져오기

        try {
            tx.begin();  // 트랜잭션 시작

            TypedQuery<Member> query =
                    em.createQuery("SELECT m FROM Member m ORDER BY m.name DESC", Member.class)
                            .setFirstResult(10)
                            .setMaxResults(20);
            List<Member> memberList = query.getResultList();
            for (Member member : memberList) {
                System.out.println("member = " + member.getId());
            }


            tx.commit();  // 트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();  // 에러 발생 시 롤백
            }
        } finally {
            if (em.isOpen()) {
                em.close();  // EntityManager 닫기

            }
        }
    }

    public static void initMemberTeamSampleData() {
        EntityManager em = emf.createEntityManager();
        // 트랜잭션 시작

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 팀 생성 및 저장
            Team team = new Team();
            team.setName("Development Team");
            em.persist(team);  // 팀을 먼저 저장

            // 멤버 생성 및 팀 설정 후 저장
            Member member = new Member();
            member.setName("John Doe");
            member.setAge(30);
            member.setTeam(team);  // 팀을 참조
            em.persist(member);  // 멤버 저장

            tx.commit();  // 트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();  // 오류 발생 시 롤백
            }
        }
    }

    public static void testQueryDSLTeamMember1() {
        EntityManager em = emf.createEntityManager();  // EntityManagerFactory에서 EntityManager 생성
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);  // JPAQueryFactory 생성
        EntityTransaction tx = em.getTransaction();  // 트랜잭션 가져오기

        try {
            tx.begin();  // 트랜잭션 시작

            QTeam team = QTeam.team;  // QTeam 객체 생성
            QMember member = QMember.member;  // QMember 객체 생성

            // JPAQueryFactory를 사용하여 쿼리 작성
            // QUeryDSL에서 정의한 클래스.
            // 파이썬에서 Tuple을 지원함: List이다. but 불변객체(immutable)
            List<Member> memberList = queryFactory
                    .selectFrom(member)
                    .orderBy(member.id.asc())
                    .offset(0)
                    .limit(20)
                    .fetch();

            for (Member mem : memberList) {
                System.out.println("member = " + mem.getId());

            }

            tx.commit();  // 트랜잭션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();  // 에러 발생 시 롤백
            }
        } finally {
            if (em.isOpen()) {
                em.close();  // EntityManager 닫기
            }
        }
    }

    public static Long getLastIdofMember() {
        EntityManager em = emf.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        EntityTransaction tx = em.getTransaction();

        Long lastMemberId = -1L;
        try {
            tx.begin();


            QMember member = QMember.member;
            lastMemberId = queryFactory
                    .select(member.id)
                    .from(member)
                    .orderBy(member.id.desc())
                    .limit(1)
                    .fetchOne();


            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        return lastMemberId;
    }

    public static Long getPagedMembers(Long lastMemberId, int limit) {
        EntityManager em = emf.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        EntityTransaction tx = em.getTransaction();
        List<Member> members = null;

        try {
            tx.begin();

            QMember member = QMember.member;

            if (lastMemberId == null) {
                members = queryFactory
                        .selectFrom(member)
                        .orderBy(member.id.asc())
                        .limit(limit)
                        .fetch();
            } else {
                members = queryFactory
                        .selectFrom(member)
                        .where(member.id.gt(lastMemberId))
                        .orderBy(member.id.asc())
                        .limit(limit)
                        .fetch();
            }

            for (Member m : members) {
                System.out.println(m);
            }

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        if (members != null) {
            if (!members.isEmpty()) {
                // members.size() - 1는 members 리스트의 마지막 엘리먼트 인덱스 값
                return members.get(members.size() - 1).getId();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void testPaginaAPIWithoutOffsetByQueryDsl() {
        Long queryedLastMemberId = getLastIdofMember();

        int pageSize = 20;

        // 총 페이지 수
        int totalPages = (MEMBER_NUMBERS + pageSize - 1) / pageSize; // 올림 계산

        Long lastMemberId = null;  // 첫 페이지의 페이징을 위한 코드...

        for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
            System.out.println("Page " + currentPage + ":");

            lastMemberId = getPagedMembers(lastMemberId, pageSize);

            if( queryedLastMemberId == lastMemberId){
                System.out.println(("Okay"));
            }

//            if (currentPage == 1) {
//                System.out.println("queryedLastMemberId: " + queryedLastMemberId);
//                System.out.println("lastMemberId (after first page): " + lastMemberId);
//
//                if (queryedLastMemberId.equals(lastMemberId)) {
//                    System.out.println("lastMemberId and queryedLastMemberId are identical.");
//                } else {
//                    System.out.println("lastMemberId and queryedLastMemberId are different.");
//                }
//            }
        }
    }
    public static void testFetchJoinByJPQL(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            List<Member> memberList =
                    em.createQuery("SELECT m from Member m join fetch m.team", Member.class)
                            .getResultList();
            for(Member m : memberList){
                Team team = m.getTeam();
                System.out.println("Member name=" + m.getName() + ", team id=" + m.getId() + ", name=" + team.getName());
            }
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
    public static void testFetchJoinByQueryDsl(){
        EntityManager em = emf.createEntityManager();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            QMember member = QMember.member;
            QTeam team = QTeam.team;

            List<Member> memberList = queryFactory
                    .selectFrom(member)
                    .join(member.team, team).fetchJoin()
                    .fetch();



            for(Member m : memberList){
                Team t = m.getTeam();
                System.out.println("Member name=" + m.getName() + ", team id=" + t.getId() + ", name=" + t.getName());
            }

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
    public static void testCollectionFetchJoin(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        String nameParamet = "team2";
        try {
            tx.begin();

//            List<Team> teams = em.createQuery("select t from Team t join fetch t.memberList", Team.class)
//                            .getResultList();
//
//            for(Team t : teams) {
//                System.out.printf("Team id:%d", t.getId());
//                for (Member m : t.getMemberList()){
//                    System.out.printf("Member id:%d, Team name:%s \n", m.getId(), m.getTeam()) ;
            // One : Team
            // Many : Member
            // OneToMany join : 중복된 결과값이 발생함!!!
//            List<Team> teams = em.createQuery("select t from Team t join fetch t.memberList where t.name = :name",
//                            Team.class)
//                    .setParameter("name", nameParamet)
//                    .getResultList();

            List<Team> teams = em.createQuery("select distinct t from Team t join fetch t.memberList where t.name = :name",
                            Team.class)
                    .setParameter("name", nameParamet)
                    .getResultList();

            for(Team t : teams) {
                System.out.printf("Team id:%d", t.getId());
                for (Member m : t.getMemberList()){
                    System.out.printf("Member id:%d, Team name:%s \n", m.getId(), m.getTeam()) ;

                }
            }

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

    }
}










