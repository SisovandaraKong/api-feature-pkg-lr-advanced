package istad.co.darambbankingapi.security;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    // Store in ram, like if start it has and if close it lost
//    @Bean
//    InMemoryUserDetailsManager inMemoryUserDetailsManager(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        UserDetails userAdmin = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("USER", "ADMIN")
//                .build();
//
//        UserDetails userEditor = User.builder()
//                .username("editor")
//                .password(passwordEncoder.encode("editor"))
//                .roles("USER", "EDITOR")
//                .build();
//
//        manager.createUser(userAdmin);
//        manager.createUser(userEditor);
//        return manager;
//    }

    // Implement UserDetailsService, Best for test
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // To customize security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Our logic, all endpoint have security

        httpSecurity
                .authorizeHttpRequests(request -> // Define endpoint client request
                        request
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("SCOPE_user:write")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAuthority("SCOPE_user:write")
                                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("SCOPE_user:read")
                                .requestMatchers(HttpMethod.POST, "/api/v1/accounts/**").hasAuthority("SCOPE_account:write")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/accounts/**").hasAuthority("SCOPE_account:write")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/accounts/**").hasAuthority("SCOPE_account:write")
                                .requestMatchers(HttpMethod.GET, "/api/v1/accounts/**").hasAnyAuthority("SCOPE_ROLE_ADMIN", "SCOPE_account:read")
                                .anyRequest() // Mean all request should authenticate
                                .authenticated());

        // Disable form login of web
        httpSecurity.formLogin(form -> form.disable());

        // Use default http basic ( username, password ), it called mechanism
//        httpSecurity.httpBasic(Customizer.withDefaults());
        // Use jwt mechanism to get token and access
        httpSecurity.oauth2ResourceServer(jwt->jwt
                .jwt(Customizer.withDefaults()));

        // Disable csrf
        httpSecurity.csrf(token -> token.disable());

        // Not store anything on server, and can do it should disable csrf first
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
        ));
        return httpSecurity.build();
    }
    // JWT access token================================

    // Generate an RSA public/private key pair (asymmetric cryptography)
    // The private key will be used to sign JWTs, and the public key will be used to verify them
    @Primary
    @Bean
    KeyPair keyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Wrap the generated KeyPair into an RSAKey object (Nimbus JOSE library format)
    // Includes both public and private keys, plus a unique key ID
    @Primary
    @Bean
    RSAKey rsaKey(KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Primary
    @Bean
    JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Primary
    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

    // Create jwtDecoder use Public key of RSA, it can decode jwt token
    // This allows the Resource Server to validate incoming tokens
    @Primary
    @Bean
    JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey())
                .build();
    }


    // JWT refresh token============================

    @Bean("refreshKeyPair")
    KeyPair refreshKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Wrap the generated KeyPair into an RSAKey object (Nimbus JOSE library format)
    // Includes both public and private keys, plus a unique key ID
    @Bean("refreshRsaKey")
    RSAKey refreshRsaKey(@Qualifier("refreshKeyPair") KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Bean("refreshJwkSource")
    JWKSource<SecurityContext> refreshJwkSource(@Qualifier("refreshRsaKey") RSAKey rsaKey) {
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean("refreshJwtEncoder")
    JwtEncoder refreshJwtEncoder(@Qualifier("refreshJwkSource") JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

    // Create jwtDecoder use Public key of RSA, it can decode jwt token
    // This allows the Resource Server to validate incoming tokens
    @Bean("refreshJwtDecoder")
    JwtDecoder refreshJwtDecoder(@Qualifier("refreshRsaKey") RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey())
                .build();
    }
}

