package com.taehun.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

import com.taehun.entity.Member;
import com.taehun.entity.Team;

public class JpaBooks {

static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	public static List<Long> initMemberTeamSampleData(EntityManagerFactory emf, int teamNumbers, int memberNumbers)
	{
		EntityManager em = emf.createEntityManager();
		List<Long> teamids = new ArrayList<>();
		List<Long> memberIds = new ArrayList<>();
		EntityTransaction tx = em.getTransaction();
		try
		{
			tx.begin();

			for (int i=0; i<teamNumbers; i++)
			{
				Team team = new Team();
				team.setName("team :"+i);
				em.persist(team);
				teamids.add(team.getId());
			}
			Long minIdValue = Collections.min(teamids);// teamids 엘리먼트중에 최소값을 가진 애를 찾아주는 static 메소드
			Long MaxIdValue = Collections.max(teamids);// 가장 큰값을 가진 엘리먼트를 찾아줌
			
			
			for (int i=0; i<memberNumbers; i++)
			{
				Member member = new Member();
				member.setName("mem :"+i);
				Long targetTeamId = generateRandomNumber(minIdValue, MaxIdValue);
				
				Team team = em.find(Team.class, targetTeamId);
				team.addMember(member);
				
				
				
				em.persist(member);
				memberIds.add(member.getId());

			}
			em.flush();
			em.clear();
			tx.commit();
		}
		catch (Exception e) 
		{
			tx.rollback();
			e.printStackTrace();
		}
		finally 
		{
			em.close();
		}
		
		return memberIds;
	}
	
	public static Long generateRandomNumber(Long min, Long max)
	{
		Random random = new Random();
		return random.nextLong(max -min +1) +min; //바운드값 min을 더하는 이유는 id값이 0이라는 값이 없으므로
 	}
	
	public static Long generateRandomId(List<Long> ids)
	{
		Long minIdValue = Collections.min(ids);// teamids 엘리먼트중에 최소값을 가진 애를 찾아주는 static 메소드
		Long MaxIdValue = Collections.max(ids);
		return generateRandomNumber(minIdValue, MaxIdValue);
	}
	
}