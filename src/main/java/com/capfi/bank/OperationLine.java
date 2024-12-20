package com.capfi.bank;

import java.math.BigDecimal;
import java.util.Date;

public record OperationLine(
    Date date,
    OperationType operationType,
    BigDecimal operationAmount,
    BigDecimal balance
) {
}
