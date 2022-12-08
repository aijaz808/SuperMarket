/**
 * @param file
 * @return
 */
package com.amnijaz.supermarket.inventory;

import com.amnijaz.supermarket.exception.EmptyInventoryException;

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
                String[] item= line.split(commaDelimiter);

                InventoryModel inventoryModel=new InventoryModel(Double.parseDouble(item[1]),
                        Long.parseLong(item[2]));
                inventoryModelMap.put(item[0].toString(), inventoryModel);

            }


        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new EmptyInventoryException("Inventory Empty");
        }catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return inventoryModelMap;
    }


}
