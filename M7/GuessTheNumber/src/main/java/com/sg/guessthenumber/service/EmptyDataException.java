package com.sg.guessthenumber.service;

/**
 *
 * @author dr304
 */
public class EmptyDataException extends Exception {

    public EmptyDataException(String message) {
        super(message);
    }

    public EmptyDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
