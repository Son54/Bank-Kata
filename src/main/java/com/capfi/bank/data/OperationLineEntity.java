package com.capfi.bank.data;

import com.capfi.bank.OperationType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bank_operations")
public class OperationLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "operation_type", nullable = false)
    private String operationType;

    @Column(name = "operation_amount", scale = 2, nullable = false)
    private BigDecimal operationAmount;

    @Column(name = "balance", scale = 2, nullable = false)
    private BigDecimal balance;

    public OperationLineEntity() {

    }

    public OperationLineEntity(Long id, Date date, String operationType, BigDecimal operationAmount, BigDecimal balance) {
        this.id = id;
        this.date = date;
        this.operationType = operationType;
        this.operationAmount = operationAmount;
        this.balance = balance;
    }

    public OperationLineEntity(Date date, String operationType, BigDecimal operationAmount, BigDecimal balance) {
        this.date = date;
        this.operationType = operationType;
        this.operationAmount = operationAmount;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(BigDecimal operationAmount) {
        this.operationAmount = operationAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
