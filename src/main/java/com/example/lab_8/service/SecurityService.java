package com.example.lab_8.service;

import com.example.lab_8.entity.Customer;
import com.example.lab_8.entity.Role;
import com.example.lab_8.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityService {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/events", "/tickets/search", "/tickets/event/**", "/register", "/login", "/css/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers("/events/new", "/events/create", "/events/delete/**", "/customers/**").hasRole("ADMIN")

                        .requestMatchers("/tickets/buy", "/my-tickets").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/events", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/events?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }

    @Bean
    public CommandLineRunner initAdmin(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!customerRepository.existsByEmailIgnoreCase("admin@gmail.com")) {
                Customer admin = new Customer();
                admin.setName("Адміністратор");
                admin.setEmail("admin@gmail.com");
                admin.setPhone("+380000000000");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole(Role.ROLE_ADMIN);
                customerRepository.save(admin);
            }
        };
    }
}