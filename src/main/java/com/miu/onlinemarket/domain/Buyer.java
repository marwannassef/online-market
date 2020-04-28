package com.miu.onlinemarket.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Buyer extends User {
    private String Billing_address;
    private String shipping_address;
    private long credit_card;// some issue to create hashMap
    @OneToOne
    @JoinColumn(name="shippingCart_id")
    ShippingCart shippingCart;
    public Buyer(){}

    public Buyer(User user, String billing_address, String shipping_address, long credit_card, ShippingCart shippingCard) {
        super(user);
        Billing_address = billing_address;
        this.shipping_address = shipping_address;
        this.credit_card = credit_card;
        this.shippingCart = shippingCard;
    }

    public String getBilling_address() {
        return Billing_address;
    }

    public void setBilling_address(String billing_address) {
        Billing_address = billing_address;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public void setCredit_card(long value) {
        this.credit_card = value;
    }
    public void addMoney( long value ) {
        this.credit_card = this.credit_card + value;
    }
    public void makePayment(Integer value) {
        this.credit_card = this.credit_card - value;
    }
    public ShippingCart getShippingCart() {
        return shippingCart;
    }

    public void setShippingCart(ShippingCart shippingCart) {
        this.shippingCart = shippingCart;
    }
}
