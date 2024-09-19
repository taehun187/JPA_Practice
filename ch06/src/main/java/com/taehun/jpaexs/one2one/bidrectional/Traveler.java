package com.taehun.jpaexs.one2one.bidrectional;

import javax.persistence.*;

@Entity
public class Traveler {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRAVELER_ID")
	private Long id;
	
	private String username;
	
	@OneToOne
	@JoinColumn(name = "PASSPORT_ID", unique = true)
	private PassPort passport;


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

	public PassPort getLock() {
		return passport;
	}

	public void setLock(PassPort lock) {
		this.passport = lock;
	}
	
	public Traveler() {
		super();
	}

	public Traveler(String username) {
		super();
		this.username = username;
	}


	
	
}