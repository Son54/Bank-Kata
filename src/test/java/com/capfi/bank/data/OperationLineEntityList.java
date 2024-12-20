package com.capfi.bank.data;

import com.capfi.bank.OperationType;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class OperationLineEntityList {

    public static OperationLineEntity firstOperation = new OperationLineEntity(
            1L,
            DateUtils.addDays(new Date(), -2),
            OperationType.DEPOSIT.name(),
            new BigDecimal(500),
            new BigDecimal(500)
    );

    public static OperationLineEntity secondOperation = new OperationLineEntity(
            2L,
            DateUtils.addDays(new Date(), -1),
            OperationType.DEPOSIT.name(),
            new BigDecimal(1750),
            new BigDecimal(2250)
    );

    public static OperationLineEntity thirdOperation = new OperationLineEntity(
            3L,
            new Date(),
            OperationType.WITHDRAW.name(),
            new BigDecimal(250),
            new BigDecimal(2000)
    );

    public static List<OperationLineEntity> ALL = Arrays.asList(firstOperation, secondOperation, thirdOperation);
}
