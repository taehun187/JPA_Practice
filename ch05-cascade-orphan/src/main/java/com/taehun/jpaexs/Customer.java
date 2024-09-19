package com.taehun.jpaexs;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="customer_id")
	private Long id;
	
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

	private String name;

	// 종속관계 One:부모 , Many:자식
	@OneToMany(cascade=CascadeType.ALL, // ALL안하고 다른거면 하나하나 다해줘야함
				mappedBy="customer", orphanRemoval=true)
	private Set<Order> orders = new HashSet<>();
	
	public void addOrder(Order order) {
		order.setCustomer(this);
		orders.add(order);
	}
	
	public void removeOrder(Order order) {
		orders.remove(order);
		order.setCustomer(null);	
	}
	
}
