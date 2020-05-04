package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.name like concat('%',:name,'%') OR p.description like concat('%',:name,'%')")
    List<Product> SearchByName(String name);

}
