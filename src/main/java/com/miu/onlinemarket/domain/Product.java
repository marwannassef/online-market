package com.miu.onlinemarket.domain;

import java.util.List;

import javax.persistence.*;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String description;

	private long price;

	private long quantity;

	@ManyToOne
	@JoinColumn(name = "seller_id")
	Seller seller;

	@OneToMany
	@JoinColumn(name = "product_id")
	List<Review> reviews;

	public Product() {
	}

	public Product(String name, String description, long price, long quantity, Seller seller, List<Review> reviews) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.seller = seller;
		this.reviews = reviews;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

}
