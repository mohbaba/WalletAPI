package com.example.api.infrastructure.adapter;

import com.example.api.application.ports.output.UserOutputPort;
import com.example.api.domain.models.User;
import com.example.api.infrastructure.adapter.output.persistence.entities.UserEntity;
import com.example.api.infrastructure.adapter.output.persistence.mappers.UserPersistenceMapper;
import com.example.api.infrastructure.adapter.output.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserOutputPort {
    private final UserRepository userRepository;
    private final UserPersistenceMapper userPersistenceMapper;
    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userPersistenceMapper.toUserEntity(user);
        userEntity = userRepository.save(userEntity);
        return userPersistenceMapper.toUser(userEntity);
    }

    @Override
    public User getUser(String email) {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email);
        return userPersistenceMapper.toUser(userEntity);
    }

    @Override
    public Optional<User> getUser(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return Optional.ofNullable(userPersistenceMapper.toUser(userEntity.orElse(null)));
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userPersistenceMapper.toUsers(userEntities);
    }
}
