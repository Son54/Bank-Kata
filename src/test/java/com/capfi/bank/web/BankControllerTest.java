package com.capfi.bank.web;

import com.capfi.bank.model.OperationType;
import com.capfi.bank.data.OperationLineListTest;
import com.capfi.bank.service.BankService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
        Mockito.when(bankService.history()).thenReturn(OperationLineListTest.ALL);

        //When Then
        mockMvc.perform(get("/history"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].operationType", CoreMatchers.is(OperationType.DEPOSIT.name())))
            .andExpect(jsonPath("$[1].operationType", CoreMatchers.is(OperationType.DEPOSIT.name())))
            .andExpect(jsonPath("$[2].operationType", CoreMatchers.is(OperationType.WITHDRAW.name())));
    }
}
