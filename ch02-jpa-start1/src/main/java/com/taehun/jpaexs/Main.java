package com.taehun.jpaexs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

public class Main {


	public static void main(String[] args) {
		 RoleType admin = RoleType.Admin;
		 RoleType user = RoleType.User;
		 RoleType Guest = RoleType.Guest;
		// 이 jpabook은  persistence.xml파일에  <persistence-unit name="jpabook">
		//JPA 프로그래밍을 하기 위해서는 항상 엔티티 매니저가 필요하다
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		
//		SessionFactory sf;
		EntityTransaction tx = em.getTransaction();
		try
		{
			Long id = 1L;
			Integer age = 70;
			
			
			tx.begin();
			
			Member mem1 = save(em,"AAAAA", 40, admin);

			Member mem2 = save(em,"bbbb", 50, RoleType.User);
			Member mem3 = save(em,"CCC", 50, Guest);
			
			Member mem4 = find(em, 2L);
			//여기서 m은 각 로우(엔티티 클래스 객체와 매핑)		
			TypedQuery<?> query = em.createQuery("select m from Member m", Member.class);
			List<?> members = query.getResultList();
			for(Object a : members)
			{
				Member c = (Member)a;
				System.out.println(c.getUsername());
			}
			mem4.setUsername("ttttttt");//1차 캐시의 해당 엔티티 클래스 객체가 수정 -> 스냅샷 발생...
			mem4.setAge(1);						/// 엔티티 클래스 객체 세터 메서드는 특별!
			em.flush(); // 곧바로, CRUD가 실행됨
			
			System.out.println("-----------------------------------------------------------------------------");
			try
			{
				Thread.sleep(1);
			}
			catch(Exception e)
			{
				em.clear();
			}
			System.out.println("*************************************************************************************");
			update(em, id, age);
			Member a = find(em,id); 
			
			System.out.println("member.name: " + a.getUsername());
			id = 1L;
			Member b = find(em,id); 
			
	
			System.out.println("member.name: " + b.getUsername());

		
			
//			delete(em, id);
			//끝나고 커밋
			tx.commit(); // 더티 체킹 수행 through 영속 컨텍스트의 스냅샷: update쿼리 생성 후, 실행

		}
		catch(Exception e)
		{
			tx.rollback();
			e.printStackTrace();
		}
		finally
		{
			em.close();
			emf.close();
		}

	}
	
	
	
	 private static Member save(final EntityManager em, String name, int age, RoleType role) 
	 {
			Member member = new Member();
			member.setUsername(name);
			member.setAge(age);
			member.setRoleType(role);
			em.persist(member);
			return member;
			
	 }

	  private static Member find(final EntityManager em, final Long id) 
	  {
	    return em.find(Member.class, id);
	  }

	  private static List<Member> findList(final EntityManager em, final String query) 
	  {
	    return em.createQuery(query, Member.class).getResultList();
	  }

	  private static void update(final EntityManager em, final Long id, final Integer age) 
	  {
	    Member member1 = em.find(Member.class, id);
	    member1.setAge(age);
	  }

	  private static void delete(final EntityManager em, final Long id) 
	  {
	    Member member = em.find(Member.class, id);
	    if (member != null) em.remove(member);
	  }

}