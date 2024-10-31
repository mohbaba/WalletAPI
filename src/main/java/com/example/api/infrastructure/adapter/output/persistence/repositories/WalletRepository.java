package com.example.api.infrastructure.adapter.output.persistence.repositories;

import com.example.api.infrastructure.adapter.output.persistence.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

}
