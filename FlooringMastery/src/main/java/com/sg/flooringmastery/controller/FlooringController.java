/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import com.sg.flooringmastery.service.FlooringDataValidationException;
import com.sg.flooringmastery.service.FlooringServiceLayer;
import com.sg.flooringmastery.service.NoOrderExistsException;
import com.sg.flooringmastery.view.FlooringView;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dr304
 */
public class FlooringController {

    FlooringView view;
    FlooringServiceLayer service;

    public FlooringController(FlooringView view, FlooringServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            try {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        updateOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportOrders();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        getError();
                }
            } catch (FlooringPersistenceException | FlooringDataValidationException | NoOrderExistsException e) {
                view.displayErrorMsg(e.getMessage());
            }
        }
        exit();

    }

    private int getMenuSelection() {
        return view.displayMenuAndGetChoice();
    }

    private void displayOrders() throws FlooringPersistenceException, NoOrderExistsException {
        view.displayBanner("Display Orders");
        LocalDate date = view.getDate();
        List<Order> orders = service.getOrdersByDate(date);
        view.displayAllOrders(orders);
    }

    private void addOrder() throws FlooringPersistenceException, FlooringDataValidationException {
        List<Product> allProducts = service.getAllProducts();
        List<Tax> allStates = service.getAllStates();
        view.displayBanner("Add Order");
        Order tempOrder = view.getNewOrderInfo(allProducts, allStates);
        Order newOrder = service.validateAndCalculateTempOrder(tempOrder);
        view.displayOrder(newOrder);
        if (view.confirmChanges("Add", newOrder)) {
            service.addOrder(newOrder);
            view.displaySuccessMsg("Add", newOrder);
        } else {
            view.displayNoChangesMadeMsg();
        }

    }

    private void updateOrder() throws FlooringPersistenceException, NoOrderExistsException, FlooringDataValidationException {
        List<Product> allProducts = service.getAllProducts();
        List<Tax> allTaxes = service.getAllStates();
        view.displayBanner("Edit Order");
        LocalDate date = view.getDate();
        int orderNumber = view.getOrderNumber();
        Order oldOrder = service.getOrder(date, orderNumber);
        Order tempOrder = view.getEditOrderInfo(oldOrder, allProducts, allTaxes);
        Order newOrder = service.validateAndCalculateTempOrder(tempOrder);
        view.displayOrder(newOrder);
        if (view.confirmChanges("Update", newOrder)) {
            service.editOrder(newOrder);
            view.displaySuccessMsg("Updat", newOrder);
        } else {
            view.displayNoChangesMadeMsg();
        }
    }

    private void removeOrder() throws FlooringPersistenceException, NoOrderExistsException {
        view.displayBanner("Remove Order");
        LocalDate date = view.getDate();
        int orderNumber = view.getOrderNumber();
        Order order = service.getOrder(date, orderNumber);
        view.displayOrder(order);
        if (view.confirmChanges("Remove", order)) {
            service.removeOrder(order);
            view.displaySuccessMsg("Remov", order);
        } else {
            view.displayNoChangesMadeMsg();
        }
    }

    private void exportOrders() throws FlooringPersistenceException {
        service.saveAllOrdersToBackUp();
        view.displayExportMsg();
    }

    private void exit() {
        view.displayExitMessage();
    }

    private void getError() {
        view.displayUnknownCommandMsg();
    }
}
