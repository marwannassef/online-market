package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    @Query("select b from Buyer b join b.seller s where s.userId = :id ")
    Buyer findSeller(Long id);
    Buyer findBuyerByUsername(String username);

}
