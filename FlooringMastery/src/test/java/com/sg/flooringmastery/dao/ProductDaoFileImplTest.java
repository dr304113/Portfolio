/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
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
public class ProductDaoFileImplTest {

    ProductDao testDao;

    public ProductDaoFileImplTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("productDao", ProductDao.class);
    }

    @Test
    public void testGetProductByType() throws Exception {

//        These are the objects that the txt file contains:
//        Carpet,2.25,2.10
//        Laminate,1.75,2.10
//        Tile,3.50,4.15
//        Wood,5.15,4.75
        //Creating test objects
        Product productCarpet = new Product();
        String carpetType = "Carpet";
        productCarpet.setProductName(carpetType);
        productCarpet.setCostPerSquareFoot(new BigDecimal("2.25"));
        productCarpet.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        Product productLaminate = new Product();
        String laminateType = "Laminate";
        productLaminate.setProductName(laminateType);
        productLaminate.setCostPerSquareFoot(new BigDecimal("1.75"));
        productLaminate.setLaborCostPerSquareFoot(new BigDecimal("2.10"));

        Product productTile = new Product();
        String tileType = "Tile";
        productTile.setProductName(tileType);
        productTile.setCostPerSquareFoot(new BigDecimal("3.50"));
        productTile.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        Product productWood = new Product();
        String woodType = "Wood";
        productWood.setProductName(woodType);
        productWood.setCostPerSquareFoot(new BigDecimal("5.15"));
        productWood.setLaborCostPerSquareFoot(new BigDecimal("4.75"));

        //pulling out objects and comparing to test objects
        Product retrievedProduct = testDao.getProductByType(carpetType);
        assertEquals(retrievedProduct, productCarpet, "Objects should be equal");

        retrievedProduct = testDao.getProductByType(laminateType);
        assertEquals(retrievedProduct, productLaminate, "Objects should be equal");

        retrievedProduct = testDao.getProductByType(tileType);
        assertEquals(retrievedProduct, productTile, "Objects should be equal");

        retrievedProduct = testDao.getProductByType(woodType);
        assertEquals(retrievedProduct, productWood, "Objects should be equal");

    }

    @Test
    public void testGetAllProducts() throws Exception {
        //Getting all products from dao
        List<Product> allProducts = testDao.getAllProducts();

        //Checking contents
        assertNotNull(allProducts, "List of products must not be null.");
        assertEquals(4, allProducts.size(), "List of products should have 4 product objects.");
    }

}
