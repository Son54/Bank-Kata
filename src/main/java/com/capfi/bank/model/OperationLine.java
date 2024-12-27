package com.capfi.bank.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Operation Line object model returned by the restAPI
 */
public record OperationLine(
    Date date,
    OperationType operationType,
    BigDecimal operationAmount,
    BigDecimal balance
) {
}
