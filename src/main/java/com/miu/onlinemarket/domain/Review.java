package com.miu.onlinemarket.domain;

import javax.persistence.*;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String review;

	private boolean reviewStatus;

	@OneToOne
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;

	public Review(String review, boolean reviewStatus, Buyer buyer) {
		this.review = review;
		this.reviewStatus = reviewStatus;
		this.buyer = buyer;
	}

	public Review() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public boolean isReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(boolean reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

}
