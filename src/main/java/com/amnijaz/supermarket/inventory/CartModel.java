package com.amnijaz.supermarket.inventory;

import com.amnijaz.supermarket.offer.OfferModel;

import java.util.Map;

public class CartModel {

    private Long quantity;
    private String offer;
    private Long offerCount;

    private Long startingPoint;



    public CartModel() {
    }

    public CartModel(Long quantity, String offer, Long offerCount, Long startingPoint) {
        this.quantity = quantity;
        this.offer = offer;
        this.offerCount = offerCount;
        this.startingPoint = startingPoint;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public Long getOfferCount() {
        return offerCount;
    }

    public void setOfferCount(Long offerCount) {
        this.offerCount = offerCount;
    }

    public Long getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Long startingPoint) {
        this.startingPoint = startingPoint;
    }
}
