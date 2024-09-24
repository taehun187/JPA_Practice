package com.taehun.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

import com.taehun.entity.Comment;
import com.taehun.entity.Member;
import com.taehun.entity.Post;
import com.taehun.entity.Team;
import com.taehun.entity.Address;




public class JpaBooks {

	static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	static final Long minAge = 5L;
	static final Long maxAge = 50L;

	static final Address[] addresses = {
			new Address("012 Eldo St", "Incheon", "12345"),
			new Address("345 Elm St", "Gwangju", "67890"),
			new Address("678 Elm St", "Daegu", "12345"),
			new Address("901 Elm St", "Busan", "67890"),
			new Address("234 Elm St", "Seoul", "12345"),
			new Address("567 Elm St", "Daegu", "67890"),
			new Address("890 Elm St", "Ulsan", "12345"),
			new Address("123 Elm St", "Suwon", "67890"),
			new Address("456 Elm St", "Daegu", "12345"),
			new Address("789 Elm St", "Andong", "67890")
	};

	public static List<Long> initMemberTeamSampleData(EntityManagerFactory emf, int teamNumbers, int memberNumbers) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<Long> teamIds = new ArrayList<>();
		List<Long> memberIds = new ArrayList<>();

		try {
			// 트랜잭션 시작
			tx.begin();

			// 팀 데이터 생성
			for (int i = 0; i < teamNumbers; i++) {
				Team team = new Team();
				team.setName("team:" + i);
				em.persist(team);
				teamIds.add(team.getId());
			}

			Long minIdValue = Collections.min(teamIds);
			Long maxIdValue = Collections.max(teamIds);

			// 멤버 데이터 생성
			for (int i = 0; i < memberNumbers; i++) {
				Member member = new Member();
				member.setName("kim:" + i);

				Long lAge = generateRandomNumber(minAge, maxAge);
				Integer randomAge = lAge.intValue(); // 명시적 변환
				member.setAge(randomAge);
				Address address = JpaBooks.addresses[generateRandomNumber(0L, 9L).intValue()];
				member.setAddress(new Address(address.getStreet(), address.getCity(), address.getZipCode()));

				Long targetTeamId = generateRandomNumber(minIdValue, maxIdValue);
				Team team = em.find(Team.class, targetTeamId);
				team.addMember(member);
				em.persist(member);
				memberIds.add(member.getId());
			}

			// 트랜잭션 커밋
			tx.commit();
		} catch (Exception e) {
			// 오류 발생 시 롤백
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			// EntityManager 종료
			em.close();
		}

		return memberIds;
	}

	public static Long generateRandomNumber(Long min, Long max) {
		Random random = new Random();
		return random.nextLong(max - min + 1) + min;
	}

	public static Long generateRandomID(List<Long> ids) {
		Random random = new Random();
		int randomIndex = random.nextInt(ids.size());
		return ids.get(randomIndex); // 무작위로 ID 선택
	}

	public static List<Long> initPostCommentSampleData(EntityManagerFactory emf, int postNumbers, Long postStringMaxSize, int commentNumbers, Long commentStringMaxSize) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		List<Long> ids = new ArrayList<>();

		try {
			tx.begin();
			for (int i = 0; i < postNumbers; i++) {
				Post post = new Post();
				post.setTitle("New Post" + i);
				post.setText(generateRandomString(postStringMaxSize));

				for (int j = 0; j < commentNumbers; j++) {
					Comment comment = new Comment();
					comment.setText(generateRandomString(commentStringMaxSize));
					post.addComment(comment);
				}
				ids.add(post.getId());
				em.persist(post);
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return ids;
	}

	public static String generateRandomString(Long max) {
		Long length = generateRandomNumber(1L, max);
		StringBuilder randomStringBuilder = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			randomStringBuilder.append(characters.charAt(index));
		}
		return randomStringBuilder.toString();
	}
}