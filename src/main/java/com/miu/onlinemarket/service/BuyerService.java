package com.miu.onlinemarket.service;

import java.util.List;

import com.miu.onlinemarket.domain.Address;
import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.PaymentMethod;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;

public interface BuyerService {

	public Buyer findByUsername(String username) throws ResourceNotFoundException;

	public Buyer save(Buyer buyer);

	public List<Buyer> listBuyer();

	public void deleteBuyer(Buyer buyer);

	public Buyer update(Buyer buyer) throws ResourceNotFoundException;

	public void updateUserOrder(Order order, String username) throws ResourceNotFoundException;

	public Buyer findBuyerById(Long userId) throws ResourceNotFoundException;

	public void updateAddress(Long userId, Address address) throws ResourceNotFoundException;

	public void updatePayment(Long userId, PaymentMethod paymentMethod) throws ResourceNotFoundException;

	Buyer findBuyerBySellerId(Long id);

	List<Buyer> findBuyersBySellerId(Long id);

}
