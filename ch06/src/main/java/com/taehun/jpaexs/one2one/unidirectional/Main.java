package com.taehun.jpaexs.one2one.unidirectional;

import javax.persistence.*;

public class Main {
	public static void createMemberAndLocker(EntityManagerFactory emf)
	{
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Member mem1 = new Member("kim");
		em.persist(mem1);
	
		Long memid = mem1.getId();
		Locker locker = new Locker("locker1");
		em.persist(locker);
		mem1.setLock(locker);
		
		em.flush();
		em.clear();
		Member member = em.find(Member.class, memid);
		Locker locker1 = member.getLock();
		tx.commit();
		em.close();
	}
	

	public static void main(String[] args) {
		EntityManagerFactory emf = new Persistence().createEntityManagerFactory("jpabook5");
		createMemberAndLocker(emf);
		emf.close();
	}

}