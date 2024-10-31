//package com.example.api.infrastructure.adapter.config;
//
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
////@AllArgsConstructor
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final JwtAuthConverter jwtAuthConverter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/users/register").permitAll()
//                        .requestMatchers("/api/v1/users/login").permitAll()
//                        .anyRequest().hasAnyRole("USER")
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(jwtAuthConverter)
//                        )
//                )
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .build();
//    }
//
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        return http.csrf(csrf -> csrf.disable())  // Disable CSRF protection
////                .cors(cors -> cors.disable())  // Disable CORS for now
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/public/**").permitAll()
////                        .requestMatchers("auth/api/v1/users/register").permitAll()
////                        .requestMatchers("auth/api/v1/users/login").permitAll()
////                        .anyRequest().hasAnyRole("USER")
////                )
////                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))  // Use JWT tokens
////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless session management
////                .build();
////    }
//
//
//}
