package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    Buyer findBuyerByUsername(String username);
}
