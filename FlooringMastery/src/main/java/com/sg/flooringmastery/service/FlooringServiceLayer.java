/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dr304
 */
public interface FlooringServiceLayer {

    public Order addOrder(Order order) throws FlooringPersistenceException;

    public void saveAllOrdersToBackUp() throws FlooringPersistenceException;

    public List<Order> getOrdersByDate(LocalDate date) throws FlooringPersistenceException, NoOrderExistsException;

    public Order getOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException, NoOrderExistsException ;

    public Order editOrder(Order order) throws FlooringPersistenceException, NoOrderExistsException;

    public Order removeOrder(Order order) throws FlooringPersistenceException, NoOrderExistsException;

    public Order validateAndCalculateTempOrder(Order order) throws FlooringPersistenceException, FlooringDataValidationException;

    public List<Tax> getAllStates() throws FlooringPersistenceException;

    public List<Product> getAllProducts() throws FlooringPersistenceException;

}
