package com.miu.onlinemarket.domain;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Product name required")
	private String name;

	@NotEmpty(message = "Product description required")
	private String description;

	@Min(value = 0, message = "Price must be greater than 0")
	private double price = 0;

	@Min(value = 0, message = "Quantity must be greater than 0")
	private long quantity = 0;

	private boolean purchasedStatus = false;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "seller_id")
	Seller seller;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	Set<Review> reviews;

	public Product() {
	}

	public Product(String name, String description, double price, long quantity, Seller seller, Set<Review> reviews) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.seller = seller;
		this.reviews = reviews;
	}

	public boolean isPurchasedStatus() {
		return purchasedStatus;
	}

	public void setPurchasedStatus(boolean purchasedSatatus) {
		this.purchasedStatus = purchasedSatatus;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
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

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

}
