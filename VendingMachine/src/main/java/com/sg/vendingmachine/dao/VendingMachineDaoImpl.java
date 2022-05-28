/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/06/22
 * purpose: M3-Summative - Vending Machine
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineDaoImpl implements VendingMachineDao {

    private final String INVENTORY_FILE;
    public static final String DELIMITER = "::";

    private final Map<String, Item> allItems = new HashMap<>();

    public VendingMachineDaoImpl() {
        INVENTORY_FILE = "inventory.txt";
    }

    public VendingMachineDaoImpl(String inventoryTextFile) {
        INVENTORY_FILE = inventoryTextFile;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {   //read all
        loadInventory();
        return new ArrayList<>(allItems.values());
    }

    @Override
    public Item addItem(String id, Item item) throws VendingMachinePersistenceException {  //create
        loadInventory();
        allItems.put(id, item);
        writeInventory();
        return item;
    }

    @Override
    public Item removeItem(String id) throws VendingMachinePersistenceException {  //delete
        loadInventory();
        Item removedItem = allItems.remove(id);
        writeInventory();
        return removedItem;
    }

    @Override
    public Item updateItem(String oldItemId, Item newItem) throws VendingMachinePersistenceException {  //update
        loadInventory();
        allItems.remove(oldItemId);
        allItems.put(newItem.getId(), newItem);
        writeInventory();
        return newItem;

    }

    @Override
    public Item getItemById(String id) throws VendingMachinePersistenceException {  //read by id
        loadInventory();
        return allItems.get(id);

    }

    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("Yikes! Could not load inventory data in memory.", e);
        }
        String currentLine;
        Item currentItem;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItems(currentLine);
            allItems.put(currentItem.getId(), currentItem);
        }
        scanner.close();
    }

    private void writeInventory() throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Yikes! Could not save inventory data");
        }
        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            itemAsText = marshallItems(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();

    }

    private String marshallItems(Item anItem) {
        String itemAsText = anItem.getId() + DELIMITER;
        itemAsText += anItem.getName() + DELIMITER;
        itemAsText += anItem.getCost() + DELIMITER;
        itemAsText += anItem.getTotal();
        return itemAsText;
    }

    private Item unmarshallItems(String itemAsText) {
        String[] itemTokens = itemAsText.split(DELIMITER);
        String id = itemTokens[0];
        Item itemFromFile = new Item(id);
        itemFromFile.setName(itemTokens[1]);
        itemFromFile.setCost(new BigDecimal(itemTokens[2]));
        itemFromFile.setTotal(Integer.parseInt(itemTokens[3]));
        return itemFromFile;
    }

}
