package com.example.api.infrastructure.adapter.input.rest.dtos.requests;

import lombok.*;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRegisterRequest {
    private String username;
    private String password;
    private Boolean enabled;
    private List<Credential> credentials;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Credential {
        private String type;
        private String value;
        private boolean temporary;

    }
}
