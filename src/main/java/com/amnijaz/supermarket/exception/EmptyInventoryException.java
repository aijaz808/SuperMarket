package com.amnijaz.supermarket.exception;

public class EmptyInventoryException extends RuntimeException {
    public EmptyInventoryException(String nothing_present_on_inventory) {

        super("Empty Inventory Exception");
    }
}
