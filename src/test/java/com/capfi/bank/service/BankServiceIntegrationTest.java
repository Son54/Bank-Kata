package com.capfi.bank.service;

import com.capfi.bank.OperationAmount;
import com.capfi.bank.OperationLine;
import com.capfi.bank.OperationType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BankServiceIntegrationTest {

    @Autowired
    private BankService bankService;

    @Test
    public void shouldDeposit() {
        //Given
        OperationAmount operationAmount = new OperationAmount(new BigDecimal(500));

        //When
        OperationLine operationLine = bankService.deposit(operationAmount);

        //Then
        Assertions.assertThat(operationLine.operationType()).isEqualTo(OperationType.DEPOSIT);
        Assertions.assertThat(operationLine.operationAmount().doubleValue()).isEqualTo(500);
        Assertions.assertThat(operationLine.balance().doubleValue()).isEqualTo(2500);
    }

    @Test
    public void shouldWithdraw() {
        //Given
        OperationAmount operationAmount = new OperationAmount(new BigDecimal(500));

        //When
        OperationLine operationLine = bankService.withdraw(operationAmount);

        //Then
        Assertions.assertThat(operationLine.operationType()).isEqualTo(OperationType.WITHDRAW);
        Assertions.assertThat(operationLine.operationAmount().doubleValue()).isEqualTo(500);
        Assertions.assertThat(operationLine.balance().doubleValue()).isEqualTo(1500);
    }

    @Test
    public void shouldFailWithdraw_NotEnoughBalance() {
        //Given
        OperationAmount operationAmount = new OperationAmount(new BigDecimal(5000));

        //When/Then
        Exception exception = assertThrows(NotEnoughBalanceException.class, () -> {
            bankService.withdraw(operationAmount);
        });
        Assertions.assertThat(exception.getMessage()).isEqualTo("You don't have enough balance in your account (2000.00)");
    }
}
