package com.miu.onlinemarket.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @OneToMany
    @JoinColumn(name ="order_id")
    List<Item> items = new ArrayList<>();

    public Order(){};
    public Order(Item items) {
        this.items.add(items);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
