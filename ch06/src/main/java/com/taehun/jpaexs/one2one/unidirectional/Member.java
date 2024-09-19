package com.taehun.jpaexs.one2one.unidirectional;

import javax.persistence.*;

@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;
	
	private String username;
	
	@OneToOne
	@JoinColumn(name = "LOCKER_ID", unique = true)
	private Locker lock;

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

	public Locker getLock() {
		return lock;
	}

	public void setLock(Locker lock) {
		this.lock = lock;
	}
	
	public Member() {
		super();
	}

	public Member(String username) {
		super();
		this.username = username;
	}


	
	
}