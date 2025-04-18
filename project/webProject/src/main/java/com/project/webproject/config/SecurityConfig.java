package com.project.webproject.config;

import com.project.webproject.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.debug("Configuring SecurityFilterChain");
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/register"),
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/error"),
                                new AntPathRequestMatcher("/css/**"),
                                new AntPathRequestMatcher("/js/**"),
                                new AntPathRequestMatcher("/files/**")
                        ).permitAll()
                        .requestMatchers(
                                new AntPathRequestMatcher("/lecture/add", "GET"),
                                new AntPathRequestMatcher("/lecture/add", "POST"),
                                new AntPathRequestMatcher("/lecture/*/delete", "POST"),
                                new AntPathRequestMatcher("/lecture/*/add-note", "GET"),
                                new AntPathRequestMatcher("/lecture/*/add-note", "POST"),
                                new AntPathRequestMatcher("/lecture/*/note/*/delete", "POST")
                        ).hasRole("TEACHER")
                        .requestMatchers(
                                new AntPathRequestMatcher("/poll/add", "GET"),
                                new AntPathRequestMatcher("/poll/add", "POST"),
                                new AntPathRequestMatcher("/poll/*/delete", "POST")
                        ).hasRole("TEACHER")
                        .requestMatchers(
                                new AntPathRequestMatcher("/poll/**"),
                                new AntPathRequestMatcher("/lecture/**")
                        ).hasAnyRole("STUDENT", "TEACHER")
                        .requestMatchers(
                                new AntPathRequestMatcher("/users/**")
                        ).hasRole("TEACHER")
                        .requestMatchers(new AntPathRequestMatcher("/logout", "GET")).denyAll()
                        .anyRequest().hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error")
                )
                .authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/WEB-INF/**");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        logger.debug("Providing UserDetailsService: {}", appUserService.getClass().getName());
        return appUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                Authentication auth = super.authenticate(authentication);
                return auth;
            }
        };
        provider.setUserDetailsService(appUserService);
        provider.setPasswordEncoder(passwordEncoder);
        logger.debug("AuthenticationProvider configured");
        return provider;
    }
}