package com.SpringBootMVC.ExpensesTracker.security;

import com.SpringBootMVC.ExpensesTracker.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(config ->
                config
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/showRegistrationForm").permitAll()
                        .requestMatchers("/processRegistration").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form ->
                        form
                                .loginPage("/showLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll());
        return http.build();
    }

}