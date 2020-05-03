package com.miu.onlinemarket.domain;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private double totalPrice;

	@OneToMany
	@JoinColumn(name = "orders_id")
	private List<Item> items;

	public Order(double totalPrice, Item items) {
		this.totalPrice = totalPrice;
		this.items.add(items);
	}

	public Order() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
