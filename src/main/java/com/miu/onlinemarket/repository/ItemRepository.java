package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface ItemRepository extends JpaRepository<Item, Long> {
    
    @Transactional
    @Modifying
    @Query("delete from Item i where i.id= :id")
    void deleteItem(Long id);

    public Item findItemById(Long id);

}
