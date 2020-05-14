package com.miu.onlinemarket.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	private Product product;

	private long quantity = 0;

	private Status status;

	private boolean reviewStatus = false;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "seller_id")
	private Seller seller;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "orders_id")
	private Order order;
	@OneToOne
	@JoinColumn(name = "buyer_id")
	Buyer buyer;

	@OneToOne
	Review review;

	public Item(Product product, long quantity, Status status, Seller seller, Order order, Buyer buyer) {
		this.product = product;
		this.quantity = quantity;
		this.status = status;
		this.seller = seller;
		this.order = order;
		this.buyer = buyer;
	}

	public Item() {
	}

	public boolean getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(boolean reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
}
