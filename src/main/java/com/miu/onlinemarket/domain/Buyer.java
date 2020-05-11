package com.miu.onlinemarket.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Buyer extends User {

	@OneToOne
	private Address shippingAddress;

	@OneToOne
	private PaymentMethod paymentMethod;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "buyer_id")
	Set<Order> orders;

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

}
