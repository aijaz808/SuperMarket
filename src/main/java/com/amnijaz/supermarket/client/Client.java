package com.amnijaz.supermarket.client;

import com.amnijaz.supermarket.inventory.InventoryModel;

import java.io.FileNotFoundException;
import java.util.Map;

public interface Client {

        public void getInventoryItemList() throws FileNotFoundException;
}
