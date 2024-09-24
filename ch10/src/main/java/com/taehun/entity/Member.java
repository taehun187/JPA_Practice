package com.taehun.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MEMBER_ID")
	private Long id;

	private String name;

	private Integer age;

	@Embedded
	private Address address;

	@ElementCollection
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AddressEntity> addressList = new ArrayList<>(); // 수정: AddressEntity를 참조하게 변경


	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<FavoriteFood> favoriteFoods = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", age= " + age + "]";
	}
}