/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dao.OrderDao;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dr304
 */
public class OrderDaoStubImpl implements OrderDao {

    public Order onlyOrder;

    public OrderDaoStubImpl() {

        onlyOrder = new Order();
        onlyOrder.setCustomerName("De Bugger");
        onlyOrder.setOrderNumber(1);
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        onlyOrder.setOrderDate(ld);
        onlyOrder.setArea(new BigDecimal("600").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        tax.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax.setStateName("Ohio");
        onlyOrder.setTax(tax);
        Product product = new Product();
        product.setProductName("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        onlyOrder.setProduct(product);
    }

    public OrderDaoStubImpl(Order testOrder) {
        this.onlyOrder = testOrder;
    }

    @Override
    public Order createOrder(Order order) throws FlooringPersistenceException {
        if (order.getOrderNumber() != onlyOrder.getOrderNumber()) {
            return order;
        } else {
            return null;
        }
    }

    @Override
    public Order getOrderById(LocalDate date, int orderNumber) throws FlooringPersistenceException {
        if (date.equals(onlyOrder.getOrderDate()) && orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate date) throws FlooringPersistenceException {
        List<Order> ordersByDate = new ArrayList<>();
        ordersByDate.add(onlyOrder);
        return ordersByDate;
    }

    @Override
    public List<Order> getAllOrders() throws FlooringPersistenceException {
        List<Order> orders = new ArrayList<>();
        orders.add(onlyOrder);
        return orders;
    }

    @Override
    public Order updateOrder(Order order) throws FlooringPersistenceException {
        if (order.getOrderNumber() == onlyOrder.getOrderNumber()) {
            return order;
        } else {
            return null;
        }

    }

    @Override
    public Order deleteOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException {
        if (date.equals(onlyOrder.getOrderDate()) && orderNumber == onlyOrder.getOrderNumber()) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public void exportAllOrders() throws FlooringPersistenceException {
        //Do nothing..
    }

}
