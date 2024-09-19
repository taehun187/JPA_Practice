package com.taehun.jpaexs.one2one.bidrectional;

import javax.persistence.*;


public class Main {
	public static void bcreateMemberAndLocker(EntityManagerFactory emf)
	{
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Traveler mem1 = new Traveler("kim");
		
		em.persist(mem1);
		Long memid = mem1.getId();
		PassPort locker = new PassPort("locker1");
		locker.addtravlerTest(mem1);
		mem1.setLock(locker);
		em.persist(locker);
		em.persist(mem1);
		em.flush();
		em.clear();
		
		Long passid = locker.getId();
		PassPort passPort2 = em.find(PassPort.class, passid);
		Traveler traveler2 = passPort2.getTravler();

		tx.commit();
		em.close();
	}
	

	public static void main(String[] args) {
		EntityManagerFactory emf = new Persistence().createEntityManagerFactory("jpabook4");
		bcreateMemberAndLocker(emf);
		emf.close();

	}

}