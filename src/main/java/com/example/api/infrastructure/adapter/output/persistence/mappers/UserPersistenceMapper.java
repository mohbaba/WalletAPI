package com.example.api.infrastructure.adapter.output.persistence.mappers;

import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.output.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserPersistenceMapper {
    UserEntity toUserEntity(User user);
    User toUser(UserEntity entity);
}
