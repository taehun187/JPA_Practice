package com.taehun;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.*;

import com.taehun.entity.Address;
import com.taehun.entity.Member;
import com.taehun.utilities.JpaBooks;

public class Main {
	
	static final int Team_NUMBERS=10;
	static final int MEMBER_NUMBERS =10;
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook1");
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try
		{
			tx.begin();
			List<Long>  membersIds = JpaBooks.initMemberTeamSampleData(em, Team_NUMBERS, MEMBER_NUMBERS);
			
			Long memberid = insertFavoriteFood(em, membersIds);
			
			searchFavoriteFood(em, memberid);
			updateFavoriteFood(em, memberid);
			insertAddressAndAddressList(em, membersIds);
			tx.commit();
			
			
		}
		
		catch (Exception e) 
		{
			tx.rollback();
			e.printStackTrace();
	
			// TODO: handle exception
		}
		finally
		{
			
		}

	}
	
	public static Long insertFavoriteFood(EntityManager em, List<Long> memberIds)
	{
		Member member = em.find(Member.class, JpaBooks.generateRandomId(memberIds));
		
		member.getFavoriteFood().add("짬뽕");
		member.getFavoriteFood().add("떡볶이");
		member.getFavoriteFood().add("바닐라아이스라떼");
		em.flush();
		em.clear();
		
		return member.getId();
	}
	
	public static void searchFavoriteFood(EntityManager em,Long memberId)
	{
		System.out.println("================================================================searchFavoriteFood================================================================");
		Member mem = em.find(Member.class, memberId);
		for (String str : mem.getFavoriteFood())
		{
			System.out.println("멤버:" +  mem.getName()+ " id: "  + memberId + " Food : " + str);
		}
	}
	
	public static void updateFavoriteFood(EntityManager em,Long memberId)
	{
		System.out.println("================================================================updateFavoriteFood================================================================");
		Member mem = em.find(Member.class, memberId);
		mem.getFavoriteFood().remove("바닐라아이스라떼");
		mem.getFavoriteFood().add("아이스 아메리카노");
		
	}
	
	public static void insertAddressAndAddressList(EntityManager em, List<Long> memberIds)
	{
		Member member = em.find(Member.class, JpaBooks.generateRandomId(memberIds));
		
		member.setAddress(new Address("123 Main street","Daegu","12345"));
		member.getAddressList().add(new Address("456 Main street","Daegu","67890"));
		member.getAddressList().add(new Address("789 Main street","Daegu","54321"));
		
		em.flush();
		em.clear();
		
		Member foundMember = em.find(Member.class, member.getId());
		
		for (Address address : foundMember.getAddressList())
		{
			System.out.printf("Strret : %s , city :$s, zopCode %s \n" , address.getStreet(),address.getCity(), address.getZipcod());
		}
		
		
	}

}