package com.miu.onlinemarket.service.impl;

import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.Role;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
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
    public Seller findSeller(String username) throws ResourceNotFoundException {
    	Seller seller = sellerRepository.findByUsername(username);
    	if(seller == null){
    	    throw new ResourceNotFoundException("Seller with username " +username+" is not found");
        }
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
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepo.findByName("ROLE_SELLER"));
		seller.setRoles(roles);
        return sellerRepository.save(seller);

    }

    @Override
    public Seller update(Seller seller) throws ResourceNotFoundException {
    	Seller oldSeller = sellerRepository.findByUsername(seller.getUsername());
        if(seller == null){
            throw new ResourceNotFoundException("Seller with username " +seller.getUsername()+" is not found");
        }
    	seller.setRoles(oldSeller.getRoles());
    	seller.setApproved(oldSeller.getApproved());
    	seller.setProducts(oldSeller.getProducts());

    	seller.setItems(oldSeller.getItems());
        return sellerRepository.save(seller);
    }

    @Override
    public List<Seller> findUnApprovedSeller() {
        return sellerRepository.findUnApproved();
    }

    @Override
    public Seller findSellerById(Long id) throws ResourceNotFoundException {
        return sellerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Seller with id " + id +" not found")
        );
    }

    @Override
    public List<Seller> findSellersByBuyerId(Long id) {
        return sellerRepository.findSellersByBuyerId(id);
    }


    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
