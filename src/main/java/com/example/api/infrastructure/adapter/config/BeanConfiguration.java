package com.example.api.infrastructure.adapter.config;

import com.example.api.application.services.UserService;
import com.example.api.application.services.WalletService;
import com.example.api.infrastructure.adapter.UserPersistenceAdapter;
import com.example.api.infrastructure.adapter.WalletPersistenceAdapter;
import com.example.api.infrastructure.adapter.output.persistence.mappers.UserPersistenceMapper;
import com.example.api.infrastructure.adapter.output.persistence.mappers.WalletPersistenceMapper;
import com.example.api.infrastructure.adapter.output.persistence.repositories.UserRepository;
import com.example.api.infrastructure.adapter.output.persistence.repositories.WalletRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserPersistenceAdapter userPersistenceAdapter(final UserRepository userRepository, final UserPersistenceMapper userPersistenceMapper){
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);
    }

    @Bean
    public WalletPersistenceAdapter walletPersistenceAdapter(final WalletRepository walletRepository, final WalletPersistenceMapper walletPersistenceMapper){
        return new WalletPersistenceAdapter(walletRepository, walletPersistenceMapper);
    }

    @Bean
    public WalletService walletService(final WalletPersistenceAdapter walletPersistenceAdapter){
        return new WalletService(walletPersistenceAdapter);
    }

    @Bean
    public UserService userService(final UserPersistenceAdapter userPersistenceAdapter){
        return new UserService(userPersistenceAdapter);
    }
}
