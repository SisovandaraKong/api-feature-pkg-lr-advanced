package istad.co.darambbankingapi.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
                                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyRole("CUSTOMER", "ADMIN")
                                .anyRequest() // Mean all request should authenticate
                                .authenticated());

        // Disable form login of web
        httpSecurity.formLogin(form -> form.disable());

        // Use default http basic ( username, password )
        httpSecurity.httpBasic(Customizer.withDefaults());

        // Disable csrf
        httpSecurity.csrf(token -> token.disable());
        // Not store anything on server, and can do it should disable csrf first
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
        ));
        return httpSecurity.build();
    }

}
