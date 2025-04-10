package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/OnlineCourseWeb/login", "/OnlineCourseWeb/register", "/OnlineCourseWeb/css/**", "/h2-console/**").permitAll()
                        .requestMatchers("/OnlineCourseWeb/courses/add", "/OnlineCourseWeb/materials/add").hasRole("TEACHER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/OnlineCourseWeb/login")
                        .defaultSuccessUrl("/OnlineCourseWeb/dashboard")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/OnlineCourseWeb/logout")
                        .logoutSuccessUrl("/OnlineCourseWeb/login?logout")
                        .permitAll()
                )
                .csrf().disable()
                .headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
