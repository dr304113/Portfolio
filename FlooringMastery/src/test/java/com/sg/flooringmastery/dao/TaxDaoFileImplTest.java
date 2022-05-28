/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author dr304
 */
public class TaxDaoFileImplTest {

    TaxDao testDao;

    public TaxDaoFileImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("taxDao", TaxDao.class);
    }

    @Test
    public void testGetTaxAndGetAllTaxes() throws Exception {

//        These are the objects that the txt file contains:
//        TX,Texas,4.45
//        WA,Washington,9.25
//        KY,Kentucky,6.00
//        CA,California,25.00
//        OH,Ohio,7.25
        //Creating test objects
        Tax taxTexas = new Tax();
        String texasAbbreviation = "TX";
        taxTexas.setStateAbbrevation(texasAbbreviation);
        taxTexas.setStateName("Texas");
        taxTexas.setTaxRate(new BigDecimal("4.45"));

        Tax taxWashington = new Tax();
        String washingtonAbbreviation = "WA";
        taxWashington.setStateAbbrevation(washingtonAbbreviation);
        taxWashington.setStateName("Washington");
        taxWashington.setTaxRate(new BigDecimal("9.25"));

        Tax taxKentucky = new Tax();
        String kentuckyAbbreviation = "KY";
        taxKentucky.setStateAbbrevation(kentuckyAbbreviation);
        taxKentucky.setStateName("Kentucky");
        taxKentucky.setTaxRate(new BigDecimal("6.00"));

        Tax taxCalifornia = new Tax();
        String californiaAbbreviation = "CA";
        taxCalifornia.setStateAbbrevation(californiaAbbreviation);
        taxCalifornia.setStateName("California");
        taxCalifornia.setTaxRate(new BigDecimal("25.00"));

        Tax taxOhio = new Tax();
        String ohioAbbreviation = "OH";
        taxOhio.setStateAbbrevation(ohioAbbreviation);
        taxOhio.setStateName("Ohio");
        taxOhio.setTaxRate(new BigDecimal("7.25"));

        //pulling out objects and comparing to test objects
        Tax retrievedTax = testDao.getTax(texasAbbreviation);
        assertEquals(retrievedTax, taxTexas, "Objects should be equal");

        retrievedTax = testDao.getTax(washingtonAbbreviation);
        assertEquals(retrievedTax, taxWashington, "Objects should be equal");

        retrievedTax = testDao.getTax(kentuckyAbbreviation);
        assertEquals(retrievedTax, taxKentucky, "Objects should be equal");

        retrievedTax = testDao.getTax(californiaAbbreviation);
        assertEquals(retrievedTax, taxCalifornia, "Objects should be equal");

        retrievedTax = testDao.getTax(ohioAbbreviation);
        assertEquals(retrievedTax, taxOhio, "Objects should be equal");

    }

    @Test
    public void getAllTaxes() throws Exception {
        //Getting all taxes from dao
        List<Tax> allTaxes = testDao.getAllTaxes();

        //Checking contents
        assertNotNull(allTaxes, "List of taxes must not be null.");
        assertEquals(5, allTaxes.size(), "List of taxes should have 5 tax objects.");
    }

}
