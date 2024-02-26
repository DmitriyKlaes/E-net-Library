package ru.klaes.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/ui/issue/**").hasAuthority("admin")
                        .requestMatchers("/ui/reader/**").hasAuthority("reader")
                        .requestMatchers("/ui/book/**").authenticated()
                        .anyRequest().permitAll()
                ).formLogin(form -> form
                .loginPage("/login")
                        .permitAll()).csrf(AbstractHttpConfigurer::disable) // отключен csrf что бы пользоваться постманом или свагером
                .build();
    }
    
}
