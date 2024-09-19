package com.taehun.jpaexs.one2one.bidrectional;

import javax.persistence.*;

@Entity
public class PassPort {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PASSPORT_ID")
	private Long id;
	
	private String name;

	@OneToOne(mappedBy = "passport")
	private Traveler travler;
	
	public void addtravlerTest(Traveler travler)
	{
		travler.setLock(this);
		this.travler = travler;
	}
	
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

	public PassPort(String name) {
		super();
		this.name = name;
	}

	public PassPort() {
	}

	public Traveler getTravler() {
		return travler;
	}

	public void setTravler(Traveler travler) {
		this.travler = travler;
	}
	
	
}