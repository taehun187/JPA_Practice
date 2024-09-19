package com.taehun.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {

            tx.begin(); //트랜잭션 시작
            Member member = new Member();
            member.setName("kin");
            em.persist(member);
            Long memid = member.getId();
            
            Member member2 = new Member();
            member2.setName("kin");
            em.persist(member2);
            Long memid2 = member2.getId();
            
            
            Product product = new Product();
            product.setName("hwanh");
            em.persist(product);
            Long productid = product.getId();
            
            Product product2 = new Product();
            product2.setName("bbbbb");
            em.persist(product2);
            Long productid2 = product2.getId();
            
            MemberProduct mempro = new MemberProduct();
            mempro.setMeber(member);
            mempro.setProduct(product);
            mempro.setOrderAmount(10);
            em.persist(mempro);
            
            MemberProduct mempro2 = new MemberProduct();
            mempro2.setMeber(member2);
            mempro2.setProduct(product);
            mempro2.setOrderAmount(10);
            em.persist(mempro2);
            
            MemberProduct mempro3 = new MemberProduct();
            mempro3.setMeber(member);
            mempro3.setProduct(product2);
            mempro3.setOrderAmount(10);
            em.persist(mempro3);
            
            MemberProduct mempro4 = new MemberProduct();
            mempro4.setMeber(member2);
            mempro4.setProduct(product2);
            mempro4.setOrderAmount(10);
            em.persist(mempro4);
            em.flush();
            em.clear();
            
            //////////////////////////////////////
            Member mem = em.find(Member.class,memid);
            
            List<MemberProduct> mempros = member.getMemberProducts();
            for(MemberProduct a : mempros)
            {
            	Product p = a.getProduct();
            	System.out.printf("상품 id %d, 상품 name %s", p.getId(), p.getName());
            }
            /////////////////////////////////////

            tx.commit();//트랜잭션 커밋


        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
            emf.close(); //엔티티 매니저 팩토리 종료
        }
    }

}