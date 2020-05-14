package com.miu.onlinemarket.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String name;

	@NotEmpty
	private String description;

	@NotNull
	private Double price;

	@NotNull
	private Long quantity;

	@Transient
	private MultipartFile image;

	@Transient
	private String photoBase64;

	@Lob
	@Column(name = "photo", columnDefinition = "BLOB")
	private byte[] photo;

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
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

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getPhotoBase64() {
		return photoBase64;
	}

	public void setPhotoBase64(String photoBase64) {
		this.photoBase64 = photoBase64;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public void addReview(Review newReview) {
		if (reviews == null) {
			reviews = new HashSet<>();
		}
		reviews.add(newReview);
	}

}
