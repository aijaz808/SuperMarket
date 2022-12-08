package com.amnijaz.supermarket.client;

import com.amnijaz.supermarket.bill.Bill;
import com.amnijaz.supermarket.bill.BillImpl;
import com.amnijaz.supermarket.inventory.CartModel;
import com.amnijaz.supermarket.inventory.Inventory;
import com.amnijaz.supermarket.inventory.InventoryImpl;
import com.amnijaz.supermarket.inventory.InventoryModel;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ClientImpl implements Client{
    private String SPACE_DELIMITER = " ";
    public static Double DISCOUNT= 0.00;

    private Inventory inventory = new InventoryImpl();

    private Bill bill =new BillImpl();

    public static Map<String , CartModel> CART_MODEL_MAP=new HashMap<>();

    public static Map<String , InventoryModel> INVENTORY_MODEL_MAP=new HashMap<>();

    /*
      Customer gives file paths
      Inventory map is initialised to maintain the count
     */
    @Override
    public void getInventoryItemList()  throws FileNotFoundException{

        Scanner scanner = new Scanner(System.in);// Taking input from customer
        String filePath = scanner.nextLine().trim();
        while(filePath.equals("")){
            filePath = scanner.nextLine().trim();
        }

        String[] input = filePath.split(SPACE_DELIMITER);
        List<String> commands = new ArrayList<>();

        INVENTORY_MODEL_MAP = inventory.getAmountAndQuantity(input[0]);

        if (filePath.contains(".txt")) {
            commands = readCommandFile(input[1]);
            processInputForFileMode(commands);
        }
        if (commands.isEmpty()) {
            processInputForInteractiveMode();
        }
    }


    /*
      Commands are executed in the list for case of file mode
     */
    private void processInputForFileMode(List<String> commands) {

        if (commands.get(0).equalsIgnoreCase("checkout")) {
            System.out.println("> "+commands.get(0));
            System.out.println("empty cart");
            for (int i = 1; i < commands.size(); i++) {
                System.out.println("> "+commands.get(i));
                if(commands.get(i).equalsIgnoreCase("checkout")) {
                    System.out.println("done");
                    return;
                }
                bill.processCartInput(commands.get(i));
            }
        }
        else {
            System.out.println("Checking out is mandatory");
        }
    }

    /*
      Commands are taken in case of interactive mode from the customer
     */
    private void processInputForInteractiveMode() {

        Scanner firstInput = new Scanner(System.in);
        String first = firstInput.nextLine().trim().toLowerCase();
        while(first.equals("")){
            first = firstInput.nextLine().trim();
        }
        if(first.equals("checkout")) {
            System.out.println("empty cart");

            Integer checkout = 1;
            while (checkout < 2 ) {

                String input = firstInput.nextLine().trim().toLowerCase();
                bill.processCartInput(input);

                if (input.equalsIgnoreCase("checkout")) {
                    System.out.println("done");
                    checkout++;
                }
            }
        }
        else {
            System.out.println("Checking out is mandatory");
        }
    }


    /*
      Commands are read from file and stored in an arraylist
     */
    private List<String> readCommandFile(String filePath) throws FileNotFoundException {

        String line= "";
        List<String> commands=new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((line = br.readLine()) != null) {
                commands.add(line.trim().toLowerCase());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Command File not found");
            System.exit(1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return commands;
    }
}
