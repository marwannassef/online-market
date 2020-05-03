package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
