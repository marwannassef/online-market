package com.miu.onlinemarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miu.onlinemarket.domain.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

	@Query("select b from Buyer b join b.seller s where s.userId = :id ")
	Buyer findFirstByBuyerBySellerId(Long id);

	Buyer findBuyerByUsername(String username);

	@Query("select b from Buyer b join b.seller s where s.userId = :id ")
	List<Buyer> findBuyersBySellerId(Long id);

}
