package com.capfi.bank.web;

import com.capfi.bank.model.OperationAmount;
import com.capfi.bank.model.OperationLine;
import com.capfi.bank.model.OperationType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BankControllerEndToEndTest {

    @Value("${apiUrl}")
    private String apiUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldDeposit() {
        //Given
        OperationAmount depositAmount = new OperationAmount(new BigDecimal(500));

        //When
        String url = apiUrl + ":" + port + "/deposit";
        HttpEntity<OperationAmount> request = new HttpEntity<>(depositAmount);
        ResponseEntity<OperationLine> operationLineResponseEntity = this.restTemplate.exchange(url, HttpMethod.POST, request, OperationLine.class);

        //Then
        Assertions.assertThat(
            operationLineResponseEntity.getBody().balance().doubleValue()).isEqualTo(2500);
        Assertions.assertThat(
            operationLineResponseEntity.getBody().operationType()).isEqualTo(OperationType.DEPOSIT);

    }

    @Test
    public void shouldWithdraw() {
        //Given
        OperationAmount withdrawAmount = new OperationAmount(new BigDecimal(500));

        //When
        String url = apiUrl + ":" + port + "/withdraw";
        HttpEntity<OperationAmount> request = new HttpEntity<>(withdrawAmount);
        ResponseEntity<OperationLine> operationLineResponseEntity = this.restTemplate.exchange(url, HttpMethod.POST, request, OperationLine.class);

        //Then
        Assertions.assertThat(
            operationLineResponseEntity.getBody().balance().doubleValue()).isEqualTo(1500);
        Assertions.assertThat(
            operationLineResponseEntity.getBody().operationType()).isEqualTo(OperationType.WITHDRAW);

    }

    @Test
    public void shouldFailDeposit_BadArgumentNegative() {
        //Given
        OperationAmount depositAmount = new OperationAmount(new BigDecimal(-50));

        //When
        String url = apiUrl + ":" + port + "/deposit";
        HttpEntity<OperationAmount> request = new HttpEntity<>(depositAmount);
        ResponseEntity<OperationLine> operationLineResponseEntityResponseEntity = this.restTemplate.exchange(url, HttpMethod.POST, request, OperationLine.class);

        //Then
        Assertions.assertThat(operationLineResponseEntityResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldFailWithdraw_BadArgumentDecimals() {
        //Given
        OperationAmount withdrawAmount = new OperationAmount(new BigDecimal("1.123"));

        //When
        String url = apiUrl + ":" + port + "/withdraw";
        HttpEntity<OperationAmount> request = new HttpEntity<>(withdrawAmount);
        ResponseEntity<OperationLine> operationLineResponseEntityResponseEntity = this.restTemplate.exchange(url, HttpMethod.POST, request, OperationLine.class);

        //Then
        Assertions.assertThat(operationLineResponseEntityResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void shouldFailToWithdraw_NotEnoughBalance() {
        //Given
        OperationAmount withdrawAmount = new OperationAmount(new BigDecimal(5000));

        //When
        String url = apiUrl + ":" + port + "/withdraw";
        HttpEntity<OperationAmount> request = new HttpEntity<>(withdrawAmount);
        ResponseEntity<OperationLine> operationLineResponseEntityResponseEntity = this.restTemplate.exchange(url, HttpMethod.POST, request, OperationLine.class);

        //Then
        Assertions.assertThat(operationLineResponseEntityResponseEntity.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
