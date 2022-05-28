/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/06/22
 * purpose: M3-Summative - Vending Machine
 */
package com.sg.vendingmachine.dto;

import static com.sg.vendingmachine.dto.Coin.DIME;
import static com.sg.vendingmachine.dto.Coin.NICKEL;
import static com.sg.vendingmachine.dto.Coin.PENNY;
import static com.sg.vendingmachine.dto.Coin.QUARTER;
import static com.sg.vendingmachine.dto.Coin.DOLLAR;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Change {

    private int pennies;
    private int nickels;
    private int dimes;
    private int quarters;
    private BigDecimal amount;
    

    public Change(int dollars, int quarters, int dimes, int nickels, int pennies) {
        BigDecimal dollarValue = DOLLAR.getCoinValue().multiply(new BigDecimal(dollars));
        BigDecimal quartersValue = QUARTER.getCoinValue().multiply(new BigDecimal(quarters));
        BigDecimal dimesValue = DIME.getCoinValue().multiply(new BigDecimal(dimes));
        BigDecimal nickelsValue = NICKEL.getCoinValue().multiply(new BigDecimal(nickels));
        BigDecimal penniesValue = PENNY.getCoinValue().multiply(new BigDecimal(pennies));
        this.amount = dollarValue.add(quartersValue).add(dimesValue).add(nickelsValue).add(penniesValue)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public Change(BigDecimal amt) {
        this.amount = amt.setScale(2, RoundingMode.HALF_UP);
        BigDecimal[] qr;
        if (amt.compareTo(new BigDecimal("0")) > 0) {
            if (amt.compareTo(QUARTER.getCoinValue()) >= 0) {
                qr = amt.divideAndRemainder(QUARTER.getCoinValue());
                this.quarters = qr[0].intValue();
                amt = qr[1];
            }
            if (amt.compareTo(DIME.getCoinValue()) >= 0) {
                qr = amt.divideAndRemainder(DIME.getCoinValue());
                this.dimes = qr[0].intValue();
                amt = qr[1];
            }
            if (amt.compareTo(NICKEL.getCoinValue()) >= 0) {
                qr = amt.divideAndRemainder(NICKEL.getCoinValue());
                this.nickels = qr[0].intValue();
                amt = qr[1];
            }
            if (amt.compareTo(PENNY.getCoinValue()) >= 0) {
                qr = amt.divideAndRemainder(PENNY.getCoinValue());
                this.pennies = qr[0].intValue();
            }
        }
    }

    public int getPennies() {
        return pennies;
    }

    public int getNickels() {
        return nickels;
    }

    public int getDimes() {
        return dimes;
    }

    public int getQuarters() {
        return quarters;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.pennies;
        hash = 53 * hash + this.nickels;
        hash = 53 * hash + this.dimes;
        hash = 53 * hash + this.quarters;
        hash = 53 * hash + Objects.hashCode(this.amount);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Change other = (Change) obj;
        if (this.pennies != other.pennies) {
            return false;
        }
        if (this.nickels != other.nickels) {
            return false;
        }
        if (this.dimes != other.dimes) {
            return false;
        }
        if (this.quarters != other.quarters) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Change{" + "pennies=" + pennies + ", nickels=" + nickels + ", dimes=" + dimes + ", quarters=" + quarters + ", AMOUNT=" + amount + '}';
    }

}
