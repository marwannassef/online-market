package com.miu.onlinemarket.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Buyer extends User {

    private int points;
	
    @OneToOne(cascade = {CascadeType.ALL})
	private Address address;

	@OneToOne(cascade = {CascadeType.ALL})
	private PaymentMethod paymentMethod;

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "buyer_id")
	Set<Order> orders;

	@ManyToMany
	@JoinTable(name = "buyer_seller")
	List<Seller> seller;

	public Buyer() {
	}

	public Buyer(User user) {
		super(user);
	}

	public Buyer(User user, Address address, PaymentMethod paymentMethod, Set<Order> orders) {
		super(user);
		this.address = address;
		this.paymentMethod = paymentMethod;
		this.orders = orders;
	}

	public List<Seller> getSeller() {
		return seller;
	}

	public void setSeller(List<Seller> seller) {
		this.seller = seller;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public void addSeller(Seller newSeller){
		if(seller == null){
			seller = new ArrayList<>();
		}
		seller.add(newSeller);
	}


	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
}
