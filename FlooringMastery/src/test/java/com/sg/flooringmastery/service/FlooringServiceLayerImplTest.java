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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author dr304
 */
public class FlooringServiceLayerImplTest {

    private FlooringServiceLayer service;

    public FlooringServiceLayerImplTest() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("service", FlooringServiceLayer.class);
    }

    @Test
    public void testGetAllOrdersByDate() throws Exception {
        //Arrange --Create test clone
        Order testClone = new Order();
        testClone.setCustomerName("De Bugger");
        testClone.setOrderNumber(1);
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testClone.setOrderDate(ld);
        testClone.setArea(new BigDecimal("600").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        tax.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax.setStateName("Ohio");
        testClone.setTax(tax);
        Product product = new Product();
        product.setProductName("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        testClone.setProduct(product);

        //Act/Assert --size
        assertEquals(1, service.getOrdersByDate(ld).size(), "List should contain 1 order");
        //  --contents
        assertTrue(service.getOrdersByDate(ld).contains(testClone), "The one order should be De Bugger");
    }

    @Test
    public void testGetOrder() throws Exception {
        //Arrange --Create test clone
        Order testClone = new Order();
        testClone.setCustomerName("De Bugger");
        testClone.setOrderNumber(1);
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testClone.setOrderDate(ld);
        testClone.setArea(new BigDecimal("600").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        tax.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax.setStateName("Ohio");
        testClone.setTax(tax);
        Product product = new Product();
        product.setProductName("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        testClone.setProduct(product);

        //Act/Assert
        Order shouldBeDeBugger = service.getOrder(ld, 1);
        assertNotNull(shouldBeDeBugger, "Getting order num 1 should not be null");
        assertEquals(testClone, shouldBeDeBugger, "Order stored under date: " + ld + " and ordernumber: 1 should be De Bugger");
        try {
            service.getOrder(ld, 88);
            fail("NoOrderExistsException should have been thrown");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect exception was thrown");
        } catch (NoOrderExistsException e) {
            return;
        }
    }

    @Test
    public void testRemoveOrder() throws Exception {
        //Arrange --Create test clone
        Order testClone = new Order();
        testClone.setCustomerName("De Bugger");
        testClone.setOrderNumber(1);
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testClone.setOrderDate(ld);
        testClone.setArea(new BigDecimal("600").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        tax.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax.setStateName("Ohio");
        testClone.setTax(tax);
        Product product = new Product();
        product.setProductName("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        testClone.setProduct(product);

        //Act/Assert
        Order shouldBeDeBugger = service.removeOrder(testClone);
        assertNotNull(shouldBeDeBugger, "Removing testClone should not be null");
        assertEquals(testClone, shouldBeDeBugger, "Order removed should be De Bugger");
        try {
            Order nullOrder = new Order();
            Order shouldBeNull = service.removeOrder(nullOrder);
            assertNull(shouldBeNull, "Removing 'nullOrder' should be null");
            fail("No order exists, NoOrderExistException should have been thrown");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect exception was thrown.");
        } catch (NoOrderExistsException e) {
            return;
        }
    }

    @Test
    public void testValidAndNullOrder() throws Exception {
        //Creating test clone
        Order testClone = new Order();
        testClone.setCustomerName("De Bugger");
        testClone.setOrderNumber(1);
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testClone.setOrderDate(ld);
        testClone.setArea(new BigDecimal("600").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        tax.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax.setStateName("Ohio");
        testClone.setTax(tax);
        Product product = new Product();
        product.setProductName("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        testClone.setProduct(product);

        //Valid order
        try {
            service.validateAndCalculateTempOrder(testClone);
        } catch (FlooringPersistenceException | FlooringDataValidationException e) {
            fail("Order valid, no exception should be thrown");
        }

        //Null order
        Order nullOrder = new Order();
        try {
            service.validateAndCalculateTempOrder(nullOrder);
            fail("Validation exception should have been thrown");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect exception was thrown");
        } catch (FlooringDataValidationException e) {
            return;
        }
    }

    @Test
    public void testOrderInvalidName() throws Exception {

        //Array of possible invalid characters
        String[] invalidCharacters = {"!", "?", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "-", "+", "=",
            ">", "<", ":", ";", "/", "|", "[", "]", "{", "}", "{", "*", "\""};

        //Creating order with invalid characters in name
        Order testClone = new Order();
        String name = "De Bugger";
        testClone.setCustomerName(name);
        testClone.setOrderNumber(1);
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testClone.setOrderDate(ld);
        testClone.setArea(new BigDecimal("600").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        tax.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax.setStateName("Ohio");
        testClone.setTax(tax);
        Product product = new Product();
        product.setProductName("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        testClone.setProduct(product);

        //Checking each character
        for (String character : invalidCharacters) {
            testClone.setCustomerName(name + character);

            try {
                service.validateAndCalculateTempOrder(testClone);
                fail("Exception should have been thrown due to invalid character in name");
            } catch (FlooringPersistenceException e) {
                fail("Incorrect exception thrown");
            } catch (FlooringDataValidationException e) {
            }
        }
    }

    @Test
    public void testOrderInvalidProduct() {

        //Creating test clone with invalid product
        Order testClone = new Order();
        testClone.setCustomerName("De Bugger");
        testClone.setOrderNumber(1);
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testClone.setOrderDate(ld);
        testClone.setArea(new BigDecimal("600").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        tax.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax.setStateName("Ohio");
        testClone.setTax(tax);
        Product product = new Product();
        product.setProductName("invalidProduct"); //invalid product name
        product.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        testClone.setProduct(product);

        try {
            service.validateAndCalculateTempOrder(testClone);
            fail("Validation exception should have been thrown, invalid product");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect Exception thrown");
        } catch (FlooringDataValidationException e) {
            return;
        }

    }

    @Test
    public void testOrderInvalidState() throws Exception {
        //Creating order with invalid state 
        Order testClone = new Order();
        testClone.setCustomerName("De Bugger");
        testClone.setOrderNumber(1);
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testClone.setOrderDate(ld);
        testClone.setArea(new BigDecimal("600").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("IS"); //Invalid state Abbreviation(key for Tax map)
        tax.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax.setStateName("Ohio");
        testClone.setTax(tax);
        Product product = new Product();
        product.setProductName("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        testClone.setProduct(product);

        try {
            service.validateAndCalculateTempOrder(testClone);
            fail("Validation exception should have been thrown");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect exception thrown");
        } catch (FlooringDataValidationException e) {
            return;
        }
    }

    @Test
    public void testOrderInvalidArea() throws Exception {
        //Creating test clone
        Order testClone = new Order();
        testClone.setCustomerName("De Bugger");
        testClone.setOrderNumber(1);
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testClone.setOrderDate(ld);
        testClone.setArea(new BigDecimal("100").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        tax.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax.setStateName("Ohio");
        testClone.setTax(tax);
        Product product = new Product();
        product.setProductName("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        testClone.setProduct(product);

        //Testing valid area
        try {
            service.validateAndCalculateTempOrder(testClone);
        } catch (FlooringPersistenceException | FlooringDataValidationException e) {
            fail("No Exception should be thrown, order area is valid");
        }

        //Testing invalid area
        testClone.setArea(new BigDecimal("99"));
        try {
            service.validateAndCalculateTempOrder(testClone);
            service.addOrder(testClone);
            fail("Validation exception should have been thrown, area invalid");
        } catch (FlooringPersistenceException e) {
            fail("Incorrect exception waas thrown");
        } catch (FlooringDataValidationException e) {
            return;
        }
    }

    @Test
    public void testGetNextOrderNum() throws Exception {
        //All new Order numbers start at zero before validate method runs and assigns next available

        //Creating test clone w/ no order num
        Order testClone = new Order();
        testClone.setCustomerName("De Bugger");
        testClone.setOrderNumber(0); //OrderNumber = 0
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testClone.setOrderDate(ld);
        testClone.setArea(new BigDecimal("400").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        tax.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax.setStateName("Ohio");
        testClone.setTax(tax);
        Product product = new Product();
        product.setProductName("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        testClone.setProduct(product);

        //Order number should be 2 after validate method runs because
        //there is already an order #1 in OrderDaoStub
        service.validateAndCalculateTempOrder(testClone);

        assertEquals(2, testClone.getOrderNumber(), "Order number should be 2");

        //Testing edit case --Order number should stay the same when editing an existing order
        testClone.setOrderNumber(5);
        service.validateAndCalculateTempOrder(testClone);

        assertEquals(5, testClone.getOrderNumber(), "Order number should still be 5 after any changes");
    }

    @Test
    public void testCalculateDerivedOrderFields() throws Exception {
        //Testing calculated fields of Order that need calculation
        //--laborCost, MaterialCost, totalTax, total

        Order testClone = new Order();
        testClone.setCustomerName("De Bugger");
        testClone.setOrderNumber(1);
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testClone.setOrderDate(ld);
        testClone.setArea(new BigDecimal("400").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        testClone.setTax(tax);
        Product product = new Product();
        product.setProductName("Tile");
        testClone.setProduct(product);

        service.validateAndCalculateTempOrder(testClone);

        BigDecimal expectedMaterialCost = new BigDecimal("1400.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedLaborCost = new BigDecimal("1660.00").setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedTotalTax = new BigDecimal("214.20").setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedTotal = new BigDecimal("3274.20").setScale(2, RoundingMode.HALF_UP);

        assertTrue(expectedMaterialCost.compareTo(testClone.getMaterialCost()) == 0, "Checking Material Cost");
        assertTrue(expectedLaborCost.compareTo(testClone.getLaborCost()) == 0, "Checking labor cost");
        assertTrue(expectedTotalTax.compareTo(testClone.getTotalTax()) == 0, "Checking total tax");
        assertTrue(expectedTotal.compareTo(testClone.getTotal()) == 0, "Checking grand total");
    }

    @Test
    public void testGetAllStates() throws Exception {

        //Getting all states
        List<Tax> allStates = service.getAllStates();

        //Checking contents
        assertNotNull(allStates, "List of states must not be null.");
        assertEquals(5, allStates.size(), "List of states should have 5 states.");

    }

    @Test
    public void testGetAllProducts() throws Exception {

        //Getting all Products
        List<Product> allProducts = service.getAllProducts();

        //Checking contents
        assertNotNull(allProducts, "List of products must not be null");
        assertEquals(4, allProducts.size(), "List of products should have 4 products");
    }

    @Test
    public void testEditOrder() throws Exception {
        //Creating test clone
        Order testClone = new Order();
        testClone.setCustomerName("De Bugger");
        testClone.setOrderNumber(1);
        LocalDate ld = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        testClone.setOrderDate(ld);
        testClone.setArea(new BigDecimal("600").setScale(2, RoundingMode.HALF_UP));
        Tax tax = new Tax();
        tax.setStateAbbrevation("OH");
        tax.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax.setStateName("Ohio");
        testClone.setTax(tax);
        Product product = new Product();
        product.setProductName("Tile");
        product.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        testClone.setProduct(product);

        //Created an edited version of the test clone already in stubbed dao memory
        Order editedTestClone = new Order();
        editedTestClone.setCustomerName("Mr. Bugger");  //Name changed
        editedTestClone.setOrderNumber(1);
        LocalDate ld2 = LocalDate.parse("04/08/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        editedTestClone.setOrderDate(ld2);
        editedTestClone.setArea(new BigDecimal("600").setScale(2, RoundingMode.HALF_UP));
        Tax tax2 = new Tax();
        tax2.setStateAbbrevation("OH");
        tax2.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        tax2.setStateName("Ohio");
        editedTestClone.setTax(tax2);
        Product product2 = new Product();
        product2.setProductName("Tile");
        product2.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product2.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        editedTestClone.setProduct(product2);

        //Act/Assert
        Order newOrder = service.editOrder(editedTestClone);

        //Making sure newOrder is not null
        assertNotNull(newOrder, "the edited order should not be null");

        //Making sure all fields except for the name are still the same
        assertEquals(newOrder.getOrderDate(), testClone.getOrderDate(), "Checking order date");
        assertEquals(newOrder.getOrderNumber(), testClone.getOrderNumber(), "Checking order number");
        assertEquals(newOrder.getArea(), testClone.getArea(), "Checking area");
        assertEquals(newOrder.getTax(), testClone.getTax(), "Checking tax");
        assertEquals(newOrder.getProduct(), testClone.getProduct(), "Checking product");

        //Now checking names are not equal
        assertFalse(newOrder.getCustomerName().equals(testClone.getCustomerName()), "Name should have changed to Mr.Bugger");

    }

}
