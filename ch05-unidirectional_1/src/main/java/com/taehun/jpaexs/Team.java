package com.taehun.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Team {
	public Team() {}
	public Team(String name) 
	{
		this.name = name;
	}
	
	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Team_ID") 
	private Long TeamId;
	
	private String name;
	
	// 단독으로 못써서 @ElementCollectionfmf 써야하지만 다른방법으로 해결가능
	
	@OneToMany(mappedBy = "team") //엔티티 클래스간의 양방향 얘기할때 어려운 mappedBy 
	List<Member> members = new ArrayList<>();
	
	public void addMember(Member m)
	{
		m.setTeam(this);
		members.add(m);
	}
	
	public Long getTeamId() {
		return TeamId;
	}

	public void setTeamId(Long teamId) {
		TeamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "Team [TeamId=" + 
				TeamId
				+ ", name=" + 
				name
				+ ", members=" + 
				members.size()
				+ "]";
	}
	
	
}