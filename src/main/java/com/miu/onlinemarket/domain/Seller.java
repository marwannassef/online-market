package com.miu.onlinemarket.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Seller extends User {

	private Boolean approved = false;

	@OneToMany(mappedBy = "seller",fetch = FetchType.EAGER)
	List<Product> products;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name ="seller_id")
	Set<Item> items;

	@ManyToMany(mappedBy = "seller",fetch = FetchType.EAGER)
	List<Buyer> buyers;

	public Seller() {
	}

	public Seller(User user) {
		super(user);
	}

	public Seller(User user, Boolean approved, List<Product> products, Set<Item> items) {
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

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public List<Buyer> getBuyers() {
		return buyers;
	}

	public void setBuyers(List<Buyer> buyers) {
		this.buyers = buyers;
	}
}
