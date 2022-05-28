/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dr304
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {

    public Item onlyItem;
    public Item anotherItem;

    public VendingMachineDaoStubImpl() {
        onlyItem = new Item("0001");
        onlyItem.setName("Only Item");
        onlyItem.setCost(new BigDecimal("2.50"));
        onlyItem.setTotal(2);

        anotherItem = new Item("0000");
        anotherItem.setName("Another Item");
        anotherItem.setCost(new BigDecimal("2.50"));
        anotherItem.setTotal(0);
    }

    public VendingMachineDaoStubImpl(Item testItem1, Item testItem2) {
        this.onlyItem = testItem1;
        this.anotherItem = testItem2;
    }

    @Override
    public Item addItem(String id, Item item) throws VendingMachinePersistenceException {
        if (id.equals(onlyItem.getId())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        itemList.add(anotherItem);
        return itemList;
    }

    @Override
    public Item getItemById(String id) throws VendingMachinePersistenceException {
        if (id.equals(onlyItem.getId())) {
            return onlyItem;
        } else if (id.equals(anotherItem.getId())) {
            return anotherItem;
        } else {
            return null;
        }
    }

    @Override
    public Item removeItem(String id) throws VendingMachinePersistenceException {
        if (id.equals(onlyItem.getId())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public Item updateItem(String id, Item item) throws VendingMachinePersistenceException {
        if (id.equals(onlyItem.getId())) {
            return onlyItem;
        } else {
            return null;
        }
    }

}
