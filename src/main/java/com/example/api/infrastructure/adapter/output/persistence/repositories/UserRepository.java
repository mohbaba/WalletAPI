package com.example.api.infrastructure.adapter.output.persistence.repositories;

import com.example.api.infrastructure.adapter.output.persistence.entities.UserEntity;
import com.example.api.infrastructure.adapter.output.persistence.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntityByEmail(String email);

}
