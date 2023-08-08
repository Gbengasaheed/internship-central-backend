package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final UnAuthorizedEntryPoint unAuthorizedEntryPoint;

    public SecurityConfiguration(UnAuthorizedEntryPoint unAuthorizedEntryPoint) {
        this.unAuthorizedEntryPoint = unAuthorizedEntryPoint;
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeHttpRequests((authorize) -> {
                    try {
                        authorize
                                .antMatchers("/api/v6/authorize", "/api/v6/signUp", "/api/v6/recruiter-signUp","/api/v6/verify/**").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .httpBasic()
                                .and()
                                .exceptionHandling()
                                .authenticationEntryPoint(unAuthorizedEntryPoint)
                                .and()
                                .sessionManagement()
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilterBean(), JWTAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilterBean() {
        return new JWTAuthenticationFilter();
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilterBean() {
        return new ExceptionHandlerFilter();
    }
}
