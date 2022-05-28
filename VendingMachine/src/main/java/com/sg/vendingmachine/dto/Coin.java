/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/06/22
 * purpose: M3-Summative - Vending Machine
 */
package com.sg.vendingmachine.dto;

import java.math.BigDecimal;

public enum Coin {

    PENNY(new BigDecimal("0.01")),
    NICKEL(new BigDecimal("0.05")),
    DIME(new BigDecimal("0.10")),
    QUARTER(new BigDecimal("0.25")),
    DOLLAR(new BigDecimal("1.00"));

    private BigDecimal value;

    private Coin(BigDecimal v) {
        value = v;
    }

    public BigDecimal getCoinValue() {
        return value;
    }
    
}
