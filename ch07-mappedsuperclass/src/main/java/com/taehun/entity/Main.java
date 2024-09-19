package com.taehun.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String... str)
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		
		try
		{	tx.begin();
		
			Member mem = new Member();
			mem.setName("Lee");
			em.persist(mem);
			
			em.flush();
			em.clear();
			
			Member foundmem = em.find(Member.class, mem.getId());
			
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
			emf.close();
		}
	}
}
