package com.capfi.bank.service;

import com.capfi.bank.model.OperationAmount;
import com.capfi.bank.model.OperationLine;
import com.capfi.bank.data.BankRepository;
import com.capfi.bank.data.OperationLineEntityListTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    private BankService bankService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bankService = new BankService(bankRepository);
    }

    @Test
    public void shouldReturnHistory() {
        //Given
        Mockito.when(bankRepository.findAll()).thenReturn(OperationLineEntityListTest.ALL);

        //When
        List<OperationLine> history = bankService.history();

        //Then
        Assertions.assertThat(history)
            .extracting("balance")
            .containsExactly(new BigDecimal(2000), new BigDecimal(2250), new BigDecimal(500));
    }

    @Test
    public void shouldFailWithdraw_NoBalanceException() {
        //Given
        Mockito.when(bankRepository.findTopByOrderByIdDesc()).thenReturn(Optional.empty());
        OperationAmount operationAmount = new OperationAmount(new BigDecimal(5000));

        //When Then
        Exception exception = assertThrows(NoBalanceException.class, () -> {
            bankService.withdraw(operationAmount);
        });
        Assertions.assertThat(exception.getMessage()).isEqualTo("You don't have any money in the bank account");
    }

    @Test
    public void shouldFailWithdraw_NotEnoughBalanceException() {
        //Given
        Mockito.when(bankRepository.findTopByOrderByIdDesc()).thenReturn(Optional.of(OperationLineEntityListTest.thirdOperation));
        OperationAmount operationAmount = new OperationAmount(new BigDecimal(5000));

        //When Then
        Exception exception = assertThrows(NotEnoughBalanceException.class, () -> {
            bankService.withdraw(operationAmount);
        });
        Assertions.assertThat(exception.getMessage()).isEqualTo("You don't have enough balance in your account (2000)");
    }
}
