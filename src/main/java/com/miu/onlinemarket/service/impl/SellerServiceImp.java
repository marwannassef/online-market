package com.miu.onlinemarket.service.impl;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.Role;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.repository.RoleRepository;
import com.miu.onlinemarket.repository.SellerRepository;
import com.miu.onlinemarket.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return sellerRepository.findByUsername(username);
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

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
