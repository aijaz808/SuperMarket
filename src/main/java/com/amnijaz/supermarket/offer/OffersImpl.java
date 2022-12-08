package com.amnijaz.supermarket.offer;

import com.amnijaz.supermarket.client.ClientImpl;

public class OffersImpl implements Offers{

    @Override
    public void buyTwoGetOneFree(String item, Long count) {

        Double amount = ClientImpl.INVENTORY_MODEL_MAP.get(item).getAmount();
        Double discount = ClientImpl.DISCOUNT + (amount *count);
        ClientImpl.DISCOUNT = discount;
    }

    @Override
    public void buyOneGetHalfOff(String item, Long count) {

        Double amount = ClientImpl.INVENTORY_MODEL_MAP.get(item).getAmount();
        Double discount = ClientImpl.DISCOUNT + (count * (amount/2.00));
        ClientImpl.DISCOUNT = discount;
    }
}
