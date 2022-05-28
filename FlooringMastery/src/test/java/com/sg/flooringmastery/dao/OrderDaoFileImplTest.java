/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import org.junit.jupiter.api.Test;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.maven.shared.utils.io.FileUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author dr304
 */
public class OrderDaoFileImplTest {

    OrderDao testDao;

    public OrderDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("orderDao", OrderDao.class);

        //deletes all order files in 'TestOrders' directory
        File dir = new File("TestOrders");
        FileUtils.cleanDirectory(dir);
    }

    @Test 
    public void testCreateGetOrderById() throws Exception {
        //Creating / Adding test orders to Dao
        Order testOrder1 = createTestOrder1();

        //Creating test input
        LocalDate date = LocalDate.parse("04/02/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        //Getting from DAO
        Order retrievedOrder = testDao.getOrderById(date, 1);

        //Checking if data is equal
        assertEquals(testOrder1.getOrderDate(), retrievedOrder.getOrderDate(), "Checking order date");
        assertEquals(testOrder1.getOrderNumber(), retrievedOrder.getOrderNumber(), "Checking order number");
        assertEquals(testOrder1.getArea(), retrievedOrder.getArea(), "Checking Area");
        assertEquals(testOrder1.getMaterialCost(), retrievedOrder.getMaterialCost(), "Checking material cost");
        assertEquals(testOrder1.getLaborCost(), retrievedOrder.getLaborCost(), "Checking labor cost");
        assertEquals(testOrder1.getTotalTax(), retrievedOrder.getTotalTax(), "Checking total tax");
        assertEquals(testOrder1.getTotal(), retrievedOrder.getTotal(), "Checking grand total");
        assertEquals(testOrder1.getTax(), retrievedOrder.getTax(), "Checking tax object");
        assertEquals(testOrder1.getProduct(), retrievedOrder.getProduct(), "Checking product object");
    }

    @Test
    public void testGetAllOrdersByDate() throws Exception {
        //Creating / Adding test orders to Dao
        Order testOrder1 = createTestOrder1();   //date 04/02/2023
        Order testOrder2 = createTestOrder2();   //date 04/02/2023
        Order testOrder3 = createTestOrder3();   //date 03/23/2023

        //Creating test input
        LocalDate mockInputDate = LocalDate.parse("04/02/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        //Getting ORDERS BY DATE from DAO
        List<Order> ordersByDate = testDao.getAllOrdersByDate(mockInputDate);

        //Checking contents
        assertNotNull(ordersByDate, "List of orders must not be null.");
        assertEquals(2, ordersByDate.size(), "List of orders should have 2 orders.");
        assertTrue(ordersByDate.contains(testOrder1), "The list of orders should include testOrder1.");
        assertTrue(ordersByDate.contains(testOrder2), "The list of orders should include testOrder2.");
        assertFalse(ordersByDate.contains(testOrder3), "The list should not contain testOrder3, as it has a different date.");
    }

    @Test
    public void testGetAllOrders() throws Exception {
        //Creating / Adding test orders to Dao
        createTestOrder1();
        createTestOrder2();
        createTestOrder3();

        //Getting ALL ORDERS from DAO
        List<Order> all = testDao.getAllOrders();

        //Checking contents
        assertNotNull(all, "List of orders must not be null.");
        assertEquals(3, all.size(), "List of orders should have 3 orders.");
    }

    @Test
    public void testExportOrders() throws Exception {
        //Creating / Adding test orders to Dao
        createTestOrder1();
        createTestOrder2();
        createTestOrder3();

        //Exporting all orders
        testDao.exportAllOrders();

        Scanner sc = new Scanner(new BufferedReader(new FileReader("TestBackup/testbackupfile.txt")));

        //Skiping header line
        sc.nextLine();

        //Reading lines from file
        List<String> allLines = new ArrayList<>();
        if (!sc.hasNextLine()) {
            fail("Export File is not being written to");
        } else {
            while (sc.hasNextLine()) {
                String currentLine = sc.nextLine();
                allLines.add(currentLine);
            }
        }
        assertEquals(3, allLines.size(), "Backup file should contain 3 orders");
    }

    @Test
    public void testDeleteOrder() throws Exception {
        //Creating / Adding test orders to Dao
        Order testOrder1 = createTestOrder1();
        Order testOrder2 = createTestOrder2();
        List<Order> testList = testDao.getAllOrders();
        assertEquals(2, testList.size(), "All orders should only have 1 order");
        

        //Removing the first order
        Order removedOrder = testDao.deleteOrder(testOrder1.getOrderDate(), testOrder1.getOrderNumber());

        //Checking correct order was removed
        assertEquals(removedOrder, testOrder1, "The removed order should be testOrder1");

        //Getting all Orders
        List<Order> allOrders = testDao.getAllOrders();

        //Checking contents of allOrders list
        assertNotNull(allOrders, "All orders should not be null");
        assertEquals(1, allOrders.size(), "All orders should only have 1 order");
        assertFalse(allOrders.contains(testOrder1), "All orders should not include testName1");
        assertTrue(allOrders.contains(testOrder2), "All orders should still include testName2");

        //Removing the second order
        removedOrder = testDao.deleteOrder(testOrder2.getOrderDate(), testOrder2.getOrderNumber());

        //Checking correct Order was removed
        assertEquals(removedOrder, testOrder2, "The removed should be testOrder2");

        //Again, getting orders from DAO
        allOrders = testDao.getAllOrders();

        //Checking contents
        assertTrue(allOrders.isEmpty(), "The retrieved list should be empty");
        Order retrievedOrder = testDao.getOrderById(testOrder1.getOrderDate(), testOrder1.getOrderNumber());
        assertNull(retrievedOrder, "TestName1 was removed and should be null");
        retrievedOrder = testDao.getOrderById(testOrder2.getOrderDate(), testOrder2.getOrderNumber());
        assertNull(retrievedOrder, "testName2 was removed and should be null");
    }

    @Test
    public void testUpdateOrder() throws Exception {
        //Creating / Adding test order to Dao
        Order originalOrder = createTestOrder1();

        //Updating original order 
        Order updatedOrder = new Order();
        LocalDate sameDate = LocalDate.parse("04/02/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        updatedOrder.setOrderDate(sameDate);
        updatedOrder.setOrderNumber(1);
        updatedOrder.setCustomerName("updatedtestname1");
        updatedOrder.setArea(new BigDecimal("400.00"));
        updatedOrder.setMaterialCost(new BigDecimal("900.00"));
        updatedOrder.setLaborCost(new BigDecimal("840.00"));
        updatedOrder.setTotalTax(new BigDecimal("104.40"));
        updatedOrder.setTotal(new BigDecimal("1844.40"));

        Tax tax2 = new Tax();
        tax2.setStateAbbrevation("KY");
        //tax2.setStateName("Kentucky");
        tax2.setTaxRate(new BigDecimal("6.00"));
        updatedOrder.setTax(tax2);

        Product product2 = new Product();
        product2.setProductName("Carpet");
        product2.setCostPerSquareFoot(new BigDecimal("2.25"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("2.10"));
        updatedOrder.setProduct(product2);

        //Updating Order in dao
        Order newOrder = testDao.updateOrder(updatedOrder);

        //Getting order from DAO
        Order retrievedOrder = testDao.getOrderById(newOrder.getOrderDate(), newOrder.getOrderNumber());

        //Getting list
        List<Order> allOrders = testDao.getAllOrders();

        //Checking if original item is still in the list
        assertFalse(allOrders.contains(originalOrder), "All Orders should not include TestName1");
        assertTrue(allOrders.contains(retrievedOrder), "All Order should include UpdatedTestName1");
    }

    private Order createTestOrder1() throws Exception {
        Order testOrder1 = new Order();
        LocalDate date = LocalDate.parse("04/02/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testOrder1.setOrderDate(date);
        testOrder1.setOrderNumber(1);
        testOrder1.setCustomerName("testname1");
        testOrder1.setArea(new BigDecimal("600.00"));
        testOrder1.setMaterialCost(new BigDecimal("3090.00"));
        testOrder1.setLaborCost(new BigDecimal("2850.00"));
        testOrder1.setTotalTax(new BigDecimal("415.80"));
        testOrder1.setTotal(new BigDecimal("6355.80"));

        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        // tax.setStateName("Ohio");
        tax.setTaxRate(new BigDecimal("7.25"));
        testOrder1.setTax(tax);

        Product product = new Product();
        product.setProductName("Wood");
        product.setCostPerSquareFoot(new BigDecimal("5.15"));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        testOrder1.setProduct(product);

        return testDao.createOrder(testOrder1);
    }

    private Order createTestOrder2() throws Exception {
        Order testOrder2 = new Order();
        LocalDate date2 = LocalDate.parse("04/02/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testOrder2.setOrderDate(date2);
        testOrder2.setOrderNumber(2);
        testOrder2.setCustomerName("testname2");
        testOrder2.setArea(new BigDecimal("400.00"));
        testOrder2.setMaterialCost(new BigDecimal("900.00"));
        testOrder2.setLaborCost(new BigDecimal("840.00"));
        testOrder2.setTotalTax(new BigDecimal("104.40"));
        testOrder2.setTotal(new BigDecimal("1844.40"));

        Tax tax2 = new Tax();
        tax2.setStateAbbrevation("KY");
        //tax2.setStateName("Kentucky");
        tax2.setTaxRate(new BigDecimal("6.00"));
        testOrder2.setTax(tax2);

        Product product2 = new Product();
        product2.setProductName("Carpet");
        product2.setCostPerSquareFoot(new BigDecimal("2.25"));
        product2.setLaborCostPerSquareFoot(new BigDecimal("2.10"));
        testOrder2.setProduct(product2);

        return testDao.createOrder(testOrder2);

    }

    private Order createTestOrder3() throws Exception {
        Order testOrder3 = new Order();
        LocalDate date3 = LocalDate.parse("03/23/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testOrder3.setOrderDate(date3);
        testOrder3.setOrderNumber(3);
        testOrder3.setCustomerName("testname3");
        testOrder3.setArea(new BigDecimal("350.00"));
        testOrder3.setMaterialCost(new BigDecimal("1225.00"));
        testOrder3.setLaborCost(new BigDecimal("1452.50"));
        testOrder3.setTotalTax(new BigDecimal("669.38"));
        testOrder3.setTotal(new BigDecimal("3346.88"));

        Tax tax3 = new Tax();
        tax3.setStateAbbrevation("CA");
        // tax3.setStateName("California");
        tax3.setTaxRate(new BigDecimal("25.00"));
        testOrder3.setTax(tax3);

        Product product3 = new Product();
        product3.setProductName("Tile");
        product3.setCostPerSquareFoot(new BigDecimal("3.50"));
        product3.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        testOrder3.setProduct(product3);

        return testDao.createOrder(testOrder3);

    }
}
