package com.miu.onlinemarket.service.impl;

import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.repository.SellerRepository;
import com.miu.onlinemarket.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImp implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Seller findSeller(String username) {
        return sellerRepository.findByUsername(username);
    }

    @Override
    public User save(Seller seller) {
        seller.setPassword(encodePassword(seller.getPassword()));
        return sellerRepository.save(seller);
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
