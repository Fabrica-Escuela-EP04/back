package com.p2f4.med_office.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.p2f4.med_office.domain.UserRepository;
import com.p2f4.med_office.entity.User;
import com.p2f4.med_office.security.CustomAuthenticationProvider;
import com.p2f4.med_office.utils.UserNotFoundException;

@Configuration(proxyBeanMethods=false)
public class ApiConfiguration {
    private static final String COMMON_PATH = "/api";
    private static final String API_VERSION = "/v1";
    public  static final String API_BASE_PATH = COMMON_PATH + API_VERSION;

    private final UserRepository repository;

     public ApiConfiguration(UserRepository repository) {
        this.repository = repository;
    }

    @Bean
    public UserDetailsService userDetailService() {
        return userEmail -> {
            final User user = repository.findByEmail(userEmail)
                              .orElseThrow(() -> new UserNotFoundException());

            String userRole = "ROLE_" + (user.getUserRole().getName()).toUpperCase();
            var authority = new SimpleGrantedAuthority(userRole);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities(authority)
                    .build();
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new CustomAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
