package com.miu.onlinemarket.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String review;

	private boolean reviewStatus = false;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;

	public Review(String review, boolean reviewStatus, Buyer buyer) {
		this.review = review;
		this.reviewStatus = reviewStatus;
		this.buyer = buyer;
	}

	public Review() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
