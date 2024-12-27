package com.capfi.bank.service;

import java.math.BigDecimal;

public class NotEnoughBalanceException extends OperationException {
    public NotEnoughBalanceException(BigDecimal balance) {
        super("You don't have enough balance in your account (" + balance.toString() + ")");
    }
}
