package istad.co.darambbankingapi.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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


    // Store in ram, like if start it has and if close it lost
    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails userAdmin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build();

        UserDetails userEditor = User.builder()
                .username("editor")
                .password(passwordEncoder.encode("editor"))
                .roles("USER", "EDITOR")
                .build();

        manager.createUser(userAdmin);
        manager.createUser(userEditor);
        return manager;
    }

    // To customize security
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        // Our logic, all endpoint have security

        httpSecurity
                .authorizeHttpRequests(request -> // Define endpoint client request
                        request
                                .requestMatchers("/api/v1/users/**").hasRole("ADMIN")
                                .anyRequest() // Mean all request should authenticated
                                .authenticated());

        // Use default http basic ( username, password )
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

}
