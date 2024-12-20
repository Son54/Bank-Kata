package com.capfi.bank.service;

public class NoBalanceException extends RuntimeException {
    public NoBalanceException() {
        super("You don't have any money in the bank account");
    }
}
