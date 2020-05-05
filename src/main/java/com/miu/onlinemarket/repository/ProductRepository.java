package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where UPPER(p.name)  like UPPER(concat('%',:name,'%') )OR UPPER(p.description) like UPPER(concat('%',:name,'%'))")
    List<Product> SearchByName(String name);

}
