package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("select p from Product p where p.seller.userId = :id AND (p.name like concat('%',:name,'%') OR p.description like concat('%',:name,'%'))")
    List<Product> SearchByName(String name , Long id);
    Seller findByUsername(String username);
}
