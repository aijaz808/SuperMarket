package com.amnijaz.supermarket.client;

import com.amnijaz.supermarket.bill.Bill;
import com.amnijaz.supermarket.bill.BillImpl;
import com.amnijaz.supermarket.inventory.CartModel;
import com.amnijaz.supermarket.inventory.Inventory;
import com.amnijaz.supermarket.inventory.InventoryImpl;
import com.amnijaz.supermarket.inventory.InventoryModel;
import com.amnijaz.supermarket.offer.Offers;
import com.amnijaz.supermarket.offer.OffersImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ClientImpl implements Client{
    private String SPACE_DELIMITER = " ";

    public static Double SUBTOTAL = 0.00;
    public static Double TOTAL= 0.00;
    public static Double DISCOUNT= 0.00;


    private Inventory inventory = new InventoryImpl();

    private Bill bill =new BillImpl();

    public static Map<String , CartModel> CART_MODEL_MAP=new HashMap<>();

    public static Map<String , InventoryModel> INVENTORY_MODEL_MAP=new HashMap<>();

    /**
     * @return
     */
    @Override
    public void getInventoryItemList() throws FileNotFoundException {


        String spaceDelimiter= " ";
        Scanner scanner=new Scanner(System.in);// Taking input from customer
        String filePath=scanner.nextLine();
        String[] input=  filePath.split(SPACE_DELIMITER);
        List<String> commands=new ArrayList<>();
        if(filePath.contains(".txt")){
            commands = readCommandFile(input[1]);
        }
        INVENTORY_MODEL_MAP= inventory.getAmountAndQuantity(input[0]);
        if(commands.isEmpty()){
            System.out.println(INVENTORY_MODEL_MAP.get("bread").getAmount());
            System.out.println(INVENTORY_MODEL_MAP.get("soap").getAmount());
            System.out.println(commands);

            Scanner cartInput=new Scanner(System.in);
            String inputCart= cartInput.nextLine().trim();
            if(inputCart.equalsIgnoreCase("checkout")) {
                System.out.println("empty cart");
                processInputForInteractiveMode();
            }
            else
                throw new RuntimeException();
        }
        System.out.println(INVENTORY_MODEL_MAP);
        System.out.println(commands);

       /* */

    }

    private void processInputForInteractiveMode() {

        Integer checkout = 1;
        while(checkout<2){

                Scanner scanner = new Scanner(System.in);

                String input = scanner.nextLine().trim();
                bill.processCartInput(input);
                if (input.equalsIgnoreCase("checkout")) {
                    System.out.println("done");
                    checkout++;
                }
            }



    }


    private List<String> readCommandFile(String filePath) throws FileNotFoundException {
        String line= "";
        List<String> commands=new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                commands.add(line);
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return commands;
    }
}
