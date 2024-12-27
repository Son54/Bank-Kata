package com.capfi.bank.service;

import com.capfi.bank.model.OperationAmount;
import com.capfi.bank.model.OperationLine;
import com.capfi.bank.model.OperationType;
import com.capfi.bank.data.BankRepository;
import com.capfi.bank.data.OperationLineEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    /**
     * Retrieve the history of all operations
     *
     * @return the list of all operations
     */
    public List<OperationLine> history() {
        return bankRepository.findAll().stream()
            .map(operationLine -> new OperationLine(
                    operationLine.getDate(),
                    OperationType.valueOf(operationLine.getOperationType()),
                    operationLine.getOperationAmount(),
                    operationLine.getBalance()
                )
            )
            .sorted(Comparator.comparing(OperationLine::date).reversed()) //sort the latest operations on first
            .collect(Collectors.toList());
    }

    /**
     * Deposit money in the account
     *
     * @param depositAmount the amount to deposit
     * @return the operation line added
     */
    public OperationLine deposit(OperationAmount depositAmount) {
        Optional<OperationLineEntity> lastOperation = bankRepository.findTopByOrderByIdDesc();
        BigDecimal oldBalance = new BigDecimal(0);
        if (lastOperation.isPresent()) {
            oldBalance = lastOperation.get().getBalance();
        }
        return addNewOperationLine(oldBalance, depositAmount.amount(), OperationType.DEPOSIT);
    }

    /**
     * Withdraw money from the account
     *
     * @param withdrawAmount the amount to withdraw
     * @return the operation line added
     */
    public OperationLine withdraw(OperationAmount withdrawAmount) throws OperationException {
        Optional<OperationLineEntity> lastOperation = bankRepository.findTopByOrderByIdDesc();
        if (lastOperation.isEmpty()) {
            throw new NoBalanceException();
        }
        BigDecimal oldBalance = lastOperation.get().getBalance();
        if (oldBalance.compareTo(withdrawAmount.amount()) < 0) {
            throw new NotEnoughBalanceException(oldBalance);
        }
        return addNewOperationLine(oldBalance, withdrawAmount.amount(), OperationType.WITHDRAW);
    }

    public OperationLine addNewOperationLine(BigDecimal balance, BigDecimal amount, OperationType operationType) {
        BigDecimal newBalance;
        if (operationType.equals(OperationType.DEPOSIT)) {
            newBalance = balance.add(amount);
        } else {
            newBalance = balance.subtract(amount);
        }
        OperationLineEntity operationLineToRegister = new OperationLineEntity(new Date(), operationType.name(), amount, newBalance);
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
