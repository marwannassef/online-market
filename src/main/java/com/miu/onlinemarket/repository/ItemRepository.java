package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    public Item findItemById(Long id);
}
