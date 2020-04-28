package com.miu.onlinemarket.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShippingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(mappedBy = "shippingCart")
    private Buyer buyer;
    @OneToOne
    @JoinColumn(name = "product_id")
    Product product;
    @OneToMany
    @JoinColumn(name = "shippingCart_id")
    List<Item> items = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Order order;

    public ShippingCart() {
    }
    public ShippingCart(Buyer buyer){
        this.buyer = buyer;
    }

}