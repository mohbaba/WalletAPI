package com.example.api.infrastructure.adapter.output.persistence.repositories;

import com.example.api.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
//    List<TransactionEntity> findTransactionEntitiesByWalletId(Long walletId);
    List<TransactionEntity> findAllByWalletId(Long walletId);
}
