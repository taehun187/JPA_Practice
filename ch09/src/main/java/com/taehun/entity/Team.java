package com.taehun.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Teams")
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="TEAM_ID")
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "team")
	private List<Member> memberList = new ArrayList<>();

	@Override
	public String toString() {
		return "Team [id=" + 
				id
				+ ", name=" +
				name
				+ 
				"member" +
				memberList.size()
				+
				"]";
	}
	
	public void addMember (Member mem)
	{
		mem.setTeam(this);
		memberList.add(mem);
	}
	
	public void removeMember(Member mem)
	{
		memberList.remove(mem);
		mem.setTeam(null);
	}
	
}