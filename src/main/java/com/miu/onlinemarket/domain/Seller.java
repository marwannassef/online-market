package com.miu.onlinemarket.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Seller extends User {
    private Boolean approve_sale = false;
    @OneToMany(mappedBy = "seller")
    List<Product> products = new ArrayList<Product>();

    public Seller() {}
    public Seller(User user, Boolean approve_sale, List<Product> products) {
        super(user);
        this.approve_sale = approve_sale;
        this.products = products;
    }

    public Boolean getApprove_sale() {
        return approve_sale;
    }

    public void setApprove_sale(Boolean approve_selling) {
        this.approve_sale = approve_selling;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public void addProduct( Product product) {
        this.products.add(product);
    }
}
