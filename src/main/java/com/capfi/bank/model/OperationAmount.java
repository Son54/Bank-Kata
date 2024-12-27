package com.capfi.bank.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OperationAmount(
    @NotNull(message = "Amount must not be null")
    @Positive(message = "Amount must be positive")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "Amount must be a valid number")
    BigDecimal amount
) {
}
