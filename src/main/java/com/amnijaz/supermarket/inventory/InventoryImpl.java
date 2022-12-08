/**
 * @param file
 * @return
 */
package com.amnijaz.supermarket.inventory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InventoryImpl implements Inventory{

    @Override
    public Map<String, InventoryModel> getAmountAndQuantity(String filePath) throws FileNotFoundException {
        String commaDelimiter = ",";
        String line= "";
        Map<String, InventoryModel> inventoryModelMap=new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {
                String[] item= line.trim().split(commaDelimiter);
                InventoryModel inventoryModel=new InventoryModel(Double.parseDouble(item[1]),
                        Long.parseLong(item[2]));
                inventoryModelMap.put(item[0].toString().toLowerCase(), inventoryModel);
            }

        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Inventory Empty");
            System.exit(1);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inventoryModelMap;
    }


}
