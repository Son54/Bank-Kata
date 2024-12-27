package com.capfi.bank.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
@Entity
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "bank_operations")
public class OperationLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //Date of operation
    @Column(name = "date", nullable = false)
    private final Date date;

    //operation type : DEPOSIT | WITHDRAW
    @Column(name = "operation_type", nullable = false)
    private final String operationType;

    //operation amount, always positive, BigDecimal for maximal precision
    @Column(name = "operation_amount", scale = 2, nullable = false)
    private final BigDecimal operationAmount;

    //Total balance after operation, BigDecimal for maximal precision
    @Column(name = "balance", scale = 2, nullable = false)
    private final BigDecimal balance;
}
