/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/06/22
 * purpose: M3-Summative - Vending Machine
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.util.List;

public interface VendingMachineServiceLayer {

    public Change vendItem(String id, Change insertedAmt) throws
            VendingMachinePersistenceException,
            NoItemInventoryException,
            InsufficientFundsException,
            VendingMachineIdException;

    public List<Item> getItemsInStock() throws
            VendingMachinePersistenceException;

    public Item addItem(String id, Item item) throws //admin
            VendingMachinePersistenceException,
            VendingMachineIdException;

    public Item removeItem(String id) throws //admin
            VendingMachinePersistenceException,
            VendingMachineIdException;

    public List<Item> getAllItems() throws //admin
            VendingMachinePersistenceException;

    public Item getItemById(String id) throws //admin
            VendingMachinePersistenceException;

    public Item updateItem(String id, Item newItem) throws //admin
            VendingMachinePersistenceException,
            VendingMachineIdException;

}
