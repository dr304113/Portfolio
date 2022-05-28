/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dao.ProductDao;
import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dr304
 */
public class ProductDaoStubImpl implements ProductDao {

    Map<String, Product> allProducts = new HashMap<>();

    public ProductDaoStubImpl() {
        Product product1 = new Product();
        product1.setProductName("Carpet");
        product1.setCostPerSquareFoot(new BigDecimal("2.25").setScale(2, RoundingMode.HALF_UP));
        product1.setLaborCostPerSquareFoot(new BigDecimal("2.10").setScale(2, RoundingMode.HALF_UP));

        Product product2 = new Product();
        product2.setProductName("Laminate");
        product2.setCostPerSquareFoot(new BigDecimal("1.75").setScale(2, RoundingMode.HALF_UP));
        product2.setLaborCostPerSquareFoot(new BigDecimal("2.10").setScale(2, RoundingMode.HALF_UP));

        Product product3 = new Product();
        product3.setProductName("Tile");
        product3.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        product3.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));

        Product product4 = new Product();
        product4.setProductName("Wood");
        product4.setCostPerSquareFoot(new BigDecimal("5.15").setScale(2, RoundingMode.HALF_UP));
        product4.setLaborCostPerSquareFoot(new BigDecimal("4.75").setScale(2, RoundingMode.HALF_UP));
        allProducts.put(product1.getProductName(), product1);
        allProducts.put(product2.getProductName(), product2);
        allProducts.put(product3.getProductName(), product3);
        allProducts.put(product4.getProductName(), product4);

    }

    @Override
    public List<Product> getAllProducts() throws FlooringPersistenceException {
        return new ArrayList<>(allProducts.values());
    }

    @Override
    public Product getProductByType(String productType) throws FlooringPersistenceException {
        return allProducts.get(productType);
    }

}
