package com.miu.onlinemarket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miu.onlinemarket.domain.Item;
import com.miu.onlinemarket.repository.ItemRepository;
import com.miu.onlinemarket.service.ItemService;


@Service
public class ItemServiceImp implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item findItem(Long id) {
       return itemRepository.findItemById(id);
    }



    @Override
    public void delete(Long id) {
        itemRepository.deleteItem(id);
    }
}
