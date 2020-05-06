package com.miu.onlinemarket.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private long totalPrice = 0;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "orders_id")
	private List<Item> items;

	public Order(long totalPrice, Item items) {
		this.totalPrice = totalPrice;
		this.items.add(items);
	}

	public Order() {
		items = new ArrayList<>();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
