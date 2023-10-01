package com.fcs.fcsmall.config.security;

import com.fcs.fcsmall.config.auth.PrincipalDetailService;
import com.fcs.fcsmall.config.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalDetailService principalDetailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sessionManagement-> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/user/**").hasAnyRole("ADMIN","USER","MANAGER")
                        .requestMatchers("/manager/**").hasAnyRole("ADMIN","MANAGER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList(new DaoAuthenticationProvider() {{
            setUserDetailsService(principalDetailService);
            setPasswordEncoder(passwordEncoder); // 여기에 원하는 PasswordEncoder를 넣으세요.
        }}));
    }
}
