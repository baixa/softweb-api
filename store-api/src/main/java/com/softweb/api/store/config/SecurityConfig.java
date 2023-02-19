package com.softweb.api.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/application", "/v1/application/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/v1/application", "/v1/application/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/v1/application", "/v1/application/**").hasRole("ADMIN")
                        .anyRequest().denyAll()
                );
        httpSecurity.httpBasic();
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("Michael")
                .password("P@ssw0rd")
                .roles("USER", "ADMIN")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("James")
                .password("P@ssw0rd1")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
