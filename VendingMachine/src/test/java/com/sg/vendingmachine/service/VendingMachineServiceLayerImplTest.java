/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoStubImpl;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author dr304
 */
public class VendingMachineServiceLayerImplTest {

    private VendingMachineServiceLayer service;

    public VendingMachineServiceLayerImplTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();

        service = new VendingMachineServiceLayerImpl(dao, auditDao);
    }

    @Test
    public void testGetItemsInStock() throws Exception {

        //Test clone 1
        Item testClone1 = new Item("0001");
        testClone1.setName("Only Item");
        testClone1.setCost(new BigDecimal("2.50"));
        testClone1.setTotal(2);

        //Test clone 2
        Item testClone2 = new Item("0000");
        testClone2.setName("Another Item");
        testClone2.setCost(new BigDecimal("2.50"));
        testClone2.setTotal(0);

        assertEquals(1, service.getItemsInStock().size(), "Should only include testClone1.");
        assertTrue(service.getItemsInStock().contains(testClone1), "List should only include testClone1");
        assertFalse(service.getItemsInStock().contains(testClone2), "Has 0 inventory, testClone2 should not be in list.");
    }

    @Test
    public void testVendItemValid() {
        Change actualRefund;
        Change expectedRefund = new Change(new BigDecimal("0.50"));
        //test amount
        Change insertedAmt = new Change(new BigDecimal("3.00"));
        //test item
        Item testClone = new Item("0001");
        testClone.setName("Only Item");
        testClone.setCost(new BigDecimal("2.50"));
        testClone.setTotal(2);

        //test 1 -Valid transaction, no exception should be thrown
        try {
            actualRefund = service.vendItem(testClone.getId(), insertedAmt);
            assertEquals(expectedRefund.getAmount(), actualRefund.getAmount(), "Refund amount should be 0.50");
            assertEquals(expectedRefund.getQuarters(), actualRefund.getQuarters(), "Should be 2 quarters");
            assertEquals(expectedRefund.getDimes(), actualRefund.getDimes(), "Should be 0 dimes");
            assertEquals(expectedRefund.getNickels(), actualRefund.getNickels(), "Should be 0 nickels");
            assertEquals(expectedRefund.getPennies(), actualRefund.getPennies(), "Should be 0 pennies");
        } catch (InsufficientFundsException
                | NoItemInventoryException
                | VendingMachinePersistenceException
                | VendingMachineIdException e) {
            fail("This is a valid transaction, no exception should have been thrown");
        }

    }

    @Test
    public void testVendItemInsufficientFunds() {
        //test amount
        Change insertedAmt = new Change(new BigDecimal("1.00"));
        //test item
        Item testClone = new Item("0001");
        testClone.setName("Only Item");
        testClone.setCost(new BigDecimal("2.50"));
        testClone.setTotal(2);

        //InsufficientFundsException should be thrown
        try {
            service.vendItem(testClone.getId(), insertedAmt);
            fail("Not enough funds to cover cost of the item, InsufficientFundsException should have been thrown");
        } catch (NoItemInventoryException | VendingMachinePersistenceException | VendingMachineIdException e) {
            fail("Incorrect exception was thrown.");
        } catch (InsufficientFundsException e) {
            return;
        }
    }

    @Test
    public void testVendItemNoInventory() {
        //test amount
        Change insertedAmt = new Change(new BigDecimal("3.00"));
        //test item
        Item testClone = new Item("0000");
        testClone.setName("Another Item");
        testClone.setCost(new BigDecimal("2.50"));
        testClone.setTotal(0);

        //NoItemInventoryException should be thrown
        try {
            service.vendItem(testClone.getId(), insertedAmt);
            fail("No inventory, NoItemInventoryException should have been thrown");
        } catch (InsufficientFundsException | VendingMachinePersistenceException | VendingMachineIdException e) {
            fail("Incorrect exception was thrown.");
        } catch (NoItemInventoryException e) {
            return;
        }
    }

    @Test
    public void testAddValidItem() {
        String id = "0005";
        Item item = new Item(id);
        item.setName("TestItem1");
        item.setCost(new BigDecimal("1.50"));
        item.setTotal(1);

        try {
            service.addItem(id, item);
        } catch (VendingMachineIdException e) {
            fail("id exception");
        } catch (VendingMachinePersistenceException e) {
            fail("persistence exception");
        }
    }

    @Test
    public void testAddDuplicateIdItem() {
        Item item = new Item("0001");
        item.setName("TestItem1");
        item.setCost(new BigDecimal("1.50"));
        item.setTotal(1);

        try {
            service.addItem(item.getId(), item);
            fail("Expected DupeId Exception was not thrown.");
        } catch (VendingMachinePersistenceException e) {
            fail("Incorrect exception was thrown.");
        } catch (VendingMachineIdException e) {
            return;
        }
    }

    @Test
    public void testgetAllItems() throws Exception {
        Item testClone = new Item("0001");
        testClone.setName("Only Item");
        testClone.setCost(new BigDecimal("2.50"));
        testClone.setTotal(2);

        assertEquals(2, service.getAllItems().size(), "Should have 2 items.");
        assertTrue(service.getAllItems().contains(testClone), "Only Item should be included");
    }

    @Test
    public void testGetItemById() throws Exception {
        Item testClone = new Item("0001");
        testClone.setName("Only Item");
        testClone.setCost(new BigDecimal("2.50"));
        testClone.setTotal(2);

        Item shouldBeOnlyItem = service.getItemById("0001");
        assertNotNull(shouldBeOnlyItem, "Getting 0001 should be not null.");
        assertEquals(testClone, shouldBeOnlyItem, "Item stored under 0001 should be Only Item.");
    }

    @Test
    public void testRemoveItem() throws Exception {
        //ARRANGE
        Item testClone = new Item("0001");
        testClone.setName("Only Item");
        testClone.setCost(new BigDecimal("2.50"));
        testClone.setTotal(2);

        //ACT & ASSERT
        Item shouldBeOnlyItem = service.removeItem("0001");
        assertNotNull(shouldBeOnlyItem, "Removing 0001 should not be null.");
        assertEquals(testClone, shouldBeOnlyItem, "Item removed from 0001 should be Only Item.");

        try {
            service.removeItem("0042");
            fail("VendingMachineIdException should have been thrown. Item is null.");
        } catch (VendingMachineIdException e) {
            return;
        }
    }

}
