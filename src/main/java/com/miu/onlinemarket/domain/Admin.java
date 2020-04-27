package com.miu.onlinemarket.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Admin extends User {
    @OneToMany
    @JoinColumn(name="admin_id")
    List <Buyer> buyers = new ArrayList<>();
    @OneToMany
    @JoinColumn(name="admin_id")
    List <Seller> sellers = new ArrayList<>();

    public Admin(){}
    public Admin(Buyer buyer, Seller seller) {
        this.buyers.add(buyer);
        this.sellers.add(seller);
    }

    public void addSeller(Seller seller) {
        this.sellers.add(seller);
    }
    public void addBuyer(Buyer buyer) {
        this.buyers.add(buyer);
    }
}
