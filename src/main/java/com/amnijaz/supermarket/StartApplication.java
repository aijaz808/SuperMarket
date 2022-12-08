/**
 * Starting point of Cart System
 */
package com.amnijaz.supermarket;

import com.amnijaz.supermarket.client.Client;
import com.amnijaz.supermarket.client.ClientImpl;

import java.io.FileNotFoundException;

public class StartApplication {

    static Client client=new ClientImpl();
    public static void main(String[] args){
        try {
            client.getInventoryItemList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
