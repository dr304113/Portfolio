/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/06/22
 * purpose: M3-Summative - Vending Machine
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineIdException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

public class VendingMachineController {

    VendingMachineServiceLayer service;
    VendingMachineView view;

    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        String id = "";
        int startSelection;
        boolean go = true;
        boolean keepGoing = true;
        Change credits = new Change(new BigDecimal("0"));
        while (go) {
            try {
                startSelection = getMenuAndLoadInventory();
                switch (startSelection) {
                    case 3:
                        exitMessage();
                        go = false;
                        break;
                    case 2:
                        try {
                        adminMenu();
                    } catch (VendingMachineIdException e) {

                    }
                    break;
                    case 1:
                        credits = getCoinsAndDisplay(credits);
                        while (keepGoing) {
                            int selection = getMenuSelection();
                            switch (selection) {
                                case 1:
                                    sellItem(id, credits);
                                    credits = new Change(new BigDecimal("0"));
                                    break;
                                case 2:
                                    credits = getCoinsAndDisplay(credits);
                                    break;
                                case 3:
                                    refund(credits);
                                    exitMessage();
                                    keepGoing = false;
                                    go = false;
                                    break;
                                default:
                                    getError();
                            }
                        }
                }

            } catch (VendingMachinePersistenceException | InsufficientFundsException | NoItemInventoryException | VendingMachineIdException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
    }

    private int getMenuAndLoadInventory() throws VendingMachinePersistenceException {
        List<Item> list = service.getItemsInStock();
        return view.displayMenuBeforeCoins(list);
    }

    private void refund(Change credits) throws VendingMachinePersistenceException {
        view.displayRefund(credits);
    }

    private void sellItem(String id, Change credits) throws
            VendingMachinePersistenceException,
            NoItemInventoryException,
            InsufficientFundsException,
            VendingMachineIdException {

        List<Item> items = service.getItemsInStock();
        id = view.displayMenuAndGetSelection(items);
        Change refundAmount = service.vendItem(id, credits);
        view.displaySaleRefund(service.getItemById(id), refundAmount);
    }

    public Change getCoinsAndDisplay(Change existingCredits) {
        return view.getCoinsAndDisplayAmount(existingCredits);
    }

    private void exitMessage() {
        view.displayExitMessage();
    }

    private int getMenuSelection() throws VendingMachinePersistenceException {
        return view.displaySelectAddExitPrompt();
    }

    private void adminMenu() throws
            VendingMachinePersistenceException,
            VendingMachineIdException {  //admin

        boolean keepAdminGoing = view.displayPasswordPrompt();
        while (keepAdminGoing) {
            int adminSelection = view.displayAdminMenu();
            switch (adminSelection) {
                case 1:
                    viewAllItems();
                    break;
                case 2:
                    addItem();
                    break;
                case 3:
                    editItem();
                    break;
                case 4:
                    removeItem();
                    break;
                case 5:
                    keepAdminGoing = false;
                default:
                    keepAdminGoing = false;

            }
        }
    }

    private void addItem() throws
            VendingMachinePersistenceException,
            VendingMachineIdException { //admin 

        Item item = view.displayAddItem();
        service.addItem(item.getId(), item);
    }

    private void removeItem() throws
            VendingMachinePersistenceException,
            VendingMachineIdException { //admin

        List<Item> list = service.getAllItems();
        Item item = view.displayRemoveItem(list);
        service.removeItem(item.getId());
    }

    private void viewAllItems() throws VendingMachinePersistenceException { //admin
        List<Item> items = service.getAllItems();
        view.displayAllItems(items);
    }

    private void editItem() throws VendingMachinePersistenceException, VendingMachineIdException {  //admin
        List<Item> items = service.getAllItems();
        String id = view.displayItemsAndGetChoice(items);
        Item prevItem = service.getItemById(id);
        if (prevItem != null) {
            Item newItem = view.displayEditItemMenu(prevItem);
            service.updateItem(prevItem.getId(), newItem);
        } else {
            view.displayItemDoesntExistError();
        }
    }

    private void getError() {
        view.displayErrorMessage("Unkown Command");
    }
}
