package com.softweb.api.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home").permitAll()
                        // Application endpoints
                        .requestMatchers(HttpMethod.GET, "/v1/application", "/v1/application/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/application", "/v1/application/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/v1/application", "/v1/application/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/v1/application", "/v1/application/**").authenticated()
                        // User endpoints
                        .requestMatchers(HttpMethod.GET,"/v1/user", "/v1/user/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/user/auth").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/v1/user", "/v1/user/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/v1/user", "/v1/user/**").authenticated()
                        // License endpoints
                        .requestMatchers(HttpMethod.GET,"/v1/license", "/v1/license/**").permitAll()
                        // Operating-system endpoints
                        .requestMatchers(HttpMethod.GET,"/v1/operatingSystem", "/v1/operatingSystem/**").permitAll()
                        // Image endpoints
                        .requestMatchers(HttpMethod.GET,"/v1/image", "/v1/image/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/image", "/v1/image/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/v1/image", "/v1/image/**").authenticated()
                        // Installer endpoints
                        .requestMatchers(HttpMethod.GET,"/v1/installer", "/v1/installer/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/installer", "/v1/installer/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/v1/installer", "/v1/installer/**").authenticated()
                        // Category endpoints
                        .requestMatchers(HttpMethod.GET,"/v1/category", "/v1/category/**").permitAll()
                        // API-Docs endpoint
                        .requestMatchers(HttpMethod.GET,"/v1/api-docs", "/v1/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/v1/api-swagger", "/v1/api-swagger/**").permitAll()
                        .anyRequest().permitAll()
                )
                .csrf().disable()
                .httpBasic();
        return httpSecurity.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enabled from users where username = ?")
                .authoritiesByUsernameQuery("select users.username, authority.authority from authority inner join users " +
                        "on users.id=authority.user_id where users.username = ?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
