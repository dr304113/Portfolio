/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.shared.utils.io.FileUtils;

/**
 *
 * @author dr304
 */
public class OrderDaoFileImpl implements OrderDao {

    Map<LocalDate, Map<Integer, Order>> allOrders = new HashMap<>();
    private final String EXPORT_FILE;
    private final String ORDER_DIRECTORY;
    private final String DELIMITER = ",";

    public OrderDaoFileImpl() {
        EXPORT_FILE = "Backup/DataExport.txt";
        ORDER_DIRECTORY = "Orders";
    }

    public OrderDaoFileImpl(String exportTextFile, String orderDirectory) {
        allOrders = new HashMap<>();
        EXPORT_FILE = exportTextFile;
        ORDER_DIRECTORY = orderDirectory;
    }

    @Override
    public Order createOrder(Order order) throws FlooringPersistenceException {
        loadOrdersByDate();
        //if order date does not already exists, add date as key, then put order in new Map as value
        if (allOrders.get(order.getOrderDate()) == null) {
            Map<Integer, Order> map = new HashMap<>();
            map.put(order.getOrderNumber(), order);
            allOrders.put(order.getOrderDate(), map);
            //if date already exists, pull out its Map, then add Order to that map
        } else {
            allOrders.get(order.getOrderDate()).put(order.getOrderNumber(), order);
        }
        writeOrdersByDate();
        return order;
    }

    @Override //Single order by order number
    public Order getOrderById(LocalDate date, int orderNumber) throws FlooringPersistenceException {
        loadOrdersByDate();
        if (allOrders.get(date) == null) {
            return null;
        }
        return allOrders.get(date).get(orderNumber);
    }

    @Override //All orders by date
    public List<Order> getAllOrdersByDate(LocalDate date) throws FlooringPersistenceException {
        loadOrdersByDate();
        if (allOrders.get(date) == null) {
            return null;
        }
        Map<Integer, Order> map = allOrders.get(date);
        return new ArrayList<>(map.values());
    }

    @Override   //All orders
    public List<Order> getAllOrders() throws FlooringPersistenceException {
        loadOrdersByDate();
        Map<LocalDate, Map<Integer, Order>> masterList = allOrders;
        List<Order> orders = new ArrayList<>();
        for (LocalDate date : masterList.keySet()) {
            Map<Integer, Order> subList = masterList.get(date);
            for (Order order : subList.values()) {
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public Order updateOrder(Order order) throws FlooringPersistenceException {
        loadOrdersByDate();
        allOrders.get(order.getOrderDate()).remove(order.getOrderNumber());
        allOrders.get(order.getOrderDate()).put(order.getOrderNumber(), order);
        writeOrdersByDate();
        return order;
    }

    @Override
    public Order deleteOrder(LocalDate date, int orderNumber) throws FlooringPersistenceException {
        loadOrdersByDate();
        Order removedOrder = allOrders.get(date).remove(orderNumber);
        writeOrdersByDate();
        return removedOrder;
    }

    @Override
    public void exportAllOrders() throws FlooringPersistenceException {
        loadOrdersByDate();
        writeAllOrdersToBackUp();
    }

    //Loading from txt file(s)     
    private void loadOrdersByDate() throws FlooringPersistenceException {
        Scanner sc;
        Order currentOrder;
        allOrders.clear();

        //Iterating through Orders folder
        File dir = new File(ORDER_DIRECTORY);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File orderFile : directoryListing) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(orderFile));
                    sc = new Scanner(reader);
                } catch (FileNotFoundException e) {
                    throw new FlooringPersistenceException("Yikes! Could not load order data into memory");
                }
                //Splitting file name at delimiter to get date
                String[] date = orderFile.getName().split("_|\\.");
                String dateAsString = date[1];

                //Converting date to LocalDate
                LocalDate dateAsLd = LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("MMddyyyy"));

                //Skipping header line
                sc.nextLine();

