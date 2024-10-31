package com.example.api.infrastructure.adapter.output.persistence.mappers;

import com.example.api.domain.models.User;
import com.example.api.domain.models.Wallet;
import com.example.api.infrastructure.adapter.output.persistence.entities.UserEntity;
import com.example.api.infrastructure.adapter.output.persistence.entities.WalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface UserPersistenceMapper {
    UserEntity toUserEntity(User user);
    @Mapping(target = "wallet", qualifiedByName = "mapToWallet")
    User toUser(UserEntity entity);
    List<User> toUsers(List<UserEntity> entities);

    @Named("mapToWallet")
    default Wallet mapToWallet(WalletEntity entity) {
        return Wallet.builder()
                .balance(entity.getBalance())
                .timeCreated(entity.getTimeCreated())
                .timeUpdated(entity.getTimeUpdated())
                .id(entity.getId())
                .build();
    }
}
