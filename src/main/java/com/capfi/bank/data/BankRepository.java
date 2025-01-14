package com.capfi.bank.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<OperationLineEntity, Long> {

    Optional<OperationLineEntity> findTopByOrderByIdDesc();
}
