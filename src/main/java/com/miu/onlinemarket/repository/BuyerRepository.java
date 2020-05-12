package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    @Query("select b from Buyer b join b.seller s where s.userId = :id ")
    Buyer findFirstByBuyerBySellerId(Long id);

    Buyer findBuyerByUsername(String username);

    @Query("select b from Buyer b join b.seller s where s.userId = :id ")
     List<Buyer> findBuyersBySellerId(Long id);

}
