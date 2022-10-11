package com.forex.conversion.exception;

public class ForexConversionException extends RuntimeException {

    public ForexConversionException(String message) {
        super(message);
    }

    public ForexConversionException(String message, Exception exception) {
        super(message, exception);
    }

}
