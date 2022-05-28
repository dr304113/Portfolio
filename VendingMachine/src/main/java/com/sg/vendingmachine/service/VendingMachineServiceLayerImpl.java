/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/06/22
 * purpose: M3-Summative - Vending Machine
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineAuditDao auditDao;
    private VendingMachineDao dao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public List<Item> getItemsInStock() throws VendingMachinePersistenceException {
        return dao.getAllItems().stream()
                .filter((s) -> s.getTotal() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public Change vendItem(String id, Change insertedAmt) throws NoItemInventoryException,
            InsufficientFundsException, VendingMachinePersistenceException, VendingMachineIdException {
        Change refundChange;
        Item selectedItem;
        //Getting selected item
        try {
            selectedItem = dao.getItemById(id);
        } catch (NullPointerException e) {
            throw new VendingMachineIdException("No Item with this ID exists.");
        }

        //Making sure sufficient funds were inserted
        if (insertedAmt.getAmount().compareTo(selectedItem.getCost()) < 0) {
            throw new InsufficientFundsException("!!! INSUFFICIENT FUNDS !!! \n"
                    + selectedItem.getName() + " Cost: " + selectedItem.getCost() + "\n"
                    + "Credits: " + insertedAmt.getAmount().setScale(2, RoundingMode.HALF_UP).toString());

            //Checking item inventory total
        } else if (selectedItem.getTotal() == 0) {
            throw new NoItemInventoryException(selectedItem.getName() + " IS OUT OF STOCK");

            //Decrement Item inventory total
        } else {
            Item newItem = new Item(id);
            newItem.setCost(selectedItem.getCost());
            newItem.setTotal(selectedItem.getTotal() - 1);
            newItem.setName(selectedItem.getName());
            dao.updateItem(selectedItem.getId(), newItem);

            //Calculating refund change
            refundChange = new Change(insertedAmt.getAmount().subtract(selectedItem.getCost()));

            //Reporting to Audit File
            auditDao.writeAuditEntry(selectedItem.getName() + " SOLD");
            if (selectedItem.getTotal() == 0) {
                auditDao.writeAuditEntry(selectedItem.getName() + " IS OUT OF STOCK");
            }
        }
        return refundChange;
    }

    @Override //ADMIN
    public Item addItem(String id, Item item) throws
            VendingMachinePersistenceException,
            VendingMachineIdException {

        if (dao.getItemById(id) != null) {
            throw new VendingMachineIdException("ERROR: Could not add item. Item ID: "
                    + id + " already exists.");
        }

        Item addedItem = dao.addItem(id, item);
        auditDao.writeAuditEntry("Item id: " + item.getId() + " ADDED TO INVENTORY.");
        return addedItem;
    }

    @Override  //ADMIN
    public Item removeItem(String id) throws VendingMachinePersistenceException, VendingMachineIdException {
        Item removedItem = dao.removeItem(id);
        if (removedItem == null) {
            throw new VendingMachineIdException("ERROR: Could not remove Item. Item ID#: "
                    + id + " does not exist.");
        }
        return removedItem;
    }

    @Override //ADMIN
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    @Override //ADMIN
    public Item getItemById(String id) throws VendingMachinePersistenceException {
        return dao.getItemById(id);
    }

    @Override  //ADMIN
    public Item updateItem(String id, Item newItem) throws VendingMachinePersistenceException, VendingMachineIdException {
        Item item = dao.getItemById(id);
        if (item == null) {
            throw new VendingMachineIdException("ERROR: Could not update item with ID: " + id);
        }
        Item updatedItem = dao.updateItem(id, newItem);
        return updatedItem;
    }

}
