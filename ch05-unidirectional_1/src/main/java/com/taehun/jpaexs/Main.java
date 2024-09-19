package com.taehun.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook2");
		EntityManager em = emf.createEntityManager();
		
		List<Long> hello = saveMembersAndReturnMemberIds(emf);
		

		
		String Enters = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
		System.out.println(Enters);
		printTeamnamesforMemberids(emf,hello);
		
//		SessionFactory sf;
		
		
	}
	
	public void testmethod(EntityManagerFactory emf)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try
		{
			System.out.println("---------------------------------INSERT INTO TEAM----------------------------------");
			tx.begin();
			Team team = new Team();
			team.setName("team1");
			em.persist(team);
			
			System.out.println("---------------------------------INSERT INTO MEMBER----------------------------------");
			Member member = new Member();
			member.setName("AAA");
			member.setTeam(team);
			em.persist(member);
			
			
			em.flush();
			em.clear();
			Member gotmember = em.find(Member.class, member.getId());

			System.out.println("-----------------------------------------------------------------------------");
			System.out.println("-----------------------------------------------------------------------------");
			
			Team goTeam = gotmember.getTeam();
			Long Teamids = goTeam.getTeamId();
			System.out.println("*************************************************************************************");
			
			
			System.out.println(gotmember);
		
			
		
			try
			{
				Thread.sleep(1);
			}
			catch(Exception e)
			{
				em.clear();
			}
	
	
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

	public static List<Long> saveMembersAndReturnMemberIds(EntityManagerFactory emf)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
 		List<Long> memberid = new ArrayList<>();
		try
		{
			System.out.println("\n\n\n\n\n\n");
			tx.begin();
			Member mem1 = new Member("one");
			Member mem2 = new Member("two");
			Team t1 = new Team("T1");
			em.persist(mem1);
			em.persist(mem2);
			em.persist(t1);
			
			t1.addMember(mem1);
			t1.addMember(mem2);
			
			memberid.add(mem1.getId());
			memberid.add(mem2.getId());
			em.flush();
			em.clear();
			
			Member mem = em.find(Member.class, memberid.get(0)); //1차 캐시에는 Member 객체와 해당 Member와 연관된 Team 객체가 저장됩니다.
			
			System.out.println(mem.getName());
			
/*			
			select
		        members0_.TEAM_ID as team_id3_0_0_,
		        members0_.MEMBER_ID as member_i1_0_0_,
		        members0_.MEMBER_ID as member_i1_0_1_,
		        members0_.name as name2_0_1_,
		        members0_.TEAM_ID as team_id3_0_1_ 
	        from
		        Member members0_ 
	        where
		        members0_.TEAM_ID=?
*/
			
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			mem.getTeam().getMembers().get(0);
//			System.out.println("member 이름 :" + mem.getTeam().getMembers().get(0));
			
			
			tx.commit();
		}
		catch(Exception e)
		{ 
			
			e.printStackTrace();
			tx.rollback();
		}
		finally 
		{
			em.close();
			
		}
		return memberid;
	}
	
	
	public static List<Team> printTeamnamesforMemberids(EntityManagerFactory emf, List<Long> memIds)
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<Member> members = new ArrayList<>();
		List<Team> teams = new ArrayList<>();
		try
		{
			tx.begin();
			for (Long id : memIds)
			{
				Member member = em.find(Member.class, id.longValue());
				members.add(member);
				if(teams.indexOf(member.getTeam()) == -1) // -1 이라는거는 없다는거다
				{
					teams.add(member.getTeam());
				}
			}
			for(Team team : teams)
			{
				System.out.printf("팀 이름: %s", team.getName());
				System.out.println("");
				for (Member member : team.getMembers()) //1차 캐시에는 Member 객체와 해당 Member와 연관된 Team 객체가 저장됩니다.
				{
					System.out.printf("멤버 Id %d 이름: %s", member.getId(), member.getName());
					System.out.println("");
				}
			}
			
			tx.commit();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			tx.rollback();
		}
		finally 
		{
			em.close();
			
		}
		return teams;
	}
	
	
	
}