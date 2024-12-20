package com.capfi.bank.service;

import com.capfi.bank.OperationAmount;
import com.capfi.bank.OperationLine;
import com.capfi.bank.OperationType;
import com.capfi.bank.data.BankRepository;
import com.capfi.bank.data.OperationLineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankService {

    @Autowired
    BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<OperationLine> history() {
        return bankRepository.findAll().stream()
                .map(operationLine -> new OperationLine(
                        operationLine.getDate(),
                        OperationType.valueOf(operationLine.getOperationType()),
                        operationLine.getOperationAmount(),
                        operationLine.getBalance()
                        )
                )
                .sorted(Comparator.comparing(OperationLine::date).reversed())
                .collect(Collectors.toList());
    }

    public OperationLine deposit(OperationAmount creditAmount) {
        Optional<OperationLineEntity> lastOperation = bankRepository.findTopByOrderByIdDesc();
        BigDecimal oldBalance = new BigDecimal(0);
        if (lastOperation.isPresent()) {
            oldBalance = lastOperation.get().getBalance();
        }
        OperationLineEntity operationLineToRegister = new OperationLineEntity(
            new Date(),
            OperationType.DEPOSIT.name(),
            creditAmount.amount(),
            oldBalance.add(creditAmount.amount())
        );

        OperationLineEntity registeredLine = bankRepository.save(operationLineToRegister);

        return entityToObject(registeredLine);
    }

    public OperationLine withdraw(OperationAmount withdrawAmount) {
        Optional<OperationLineEntity> lastOperation = bankRepository.findTopByOrderByIdDesc();
        if (lastOperation.isEmpty()) {
            throw new NoBalanceException();
        }
        BigDecimal oldBalance = lastOperation.get().getBalance();
        if (oldBalance.compareTo(withdrawAmount.amount()) < 0) {
            throw new NotEnoughBalanceException(oldBalance);
        }

        OperationLineEntity operationLineToRegister = new OperationLineEntity(
                new Date(),
                OperationType.WITHDRAW.name(),
                withdrawAmount.amount(),
                oldBalance.subtract(withdrawAmount.amount())
        );

        OperationLineEntity registeredLine = bankRepository.save(operationLineToRegister);

        return entityToObject(registeredLine);
    }

    public OperationLine entityToObject(OperationLineEntity operationLineEntity) {
        return new OperationLine(
            operationLineEntity.getDate(),
            OperationType.valueOf(operationLineEntity.getOperationType()),
            operationLineEntity.getOperationAmount(),
            operationLineEntity.getBalance()
        );
    }
}
