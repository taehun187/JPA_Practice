package com.taehun.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


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
	
	@OneToMany(fetch = FetchType.LAZY) /* (targetEntity = Member.class) */
	@JoinColumn(name = "Team_ID")
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