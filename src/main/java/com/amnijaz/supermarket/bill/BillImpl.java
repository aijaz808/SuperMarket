package com.amnijaz.supermarket.bill;

import com.amnijaz.supermarket.client.ClientImpl;
import com.amnijaz.supermarket.inventory.CartModel;
import com.amnijaz.supermarket.inventory.InventoryModel;
import com.amnijaz.supermarket.offer.Offers;
import com.amnijaz.supermarket.offer.OffersImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class BillImpl implements Bill{


    /**
     * @param inventoryModelMap
     * @param input
     */

    private String SPACE_DELIMITER = " ";

    private Offers offer=new OffersImpl();


    /**
     * @param input
     */
    @Override
    public Boolean processCartInput(String input) {
        if(input.toLowerCase().startsWith("add")){
            processItemAddition(input);
        }
        else if (input.toLowerCase().startsWith("offer")) {
            processItemOffer(input);
        }
        else if (input.toLowerCase().startsWith("bill")) {
            calculateBill();
        }

        return Boolean.TRUE;
    }

    private void calculateBill() {
        Integer size= ClientImpl.CART_MODEL_MAP.size();
        Collection<String> cart= ClientImpl.CART_MODEL_MAP.keySet();
        Double subTotal = 0.00;
        Double total= 0.00;
        for (String item : cart){
            Double price = ClientImpl.INVENTORY_MODEL_MAP.get(item).getAmount();
            Long quantity = ClientImpl.CART_MODEL_MAP.get(item).getQuantity();
            subTotal+=(price*quantity);
        }
       /* cartItems.stream().forEach(item -> {
            Double price = ClientImpl.INVENTORY_MODEL_MAP.get(item).getAmount();
            Long quantity = ClientImpl.CART_MODEL_MAP.get(item).getQuantity();
            subTotal.updateAndGet(v -> v + price * quantity);
            total.updateAndGet(v -> v + subTotal.get() - ClientImpl.DISCOUNT);
        });*/
        System.out.print("subtotal :");
        System.out.printf("%.2f", subTotal);
        System.out.print( ", discount:" );
        System.out.printf("%.2f",ClientImpl.DISCOUNT );
        System.out.print(", total: ");
        System.out.printf("%.2f",(subTotal-ClientImpl.DISCOUNT));
    }

    private void processItemOffer(String input) {

        String[] cartInput = input.split(SPACE_DELIMITER);
        try {

            if (cartInput.length == 3) {
                if (ClientImpl.INVENTORY_MODEL_MAP.containsKey(cartInput[2])) {
                   if(cartInput[1].equalsIgnoreCase("buy_2_get_1_free")) {
                       if (ClientImpl.CART_MODEL_MAP.containsKey(cartInput[2])) {
                           CartModel cartModel = ClientImpl.CART_MODEL_MAP.get(cartInput[2]);

                               if (cartModel.getOffer() != null && cartModel.getOffer().equalsIgnoreCase("buy_1_get_half_off")) {
                                   Long startingPoint = (cartModel.getOfferCount() * 2);
                                   Long tempQuantity = cartModel.getQuantity() - startingPoint;
                                   if (tempQuantity >= 3) {
                                       Long tempOffer = tempQuantity / 3;
                                       offer.buyTwoGetOneFree(cartInput[2], tempOffer);
                                       cartModel.setOffer("buy_2_get_1_free");
                                       cartModel.setOfferCount(tempOffer);
                                       cartModel.setStartingPoint(startingPoint);
                                       ClientImpl.CART_MODEL_MAP.put(cartInput[2], cartModel);
                                   }


                               }
                           else if (cartModel.getOffer() == null) {
                                   Long quantity = cartModel.getQuantity();
                                   if (quantity >= 3) {
                                       Long tempOffer = quantity / 3;
                                       offer.buyTwoGetOneFree(cartInput[2], tempOffer);
                                       cartModel.setOffer("buy_2_get_1_free");
                                       cartModel.setStartingPoint(0L);
                                       cartModel.setOfferCount(tempOffer);
                                       ClientImpl.CART_MODEL_MAP.put(cartInput[2], cartModel);
                                   }else {
                                       cartModel.setOffer("buy_2_get_1_free");
                                       cartModel.setStartingPoint(0L);
                                       cartModel.setOfferCount(0L);
                                       ClientImpl.CART_MODEL_MAP.put(cartInput[2], cartModel);
                                   }
                               }
                           System.out.println("offer added");

                       }else {
                           System.out.println("The item does not exist in the cart");
                       }
                   }
                   else if (cartInput[1].equalsIgnoreCase("buy_1_get_half_off")) {
                       if(ClientImpl.CART_MODEL_MAP.containsKey(cartInput[2])){
                           CartModel cartModel= ClientImpl.CART_MODEL_MAP.get(cartInput[2]);
                           if(cartModel.getOffer() != null && cartModel.getOffer().equalsIgnoreCase("buy_2_get_1_free"))
                           {
                               Long startingPoint= (cartModel.getOfferCount() *2);
                               Long tempQuantity = cartModel.getQuantity() -startingPoint;
                               if(tempQuantity >=  2){
                                   Long tempOffer=tempQuantity/2;
                                   offer.buyOneGetHalfOff(cartInput[2], tempOffer);
                                   cartModel.setOffer("buy_1_get_half_off");
                                   cartModel.setOfferCount(tempOffer);
                                   cartModel.setStartingPoint(startingPoint);
                                   ClientImpl.CART_MODEL_MAP.put(cartInput[2], cartModel);
                               }


                           }else if (cartModel.getOffer()==null) {
                               Long quantity =cartModel.getQuantity();
                               if(quantity >= 2){
                                   Long tempOffer=quantity/2;
                                   offer.buyOneGetHalfOff(cartInput[2], tempOffer);
                                   cartModel.setOfferCount(tempOffer);
                               }else {
                                   cartModel.setOffer("buy_1_get_half_off");
                                   cartModel.setStartingPoint(0L);
                                   cartModel.setOfferCount(0L);
                                   ClientImpl.CART_MODEL_MAP.put(cartInput[2], cartModel);
                               }

                           }

                           System.out.println("offer added");
                       }else{
                           System.out.println("The item does not exist in the cart");
                       }
                   }
                   else{
                       System.out.println("Wrong offer entered");
                   }
                }else{
                    System.out.println("Oops! This item does not exist in the inventory.");
                }
            }
        }catch (RuntimeException e){
            System.out.println("");
        }

    }

    public Boolean processItemAddition( String input) {

            String[] cartInput = input.split(SPACE_DELIMITER);
            try {

                Long cartQuantity = Long.parseLong(cartInput[2]);
                if (cartInput.length == 3) {
                    if (ClientImpl.INVENTORY_MODEL_MAP.containsKey(cartInput[1])) {

                        InventoryModel inventoryModel = ClientImpl.INVENTORY_MODEL_MAP.get(cartInput[1]);

                        if (inventoryModel.getQuantity() >= cartQuantity) {
                            if (ClientImpl.CART_MODEL_MAP.containsKey(cartInput[1])) {

                                CartModel cartModel = ClientImpl.CART_MODEL_MAP.get(cartInput[1]);

                                if (cartModel.getOffer() != null) {

                                    Long quantity = cartModel.getQuantity() + cartQuantity;

                                    if (cartModel.getOffer().equalsIgnoreCase("buy_2_get_1_free")) {

                                        if ((quantity-cartModel.getStartingPoint()) >= (cartModel.getOfferCount() * 3) + 3) {
                                            Long tempOffer= (quantity-cartModel.getStartingPoint())/3;
                                            Long count = tempOffer - cartModel.getOfferCount();
                                            offer.buyTwoGetOneFree(cartInput[1], count);

                                            cartModel.setQuantity(quantity);
                                            cartModel.setOfferCount(tempOffer);
                                            ClientImpl.CART_MODEL_MAP.replace(cartInput[1], cartModel);

                                        } else {
                                            cartModel.setQuantity(quantity);
                                            ClientImpl.CART_MODEL_MAP.put(cartInput[1], cartModel);
                                        }
                                    } else if (cartModel.getOffer().equalsIgnoreCase("buy_1_get_half_off")) {

                                        if ((quantity-cartModel.getStartingPoint()) >= (cartModel.getOfferCount() * 2) + 2) {
                                            Long tempOffer= (quantity-cartModel.getStartingPoint())/2;
                                            Long count = tempOffer - cartModel.getOfferCount();
                                            offer.buyOneGetHalfOff(cartInput[1], count);

                                            cartModel.setQuantity(quantity);
                                            cartModel.setOfferCount(tempOffer);
                                            ClientImpl.CART_MODEL_MAP.replace(cartInput[1], cartModel);
                                        } else {
                                            cartModel.setQuantity(quantity);
                                            ClientImpl.CART_MODEL_MAP.replace(cartInput[1], cartModel);
                                        }

                                    }
                                }
                                else {
                                    cartModel.setQuantity(cartQuantity);
                                    ClientImpl.CART_MODEL_MAP.put(cartInput[1], cartModel);
                                }
                            } else {
                                CartModel cartModel = new CartModel(cartQuantity, null, 0L,null);
                                ClientImpl.CART_MODEL_MAP.put(cartInput[1], cartModel);
                            }
                            inventoryModel.setQuantity(inventoryModel.getQuantity()-cartQuantity);
                            ClientImpl.INVENTORY_MODEL_MAP.put(cartInput[1],inventoryModel);
                            System.out.println("added "+ cartInput[1] + " "+ cartInput[2]);
                            return Boolean.TRUE;
                        }
                        else {
                            System.out.println("Item is not sufficient in inventory");
                            //processInputForInteractiveMode(inventoryModelMap);
                            return Boolean.TRUE;

                        }
                    }
                    else {
                        System.out.println("Oops! This item does not exist in the inventory.");
                        return Boolean.TRUE;
                    }
                } else {
                    System.out.println("Input entered is not correct");
                    //processInputForInteractiveMode(inventoryModelMap);
                    return Boolean.TRUE;
                }
            }catch(NumberFormatException e){
                throw new RuntimeException("The quantity entered is not valid");
            }
        }


}
