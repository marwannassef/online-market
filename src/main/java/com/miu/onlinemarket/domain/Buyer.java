package com.miu.onlinemarket.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Buyer extends User {

	@OneToOne
	private Address shippingAddress;

	@OneToOne
	private PaymentMethod paymentMethod;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
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

	public Buyer(User user, Address shippingAddress, PaymentMethod paymentMethod, Set<Order> orders) {
		super(user);
		this.shippingAddress = shippingAddress;
		this.paymentMethod = paymentMethod;
		this.orders = orders;
	}

	public List<Seller> getSeller() {
		return seller;
	}

	public void setSeller(List<Seller> seller) {
		this.seller = seller;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
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
}
