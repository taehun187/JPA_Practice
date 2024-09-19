package com.taehun.jpaexs.one2one.unidirectional;

import javax.persistence.*;

@Entity
public class Locker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOCKER_ID")
	private Long id;
	
	private String name;

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

	public Locker(String name) {
		super();
		this.name = name;
	}

	public Locker() {
	}
	
	
}