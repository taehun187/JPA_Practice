package com.taehun.jpaexs;

import java.util.Date;

import javax.persistence.*;

@Entity
//@Table(name = "Member",
//uniqueConstraints = @UniqueConstraint(name = "mailePhone",columnNames = {"email", "poneNum"}))
@Table(name = "MEMBER")
@SequenceGenerator(
	    name = "MEMBER_SEQ_GENERATOR",
	    sequenceName = "MEMBER_SEQ",
	    initialValue = 1, allocationSize = 30)
//@TableGenerator(
//		  name = "MEMBER_SEQ_GENERATOR",
//		  table = "MY_SEQUENCES",
//		  pkColumnValue = "MEMBER_SEQ", allocationSize = 30)
public class Member {
	// 항상 Default Constructor가 필요함

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE
					,generator = "MEMBER_SEQ_GENERATOR")
	private Long id; // Integer(X), String도 권장하지 않음 : ID자동증가 X
	
//	@Column(unique = true)// 유니크 제약조건 = 하나의 테이블에 동일한 칼럼 값을 가질 수 없도록 제약
//						  //null 값은 중복으로 간주 하지 않는다
//	private String email;
//	@Column(unique = true) 
//	private String poneNum;
	
	
	@Column(name="name")
	private String username;
	
	private Integer age;
	
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;            
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;        
	
	
//	@PrePersist
//	
//	@PreUpdate
//	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	
}