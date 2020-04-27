package com.miu.onlinemarket.domain;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String description;
    Long price;
    Long quantity;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    Seller seller;
    @OneToOne(mappedBy = "product")
    ShippingCart shippingCart;
    public Product(){}
    public Product(String description, Long price, Long quantity, Seller seller,ShippingCart shippingCart) {
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.seller = seller;
        this.shippingCart = shippingCart;
    }

    public ShippingCart getShippingCart() {
        return shippingCart;
    }

    public void setShippingCart(ShippingCart shippingCart) {
        this.shippingCart = shippingCart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
