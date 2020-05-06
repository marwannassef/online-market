package com.miu.onlinemarket.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.Role;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.repository.RoleRepository;
import com.miu.onlinemarket.repository.SellerRepository;
import com.miu.onlinemarket.service.SellerService;

@Service
public class SellerServiceImp implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RoleRepository roleRepo;

    @Override
    public Seller findSeller(String username) {
    	Seller seller = sellerRepository.findByUsername(username);
    	if (seller.getPhoto() != null && seller.getPhoto().length != 0)
    		seller.setPhotoBase64(Base64.getEncoder().encodeToString(seller.getPhoto()));
        return seller;
    }

    @Override
    public List<Product> searchByName(String name, long id) {
        return sellerRepository.SearchByName(name,id);
    }

    @Override
    public User save(Seller seller) {
        seller.setPassword(encodePassword(seller.getPassword()));
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findByName("ROLE_SELLER"));
		seller.setRoles(roles);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller update(Seller seller) {
    	Seller oldSeller = sellerRepository.findByUsername(seller.getUsername());
    	seller.setRoles(oldSeller.getRoles());
    	seller.setApproved(oldSeller.getApproved());
    	seller.setProducts(oldSeller.getProducts());
    	seller.setItems(oldSeller.getItems());
        return sellerRepository.save(seller);
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
