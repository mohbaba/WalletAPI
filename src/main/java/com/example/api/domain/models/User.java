package com.example.api.domain.models;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private Wallet wallet;
    private Role role;

    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal(0));
        this.setWallet(wallet);
        return wallet;
    }

    public void updateUserWith(User updateUserRequest) {
        if (updateUserRequest.getFirstName() != null) this.firstName = updateUserRequest.getFirstName();
        if (updateUserRequest.getLastName() != null) this.lastName = updateUserRequest.getLastName();
        if (updateUserRequest.getEmail() != null) this.email = updateUserRequest.getEmail();
        if (updateUserRequest.getEmail() != null) this.setEmail(updateUserRequest.getEmail());
        if (updateUserRequest.getPhoneNumber() != null) this.setPhoneNumber(updateUserRequest.getPhoneNumber());

    }

}
