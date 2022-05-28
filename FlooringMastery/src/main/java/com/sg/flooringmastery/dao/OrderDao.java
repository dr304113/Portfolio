/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dr304
 */
public interface OrderDao {

    public Order createOrder(Order order) throws FlooringPersistenceException;

    public Order getOrderById(LocalDate date, int orderNumber) throws FlooringPersistenceException;

    public List<Order> getAllOrdersByDate(LocalDate date) throws FlooringPersistenceException;

    public List<Order> getAllOrders() throws FlooringPersistenceException;

    public Order updateOrder(Order order) throws FlooringPersistenceException;

    public Order deleteOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException;

    public void exportAllOrders() throws FlooringPersistenceException;

}
