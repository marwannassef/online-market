package com.miu.onlinemarket.service.impl;

import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.miu.onlinemarket.domain.Address;
import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Item;
import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.PaymentMethod;
import com.miu.onlinemarket.domain.Role;
import com.miu.onlinemarket.domain.Status;
import com.miu.onlinemarket.repository.BuyerRepository;
import com.miu.onlinemarket.repository.RoleRepository;
import com.miu.onlinemarket.service.BuyerService;

@Service
public class BuyerServiceImp implements BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

	@Autowired
	private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public Buyer save(Buyer buyer) {
        buyer.setPassword(encodePassword(buyer.getPassword()));
        Set<Role> roles = new HashSet<>();
		roles.add(roleRepo.findByName("ROLE_BUYER"));
		buyer.setRoles(roles);
		Order order = new Order(0, Status.PREPARED, new HashSet<Item>());
		Set<Order> orders = new HashSet<Order>();
		orders.add(order);
		buyer.setOrders(orders);
        return buyerRepository.save(buyer);
    }


    @Override
    public Buyer findByUsername(String username) {
    	Buyer buyer = buyerRepository.findBuyerByUsername(username);
    	if (buyer.getPhoto() != null && buyer.getPhoto().length != 0)
    		buyer.setPhotoBase64(Base64.getEncoder().encodeToString(buyer.getPhoto()));
        return buyer;
    }


    @Override
    public List<Buyer> listBuyer() { return buyerRepository.findAll(); }

    @Override
    public void deleteBuyer(Buyer buyer) {
        buyerRepository.delete(buyer);
    }

    @Override
    public Buyer update(Buyer buyer) {
		Buyer oldBuyer = buyerRepository.findBuyerByUsername(buyer.getUsername());
		buyer.setRoles(oldBuyer.getRoles());
		buyer.setPaymentMethod(oldBuyer.getPaymentMethod());
		buyer.setShippingAddress(oldBuyer.getShippingAddress());
		buyer.setOrders(oldBuyer.getOrders());
        return buyerRepository.save(buyer);
    }



    @Override
    public Buyer findBuyerById(Long userId) {
        return buyerRepository.findById(userId).get();
    }

    @Override
    public void updateAddress(Long userId, Address address) {
        Buyer buyer = buyerRepository.findById(userId).get();
        buyer.setShippingAddress(address);
        buyerRepository.save(buyer);
    }

    @Override
    public void updatePayment(Long userId, PaymentMethod paymentMethod) {
        Buyer buyer = buyerRepository.findById(userId).get();
        buyer.setPaymentMethod(paymentMethod);
        buyerRepository.save(buyer);
    }
}
