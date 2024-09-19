package com.taehun.jpaexs;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

public class Main {
	
	public static Map<EntityClassStyle, Long> setTestTables(EntityManagerFactory emf) {
		Map<EntityClassStyle, Long> maps = new HashMap<>();
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
			
			Customer customer = new Customer();
			customer.setName("kim");
			
			Order order = new Order();
			order.setDescription("Order 1");
			customer.addOrder(order);
			
			LineItem item1 = new LineItem();
			item1.setProductName("Item 1");
			item1.setQuantity(2);
			order.addLineItem(item1);
			
			em.persist(customer);
//			em.persist(order);
//			em.persist(item1);
//			
			tx.commit();
			
			System.out.println("order is Persist: " + em.contains(order));
			System.out.println("item1 is Persist: " + em.contains(item1));			
			
			maps.put(EntityClassStyle.CUSTOMER, customer.getId());
			maps.put(EntityClassStyle.ORDER, order.getId());
			maps.put(EntityClassStyle.LINEITEM, item1.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		
		return maps;		
	}
	
	public static void occurenceOrphanEntity(EntityManagerFactory emf,
			Map<EntityClassStyle, Long> maps) throws InterruptedException {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Customer customer = null;
		Order order = null;
		LineItem item1 = null;
		
		try {
			tx.begin();
			
			customer = em.find(Customer.class, maps.get(EntityClassStyle.CUSTOMER));
			order = em.find(Order.class, maps.get(EntityClassStyle.ORDER));
			item1 = em.find(LineItem.class, maps.get(EntityClassStyle.LINEITEM));
			
			customer.removeOrder(order); // order는 orphan entity class object...
			// cascade=CascadeType.REMOVE로 설정이 된 경우,,,
			// 편의 removeOrder 메서드가 영향을 미치지 못함...그래서 직접 remove를 해 준다.
//			em.remove(order);
			em.flush();			

			if (em.contains(customer)) {
				System.out.println("customer는 영속 상태");
			} else {
				System.out.println("customer는 비영속 상태");
			}
			
			if (em.contains(order)) {
				System.out.println("order는 영속 상태");
			} else {
				System.out.println("order는 비영속 상태");
			}
			
			if (em.contains(item1)) {
				System.out.println("item1는 영속 상태");
			} else {
				System.out.println("item1는 비영속 상태");
			}	
			/////////////////////////////////////////////
			order.removeLineItem(item1);
			/////////////////////////////////////////////
			
			tx.commit();
			
		} catch(Exception e) {
			e.printStackTrace();	
            tx.rollback();		
		} finally {
			em.close();
		}
		
		//////////////////////////////////
		Thread.sleep(1000);
		///////////////////////////////////
		
		em = emf.createEntityManager();
		tx = em.getTransaction();
		
		tx.begin();
		try {
			Customer nCustomer = null;
			if (em.contains(customer)) {
				System.out.println("customer는 영속 상태");
			} else {
				System.out.println("customer는 비영속 상태");
				nCustomer = em.merge(customer);
			}
			
			Order nOrder = null;
			if (em.contains(order)) {
				System.out.println("order는 영속 상태");
			} else {
				System.out.println("order는 비영속 상태");
				nOrder = em.merge(order);
			}
			
			LineItem nItem = null;
			if (em.contains(item1)) {
				System.out.println("item1는 영속 상태");
			} else {
				System.out.println("item1는 비영속 상태");
				nItem = em.merge(item1);  ///////////////////////////////////////////
			}		
			
			nCustomer.addOrder(nOrder);
			nOrder.addLineItem(nItem);
			
			em.flush();			
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		
		
	}
	
	
	public static void main(String ...args) throws InterruptedException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		
		Map<EntityClassStyle, Long> maps = setTestTables(emf);
		occurenceOrphanEntity(emf, maps);
	}

}