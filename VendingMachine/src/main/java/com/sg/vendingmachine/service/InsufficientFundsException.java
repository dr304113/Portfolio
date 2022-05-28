/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/06/22
 * purpose: M3-Summative - Vending Machine
 */
package com.sg.vendingmachine.service;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
    }   

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }

}
