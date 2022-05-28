/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dr304
 */
public class VendingMachineDaoImplTest {

    VendingMachineDao testDao;

    public VendingMachineDaoImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testinventory.txt";
        new FileWriter(testFile);
        testDao = new VendingMachineDaoImpl(testFile);
    }

    @Test
    public void testAddGetItem() throws Exception {
        //test method inputs
        String id = "0001";
        Item testItem1 = new Item(id);
        testItem1.setName("TestItem1");
        testItem1.setCost(new BigDecimal("2.00"));
        testItem1.setTotal(3);

        //Adding item to DAO
        testDao.addItem(id, testItem1);

        //Getting item from DAO
        Item retrievedItem = testDao.getItemById(id);

        //Checking if data is equal
        assertEquals(testItem1.getId(), retrievedItem.getId(), "Checking item id.");
        assertEquals(testItem1.getName(), retrievedItem.getName(), "Checking item name.");
        assertEquals(testItem1.getCost(), retrievedItem.getCost(), "Checking Cost.");
        assertEquals(testItem1.getTotal(), retrievedItem.getTotal(), "Checking item total.");
    }

    @Test
    public void testAddGetAllItems() throws Exception {
        //Creating first item
        Item firstItem = new Item("0001");
        firstItem.setName("TestItem1");
        firstItem.setCost(new BigDecimal("1.50"));
        firstItem.setTotal(1);

        //Creating second item
        Item secondItem = new Item("0002");
        secondItem.setName("TestItem2");
        secondItem.setCost(new BigDecimal("2.50"));
        secondItem.setTotal(2);

        //Adding both items to DAO
        testDao.addItem(firstItem.getId(), firstItem);
        testDao.addItem(secondItem.getId(), secondItem);

        //Retrieving list of items from DAO
        List<Item> allItems = testDao.getAllItems();

        //Check general contents
        assertNotNull(allItems, "List of items must not be null.");
        assertEquals(2, allItems.size(), "List of items should have 2 items.");

        //Then the specifics
        assertTrue(testDao.getAllItems().contains(firstItem), "The list of items should include TestItem1.");
        assertTrue(testDao.getAllItems().contains(secondItem), "The list of items should include TestItem2.");
    }

    @Test
    public void testRemoveItem() throws Exception {
        //Creating two new items
        Item firstItem = new Item("0001");
        firstItem.setName("TestItem1");
        firstItem.setCost(new BigDecimal("1.50"));
        firstItem.setTotal(1);

        Item secondItem = new Item("0002");
        secondItem.setName("TestItem2");
        secondItem.setCost(new BigDecimal("2.50"));
        secondItem.setTotal(2);

        //Adding both to DAO
        testDao.addItem(firstItem.getId(), firstItem);
        testDao.addItem(secondItem.getId(), secondItem);

        //Removing the first item - testItem1
        Item removedItem = testDao.removeItem(firstItem.getId());

        //Checking that the correct item was removed
        assertEquals(removedItem, firstItem, "The removed item should be TestItem1.");

        //Getting all items
        List<Item> allItems = testDao.getAllItems();

        //Checking general contents
        assertNotNull(allItems, "All items list should not be null.");
        assertEquals(1, allItems.size(), "All items should only have 1 item.");

        //Then specifics
        assertFalse(allItems.contains(firstItem), "All items should NOT include TestItem1.");
        assertTrue(allItems.contains(secondItem), "All items should NOT include TestItem2.");

        //Removing second item
        removedItem = testDao.removeItem(secondItem.getId());

        //Checking correct item was removed
        assertEquals(removedItem, secondItem, "The removed item should be TestItem2.");

        //Retrieving items again
        allItems = testDao.getAllItems();

        //Checking contents of list - should be empty now
        assertTrue(allItems.isEmpty(), "The retrieved list of items should be empty.");

        //Trying to get old items by id- they should be null
        Item retrievedItem = testDao.getItemById(firstItem.getId());
        assertNull(retrievedItem, "TestItem1 was removed, should be null.");
        retrievedItem = testDao.getItemById(secondItem.getId());
        assertNull(retrievedItem, "TestItem2 was removed, should be null.");
    }

    @Test
    public void testUpdateItem() throws Exception {

        //Testing id update
        //Creating test input item
        Item originalItem = new Item("0001");
        originalItem.setName("TestItem1");
        originalItem.setCost(new BigDecimal("1.50"));
        originalItem.setTotal(1);

        //Creating updated item with new id
        Item editedIdItem = new Item("0005");
        editedIdItem.setName("TestItem1");
        editedIdItem.setCost(new BigDecimal("1.50"));
        editedIdItem.setTotal(1);

        //Adding originalItem to DAO
        testDao.addItem(originalItem.getId(), originalItem);

        //Editing item
        Item editedItem = testDao.updateItem(originalItem.getId(), editedIdItem);

        //Getting item from DAO
        Item retrievedItem = testDao.getItemById(editedItem.getId());

        //Getting list
        List<Item> allItems = testDao.getAllItems();

        //Checking if original item is still in list
        assertFalse(allItems.contains(originalItem), "All items should not include TestItem1");
        assertTrue(allItems.contains(retrievedItem), "All items should include New Item");

    }

}
