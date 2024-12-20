package com.capfi.bank.web;

import com.capfi.bank.OperationAmount;
import com.capfi.bank.OperationType;
import com.capfi.bank.data.OperationLineList;
import com.capfi.bank.service.BankService;
import com.capfi.bank.service.NotEnoughBalanceException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.NotAcceptableStatusException;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BankController.class)
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService bankService;

    @Test
    public void shouldListHistory() throws Exception {
        //Given
        Mockito.when(bankService.history()).thenReturn(OperationLineList.ALL);

        //When Then
        mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].operationType", CoreMatchers.is(OperationType.WITHDRAW.name())))
                .andExpect(jsonPath("$[1].operationType", CoreMatchers.is(OperationType.DEPOSIT.name())))
                .andExpect(jsonPath("$[2].operationType", CoreMatchers.is(OperationType.DEPOSIT.name())));
    }
}
