package com.miu.onlinemarket.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Buyer extends User {

	@OneToOne
	private Address shippingAddress;

	@OneToOne
	private PaymentMethod paymentMethod;

	@OneToMany
	@JoinColumn(name = "order_id")
	List<Order> orders;

	public Buyer(User user, Address shippingAddress, PaymentMethod paymentMethod, long credit_card,
			List<Order> orders) {
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

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
