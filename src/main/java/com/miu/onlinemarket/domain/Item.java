package com.miu.onlinemarket.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long quantity;
    private String review;
    boolean display_review = false;
    private boolean shipping_status = false;

    public Item (){}
    public Item(long quantity, String review, boolean display_review, boolean shipping_status) {
        this.quantity = quantity;
        this.review = review;
        this.display_review = display_review;
        this.shipping_status = shipping_status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isDisplay_review() {
        return display_review;
    }

    public void setDisplay_review(boolean display_review) {
        this.display_review = display_review;
    }

    public boolean isShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(boolean shipping_status) {
        this.shipping_status = shipping_status;
    }
}