                //Reading file line by line, splitting into chunks at delimiter, assigning to Order Object
                while (sc.hasNextLine()) {
                    String currentLine = sc.nextLine();
                    currentOrder = unmarshallOrders(currentLine, dateAsLd);

                    //Storing
                    if (allOrders.get(dateAsLd) == null) {
                        Map<Integer, Order> tempMap = new HashMap<>();
                        tempMap.put(currentOrder.getOrderNumber(), currentOrder);
                        allOrders.put(dateAsLd, tempMap);
                    } else {
                        allOrders.get(dateAsLd).put(currentOrder.getOrderNumber(), currentOrder);
                    }
                }
                //Clean up
                sc.close();
            }
        }
    }

    //Unmarshall for loading into memory
    private Order unmarshallOrders(String orderAsText, LocalDate dateAsLd) throws FlooringPersistenceException {
        String[] orderTokens = orderAsText.split(DELIMITER);
        Order orderFromFile = new Order();
        orderFromFile.setOrderNumber(Integer.parseInt(orderTokens[0]));
        String name = orderTokens[1];
        if (name.contains("!")) {
            name = name.replace("!", ".");
        }
        if (name.contains("?")) {
            name = name.replace("?", ",");
        }
        orderFromFile.setCustomerName(name);
        Tax tax = new Tax();
        tax.setStateAbbrevation(orderTokens[2]);
        tax.setTaxRate(new BigDecimal(orderTokens[3]).setScale(2, RoundingMode.HALF_UP));
        //tax.setStateName(tDao.getTax(orderTokens[2]).getStateName());
        orderFromFile.setTax(tax);
        Product product = new Product();
        product.setProductName(orderTokens[4]);
        orderFromFile.setArea(new BigDecimal(orderTokens[5]).setScale(2, RoundingMode.HALF_UP));
        product.setCostPerSquareFoot(new BigDecimal(orderTokens[6]).setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal(orderTokens[7]).setScale(2, RoundingMode.HALF_UP));
        orderFromFile.setProduct(product);
        orderFromFile.setMaterialCost(new BigDecimal(orderTokens[8]).setScale(2, RoundingMode.HALF_UP));
        orderFromFile.setLaborCost(new BigDecimal(orderTokens[9]).setScale(2, RoundingMode.HALF_UP));
        orderFromFile.setTotalTax(new BigDecimal(orderTokens[10]).setScale(2, RoundingMode.HALF_UP));
        orderFromFile.setTotal(new BigDecimal(orderTokens[11]).setScale(2, RoundingMode.HALF_UP));
        orderFromFile.setOrderDate(dateAsLd);
        return orderFromFile;

    }

    //Writing to text file(s)
    private void writeOrdersByDate() throws FlooringPersistenceException {
        PrintWriter out;
        Scanner sc;
        String orderAsText;
        Map<LocalDate, Map<Integer, Order>> masterList = this.allOrders;
        File orderDir = new File(ORDER_DIRECTORY);
        try {
            FileUtils.cleanDirectory(orderDir);
        } catch (IOException ex) {
            Logger.getLogger(OrderDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (LocalDate date : masterList.keySet()) {

            //Formatting date of each key to include in file name
            String[] dateChunks = date.format(DateTimeFormatter.ISO_DATE).split("-");
            String marshalledDate = dateChunks[1] + dateChunks[2] + dateChunks[0];

            File fileToWriteTo = new File(ORDER_DIRECTORY + "/Orders_" + marshalledDate + ".txt");

            try {
                out = new PrintWriter(new FileWriter(fileToWriteTo));
            } catch (IOException e) {
                throw new FlooringPersistenceException("Yikes! Could not write data to file");
            }

            //Using Scanner/FileReader to see if File already has a header in line 1
            try {
                sc = new Scanner(new BufferedReader(new FileReader(fileToWriteTo)));
            } catch (FileNotFoundException e) {
                throw new FlooringPersistenceException("Yikes! Could not Find file to read from");
            }

            //Writing header to new file
            if (!sc.hasNext()) {
                out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
            }

            //Getting list from nested Map
            Map<Integer, Order> subList = masterList.get(date);

            //Iterating through values of nested Map
            for (Order currentOrder : subList.values()) {

                //Marshalling order into readable text
                orderAsText = marshallOrders(currentOrder);

                //writing to above specified txt file
                out.println(orderAsText);

                //Force write 
                out.flush();
            }
            //Cleaning up
            out.close();
        }
    }
    //Marshall Order object into readable text

    private String marshallOrders(Order anOrder) {
        String name = anOrder.getCustomerName();
        if (name.contains(".")) {
            name = name.replace(".", "!");
        }
        if (name.contains(",")) {
            name = name.replace(",", "?");
        }
        String orderAsText = anOrder.getOrderNumber() + DELIMITER
                + name + DELIMITER
                + anOrder.getTax().getStateAbbreviation() + DELIMITER
                + anOrder.getTax().getTaxRate().setScale(2, RoundingMode.HALF_UP) + DELIMITER
                + anOrder.getProduct().getProductName() + DELIMITER
                + anOrder.getArea().setScale(2, RoundingMode.HALF_UP)
                + DELIMITER + anOrder.getProduct().getCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP)
                + DELIMITER + anOrder.getProduct().getLaborCostPerSquareFoot().setScale(2, RoundingMode.HALF_UP)
                + DELIMITER + anOrder.getMaterialCost().setScale(2, RoundingMode.HALF_UP)
                + DELIMITER + anOrder.getLaborCost().setScale(2, RoundingMode.HALF_UP)
                + DELIMITER + anOrder.getTotalTax().setScale(2, RoundingMode.HALF_UP)
                + DELIMITER + anOrder.getTotal().setScale(2, RoundingMode.HALF_UP);
        return orderAsText;
    }

    private void writeAllOrdersToBackUp() throws FlooringPersistenceException {
        PrintWriter out;
        String orderAsText;
        File exportFile = new File(EXPORT_FILE);
        Map<LocalDate, Map<Integer, Order>> masterList = this.allOrders;

        //Deleting old file
        if (exportFile.exists()) {
            exportFile.delete();
        }

        try {
            out = new PrintWriter(new FileWriter(exportFile));
        } catch (IOException e) {
            throw new FlooringPersistenceException("Yikes! Could not write data to Backup file");
        }

        //Writing header to new file
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,Date");

        //Getting nested Map from parent Map
        for (Map orderMap : masterList.values()) {
            Map<Integer, Order> subList = orderMap;

            //Iterating through values of nested Map
            for (Order currentOrder : subList.values()) {

                //Marshalling order into readable text
                orderAsText = marshallOrders(currentOrder);

                //Getting order date to write to end of each line
                String orderDate = currentOrder.getOrderDate().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

                //writing to above specified txt file
                out.println(orderAsText + DELIMITER + orderDate);

                //Force write 
                out.flush();
            }
        }
        //Cleaning up
        out.close();
    }

}
