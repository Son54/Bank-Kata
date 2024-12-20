package com.capfi.bank.data;

import com.capfi.bank.OperationLine;
import com.capfi.bank.OperationType;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OperationLineList {

    public static OperationLine firstOperation = new OperationLine(
            DateUtils.addDays(new Date(), -2),
            OperationType.WITHDRAW,
            new BigDecimal(250),
            new BigDecimal(2000)
    );

    public static OperationLine secondOperation = new OperationLine(
            DateUtils.addDays(new Date(), -1),
            OperationType.DEPOSIT,
            new BigDecimal(1750),
            new BigDecimal(2250)
    );

    public static OperationLine thirdOperation = new OperationLine(
            new Date(),
            OperationType.DEPOSIT,
            new BigDecimal(500),
            new BigDecimal(500)
    );

    public static List<OperationLine> ALL = Arrays.asList(firstOperation, secondOperation, thirdOperation);
}
