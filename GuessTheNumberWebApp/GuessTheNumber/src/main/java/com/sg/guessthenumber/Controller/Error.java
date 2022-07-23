package com.sg.guessthenumber.Controller;

import java.time.LocalDateTime;

/**
 *
 * @author dr304
 */
public class Error {

    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
