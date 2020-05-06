package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Item;

public interface ItemService {
    public Item save(Item item);
    public Item findItem(Long id);
    public void delete(Item item);

}
