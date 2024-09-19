package com.taehun.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="orders")
public class Order {
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_id")
	private Long id;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@OneToMany(mappedBy="order", orphanRemoval=true, cascade=CascadeType.ALL)
	private List<LineItem> lineItems = 
			new ArrayList<>();
	
	public void addLineItem(LineItem lineItem) {
		lineItem.setOrder(this);
		lineItems.add(lineItem);
	}
	
	public void removeLineItem(LineItem lineItem) {
		lineItems.remove(lineItem);
		lineItem.setOrder(null);
	}
	
}
