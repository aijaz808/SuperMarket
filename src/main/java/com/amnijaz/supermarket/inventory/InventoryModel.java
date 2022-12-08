package com.amnijaz.supermarket.inventory;

public class InventoryModel {

    private Double amount;
    private Long quantity;


    public InventoryModel() {
    }

    public InventoryModel(Double amount, Long quantity) {
        this.amount = amount;
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
