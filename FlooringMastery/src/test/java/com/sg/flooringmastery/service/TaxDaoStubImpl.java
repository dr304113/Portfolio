/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.dto.Tax;
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
public class TaxDaoStubImpl implements TaxDao {

    Map<String, Tax> allTaxes = new HashMap<>();

    public TaxDaoStubImpl() {
        Tax tax1 = new Tax();
        tax1.setStateAbbrevation("TX");
        tax1.setStateName("Texas");
        tax1.setTaxRate(new BigDecimal("4.45").setScale(2, RoundingMode.HALF_UP));

        Tax tax2 = new Tax();
        tax2.setStateAbbrevation("WA");
        tax2.setStateName("Washington");
        tax2.setTaxRate(new BigDecimal("9.25").setScale(2, RoundingMode.HALF_UP));

        Tax tax3 = new Tax();
        tax3.setStateAbbrevation("KY");
        tax3.setStateName("Kentucky");
        tax3.setTaxRate(new BigDecimal("6.00").setScale(2, RoundingMode.HALF_UP));

        Tax tax4 = new Tax();
        tax4.setStateAbbrevation("CA");
        tax4.setStateName("California");
        tax4.setTaxRate(new BigDecimal("25.00").setScale(2, RoundingMode.HALF_UP));

        Tax tax5 = new Tax();
        tax5.setStateAbbrevation("OH");
        tax5.setStateName("Ohio");
        tax5.setTaxRate(new BigDecimal("7.25").setScale(2, RoundingMode.HALF_UP));
        allTaxes.put(tax1.getStateAbbreviation(), tax1);
        allTaxes.put(tax2.getStateAbbreviation(), tax2);
        allTaxes.put(tax3.getStateAbbreviation(), tax3);
        allTaxes.put(tax4.getStateAbbreviation(), tax4);
        allTaxes.put(tax5.getStateAbbreviation(), tax5);
    }

    @Override
    public List<Tax> getAllTaxes() throws FlooringPersistenceException {
        return new ArrayList<>(allTaxes.values());
    }

    @Override
    public Tax getTax(String stateAbbreviation) throws FlooringPersistenceException {
        return allTaxes.get(stateAbbreviation);
    }

}
