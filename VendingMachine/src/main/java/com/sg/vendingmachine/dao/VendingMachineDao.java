/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/06/22
 * purpose: M3-Summative - Vending Machine
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.util.List;

public interface VendingMachineDao {

    public Item addItem(String id, Item item) throws VendingMachinePersistenceException;

    public Item updateItem(String id, Item newItem) throws VendingMachinePersistenceException;

    public Item removeItem(String id) throws VendingMachinePersistenceException;

    public List<Item> getAllItems() throws VendingMachinePersistenceException;

    public Item getItemById(String id) throws VendingMachinePersistenceException;
    

}
