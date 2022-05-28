/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dr304
 */
public class FlooringServiceLayerImpl implements FlooringServiceLayer {

    OrderDao orderDao;
    ProductDao productDao;
    TaxDao taxDao;

    public FlooringServiceLayerImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public Order addOrder(Order order) throws FlooringPersistenceException {
        return orderDao.createOrder(order);
    }

    @Override
    public void saveAllOrdersToBackUp() throws FlooringPersistenceException {
        orderDao.exportAllOrders();
    }

    @Override
    public List<Order> getOrdersByDate(LocalDate date) throws FlooringPersistenceException, NoOrderExistsException {
        if (orderDao.getAllOrdersByDate(date) == null) {
            throw new NoOrderExistsException("No orders exist for that date..");
        }
        return orderDao.getAllOrdersByDate(date);
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException, NoOrderExistsException {
        if (orderDao.getOrderById(date, orderNumber) == null) {
            throw new NoOrderExistsException("No such order exists..");
        }
        return orderDao.getOrderById(date, orderNumber);
    }

    @Override
    public Order editOrder(Order order) throws FlooringPersistenceException, NoOrderExistsException {
        if (orderDao.updateOrder(order) == null) {
            throw new NoOrderExistsException("No such order exists");
        }
        return orderDao.updateOrder(order);
    }

    @Override
    public Order removeOrder(Order order) throws FlooringPersistenceException, NoOrderExistsException {
        Order removedOrder = new Order();
        try {
            removedOrder = orderDao.deleteOrder(order.getOrderDate(), order.getOrderNumber());
        } catch (NullPointerException e) {
            throw new NoOrderExistsException("No such order exists..");
        }
        return removedOrder;
    }

    @Override
    public Order validateAndCalculateTempOrder(Order tempOrder) throws FlooringPersistenceException, FlooringDataValidationException {
        //        ***Validating User Input***       \\
        //
        //Checking for null or empty user input
        if (tempOrder.getOrderDate() == null
                || tempOrder.getOrderDate().toString().trim().length() == 0
                || tempOrder.getCustomerName() == null
                || tempOrder.getCustomerName().trim().length() == 0
                || tempOrder.getTax().getStateAbbreviation() == null
                || tempOrder.getTax().getStateAbbreviation().trim().length() == 0
                || tempOrder.getProduct().getProductName() == null
                || tempOrder.getProduct().getProductName().trim().length() == 0
                || tempOrder.getArea() == null
                || tempOrder.getArea().toString().trim().length() == 0) {

            throw new FlooringDataValidationException("All fields [Date, Name, State, Product, and Area] are required.");
        }

        //Checking name contains no illegal characters
        String name = tempOrder.getCustomerName();
        if (!name.matches("^[A-Za-z0-9., ]+$")) {
            throw new FlooringDataValidationException("Invalid name format");
        }

        //Checking requested state is valid
        String stateInput = tempOrder.getTax().getStateAbbreviation().toUpperCase();
        if (stateInput.length() > 2) {
            throw new FlooringDataValidationException("Only 2 characters are necessary for state abbreviation");
        }
        if (stateInput.length() <= 1) {
            throw new FlooringDataValidationException("You must enter 2 characters for state abbreviation");
        }
        if (taxDao.getTax(stateInput) == null) {
            throw new FlooringDataValidationException("Unfortunately, we do not currently do business in your state.");
        }

        //Checking Product is valid
        String productInput = tempOrder.getProduct().getProductName();
        productInput = productInput.substring(0, 1).toUpperCase() + productInput.substring(1).toLowerCase();

        if (productDao.getProductByType(productInput) == null) {
            throw new FlooringDataValidationException("The product selected is not a valid product");
        }

        //Checking Area is a positive number and above 100
        if (tempOrder.getArea().compareTo(new BigDecimal("100")) < 0) {
            throw new FlooringDataValidationException("Area must be a positive number and above 100");
        }

        //    ***Calcualting remaining Order fields***    \\
        //
        //Assigning tempOrder number to next available
        if (tempOrder.getOrderNumber() == 0) {
            List<Order> orderList = orderDao.getAllOrders();
            int maxOrderNum = orderList.stream()
                    .mapToInt(Order::getOrderNumber)
                    .max()
                    .getAsInt();

            tempOrder.setOrderNumber(maxOrderNum + 1);
        }

        //Assigning Tax and Product Objects to Order
        Tax tax = taxDao.getTax(stateInput);
        tempOrder.setTax(tax);
        Product product = productDao.getProductByType(productInput);
        tempOrder.setProduct(product);

        //Calculating derived fields and assigning to Order
        BigDecimal taxRate = tax.getTaxRate().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        BigDecimal area = tempOrder.getArea().setScale(2, RoundingMode.HALF_UP);
        BigDecimal materialCost = area.multiply(product.getCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP));
        BigDecimal laborCost = area.multiply(product.getLaborCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP));
        BigDecimal totalTax = materialCost.add(laborCost).multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal grandTotal = materialCost.add(laborCost).add(totalTax).setScale(2, RoundingMode.HALF_UP);
        tempOrder.setMaterialCost(materialCost);
        tempOrder.setLaborCost(laborCost);
        tempOrder.setTotalTax(totalTax);
        tempOrder.setTotal(grandTotal);

        //Return the hydrated/validated tempOrder
        return tempOrder;

    }

    @Override
    public List<Tax> getAllStates() throws FlooringPersistenceException {
        return taxDao.getAllTaxes();
    }

    @Override
    public List<Product> getAllProducts() throws FlooringPersistenceException {
        return productDao.getAllProducts();
    }

}
