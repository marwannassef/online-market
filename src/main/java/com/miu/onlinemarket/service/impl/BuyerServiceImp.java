package com.miu.onlinemarket.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.miu.onlinemarket.domain.Address;
import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.PaymentMethod;
import com.miu.onlinemarket.domain.Role;
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
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findByName("ROLE_BUYER"));
		buyer.setRoles(roles);
        return buyerRepository.save(buyer);
    }


    @Override
    public Buyer findByUsername(String username) throws ResourceNotFoundException {
    	Buyer buyer = buyerRepository.findBuyerByUsername(username);
    	if(buyer == null) {
            throw new ResourceNotFoundException("Buyer with username " + username + " is not found");
        }
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
    public Buyer update(Buyer buyer) throws ResourceNotFoundException {
		Buyer oldBuyer = buyerRepository.findBuyerByUsername(buyer.getUsername());
        if(buyer == null) {
            throw new ResourceNotFoundException("Buyer with username " + buyer.getUsername() + " is not found");
        }
		buyer.setRoles(oldBuyer.getRoles());
		buyer.setPaymentMethod(oldBuyer.getPaymentMethod());
		buyer.setShippingAddress(oldBuyer.getShippingAddress());
		buyer.setOrders(oldBuyer.getOrders());
        return buyerRepository.save(buyer);
    }



    @Override
    public Buyer findBuyerById(Long userId) throws ResourceNotFoundException {
        return buyerRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Seller with id " + userId +" not found")
        );
    }

    @Override
    public void updateAddress(Long userId, Address address) throws ResourceNotFoundException {
        Buyer buyer = buyerRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Seller with id " + userId +" not found")
        );
        buyer.setShippingAddress(address);
        buyerRepository.save(buyer);
    }

    @Override
    public void updatePayment(Long userId, PaymentMethod paymentMethod) throws ResourceNotFoundException {
        Buyer buyer = buyerRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("Seller with id " + userId +" not found")
        );
        buyer.setPaymentMethod(paymentMethod);
        buyerRepository.save(buyer);
    }
}
