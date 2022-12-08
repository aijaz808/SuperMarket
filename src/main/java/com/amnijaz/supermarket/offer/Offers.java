package com.amnijaz.supermarket.offer;

public interface Offers {

    public void buyTwoGetOneFree(String item, Long count);
    public void buyOneGetHalfOff(String price, Long count);
}
