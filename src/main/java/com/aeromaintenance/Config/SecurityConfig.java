package com.aeromaintenance.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.aeromaintenance.Utils.JwtAuthenticationFilter;
import com.aeromaintenance.login.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Define security filter chain for HTTP requests
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .cors().and()
            .authorizeHttpRequests()
                // Allow public access to /auth/** endpoints
                .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                
                // Require ADMIN role for /admin/** endpoints
                //.requestMatchers(new AntPathRequestMatcher("/api/roles/**")).hasRole("ADMIN")
                
                // Allow USER and ADMIN roles for /user/** endpoints
                //.requestMatchers(new AntPathRequestMatcher("/user/**")).hasAnyRole("USER", "ADMIN")
                
                // All other requests must be authenticated
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before UsernamePasswordAuthenticationFilter

        return http.build();
    }

    // Define authentication manager bean with custom UserDetailsService
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService); // Set the custom UserDetailsService
        return authenticationManagerBuilder.build();
    }

    // Define password encoder bean (BCrypt is commonly used for hashing passwords)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Using BCrypt password encoder
    }

    // Remove "ROLE_" prefix from roles (common Spring Security default behavior)
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the "ROLE_" prefix from role names
    }
}
