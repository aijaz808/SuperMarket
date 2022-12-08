package com.amnijaz.supermarket.inventory;

import java.io.FileNotFoundException;
import java.util.Map;

public interface Inventory {

    public Map<String, InventoryModel> getAmountAndQuantity(String file) throws FileNotFoundException;

}
