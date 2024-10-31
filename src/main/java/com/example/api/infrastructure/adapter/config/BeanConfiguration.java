package com.example.api.infrastructure.adapter.config;

import com.example.api.application.ports.output.TransactionOutputPort;
import com.example.api.application.services.*;
import com.example.api.infrastructure.adapter.TransactionPersistenceAdapter;
import com.example.api.infrastructure.adapter.UserPersistenceAdapter;
import com.example.api.infrastructure.adapter.WalletPersistenceAdapter;
import com.example.api.infrastructure.adapter.output.persistence.mappers.TransactionPersistenceMapper;
import com.example.api.infrastructure.adapter.output.persistence.mappers.UserPersistenceMapper;
import com.example.api.infrastructure.adapter.output.persistence.mappers.WalletPersistenceMapper;
import com.example.api.infrastructure.adapter.output.persistence.repositories.TransactionRepository;
import com.example.api.infrastructure.adapter.output.persistence.repositories.UserRepository;
import com.example.api.infrastructure.adapter.output.persistence.repositories.WalletRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
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
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public UserPersistenceAdapter userPersistenceAdapter(final UserRepository userRepository, final UserPersistenceMapper userPersistenceMapper){
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);
    }

    @Bean
    public TransactionPersistenceAdapter transactionPersistenceAdapter(final TransactionPersistenceMapper transactionPersistenceMapper, final TransactionRepository transactionRepository){
        return new TransactionPersistenceAdapter(transactionPersistenceMapper, transactionRepository);
    }

    @Bean
    public WalletPersistenceAdapter walletPersistenceAdapter(final WalletRepository walletRepository, final WalletPersistenceMapper walletPersistenceMapper){
        return new WalletPersistenceAdapter(walletRepository, walletPersistenceMapper);
    }

    @Bean
    public PaymentService paymentService(){
        return new PaymentService();
    }

    @Bean
    public TransactionService transactionService(final TransactionPersistenceAdapter transactionPersistenceAdapter, UserPersistenceAdapter userPersistenceAdapter){
        return new TransactionService(transactionPersistenceAdapter, userPersistenceAdapter);
    }

    @Bean
    public AuthService authServerService(Keycloak keycloak){
        return new AuthService(keycloak);
    }

     @Bean
    public IdentityVerificationService identityVerificationService(
            @Value("${api.prembly.key}") String apiKey,
            @Value("${api.prembly.id}") String appId) {
        return new IdentityVerificationService();
    }

    @Bean
    public WalletService walletService(final WalletPersistenceAdapter walletPersistenceAdapter, UserPersistenceAdapter userPersistenceAdapter, TransactionPersistenceAdapter transactionPersistenceAdapter){
        return new WalletService(walletPersistenceAdapter, userPersistenceAdapter, transactionPersistenceAdapter);
    }

    @Bean
    public UserService userService(final UserPersistenceAdapter userPersistenceAdapter, final WalletPersistenceAdapter walletPersistenceAdapter, final PasswordEncoder passwordEncoder, final AuthService authService, final IdentityVerificationService identityVerificationService){
        return new UserService(userPersistenceAdapter, walletPersistenceAdapter, passwordEncoder, authService, identityVerificationService);
    }
}
