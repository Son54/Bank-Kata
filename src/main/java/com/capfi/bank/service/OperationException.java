package com.capfi.bank.service;

import java.math.BigDecimal;

public class OperationException extends Exception {
    public OperationException(String errorMessage) {
        super(errorMessage);
    }
}
