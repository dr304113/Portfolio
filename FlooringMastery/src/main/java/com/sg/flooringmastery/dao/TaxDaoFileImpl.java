/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author dr304
 */
public class TaxDaoFileImpl implements TaxDao {

    Map<String, Tax> allTaxes;
    private final String TAX_FILE;
    private final String DELIMITER;

    public TaxDaoFileImpl() {
        allTaxes = new HashMap<>();
        TAX_FILE = "Data/Taxes.txt";
        DELIMITER = ",";
    }

    @Override
    public List<Tax> getAllTaxes() throws FlooringPersistenceException {
        loadTaxes();
        return new ArrayList<>(allTaxes.values());
    }

    @Override
    public Tax getTax(String stateAbbreviation) throws FlooringPersistenceException {
        loadTaxes();
        return allTaxes.get(stateAbbreviation);
    }

    //Loading from txt file-
    private void loadTaxes() throws FlooringPersistenceException {
        Scanner sc;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Yikes! Could not load tax data into memory");
        }
        //Skipping header line
        sc.nextLine();

        //Reading file line by line, splitting into chunks at delimiter, assigning to Tax Object
        while (sc.hasNextLine()) {
            String currentLine = sc.nextLine();
            String[] taxTokens = currentLine.split(DELIMITER);
            Tax taxFromFile = new Tax();
            taxFromFile.setStateAbbrevation(taxTokens[0]);
            taxFromFile.setStateName(taxTokens[1]);
            BigDecimal rate = new BigDecimal(taxTokens[2]).setScale(2, RoundingMode.HALF_UP);
            taxFromFile.setTaxRate(rate);

            //loading up the HashMap with Product Objects
            allTaxes.put(taxFromFile.getStateAbbreviation(), taxFromFile);
        }

        //Clean up
        sc.close();
    }

}
