package com.miu.onlinemarket.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Seller extends User {

	private Boolean approved = false;

	@OneToMany(mappedBy = "seller")
	List<Product> products;

	@OneToMany
	@JoinColumn(name ="seller_id")
	List<Item> items;

	public Seller() {
	}

	public Seller(User user, Boolean approved, List<Product> products, List<Item> items) {
		super(user);
		this.approved = approved;
		this.products = products;
		this.items = items;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
