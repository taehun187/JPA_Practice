package com.taehun.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try
		{
			tx.begin();
			Album album = new Album();
			album.setName("haris");
			album.setPrice(15000);
			album.setArtist("kim");
			em.persist(album);
			
			Book book = new Book();
			book.setName("haris");
			book.setPrice(15000);
			book.setAuthor("kim");
			book.setIsbn("12345");
			em.persist(book);
			
			em.flush();
			em.clear();
			
//			Book gotBook = em.find(Book.class, book.getId());
			Item item = em.find(Item.class, book.getId());
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