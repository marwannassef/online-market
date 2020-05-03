package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}
