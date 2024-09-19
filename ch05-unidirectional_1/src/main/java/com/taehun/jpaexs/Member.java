package com.taehun.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Member {
	public Member() {}
	public Member(String name)
	{
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;
	
	private String name;
	
	@ManyToOne  //(fetch = FetchType.LAZY)  // 디폴트로 즉시가 되어있으나 테스트로 Lazy로 바꿈
	@JoinColumn(name = "TEAM_ID")
	private Team team;

//	@ElementCollection // 테이블 하나를 따로 만둘어서 strs 라는 테이블 을 만들어서 정규화 문제를 해결할려고함
//	List<String> strs = new ArrayList<>(); //엔티티 클래스의 필드로 컬렉션을 추가할 경우 발생하는 문제점? : 정규화 문제
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	
	@Override
	public String toString() {
		return "Member [id=" + 
				id 
				+ ", name=" + 
				name 
				+ ", team=" + 
				team
				+ "]";
	}
	
}