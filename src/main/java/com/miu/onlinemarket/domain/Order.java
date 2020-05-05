package com.miu.onlinemarket.domain;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double totalPrice = 0.0;

	@OneToMany
	@JoinColumn(name = "orders_id")
	private List<Item> items;

	public Order(Double totalPrice, Item items) {
		this.totalPrice = totalPrice;
		this.items.add(items);
	}

	public Order() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
