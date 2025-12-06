package com.p2f4.med_office.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.p2f4.med_office.config.ApiConfiguration;

import jakarta.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity(debug = false)
@EnableMethodSecurity
@Profile("!test")
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfiguration(AuthenticationProvider authProvider, JwtAuthFilter jwtAuthFilter) {
        this.authenticationProvider = authProvider;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    public final static String LOGIN_URL_MATCHER = ApiConfiguration.API_BASE_PATH + "/auth/login";
    public final static String REFRESH_URL_MATCHER = ApiConfiguration.API_BASE_PATH + "/auth/refresh";
    public final static String REGISTER_URL_MATCHER = ApiConfiguration.API_BASE_PATH + "/auth/register";
    public final static String LOG_OUT_URL_MATCHER = ApiConfiguration.API_BASE_PATH + "/auth/logout";
    final String BASE_URL_MATCHER = ApiConfiguration.API_BASE_PATH + "/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
   
        http
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/explorer/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/actuator/prometheus/**").permitAll()
                .requestMatchers(HttpMethod.POST, LOGIN_URL_MATCHER).permitAll()
                .requestMatchers(HttpMethod.POST, REFRESH_URL_MATCHER).permitAll()
                .requestMatchers(HttpMethod.POST, REGISTER_URL_MATCHER).authenticated()
                .requestMatchers(HttpMethod.POST, LOG_OUT_URL_MATCHER).authenticated()
                .requestMatchers(BASE_URL_MATCHER).authenticated()
                .anyRequest().denyAll()
            )
            .cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(handler -> handler
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized: Invalid or missing token");
                })
            );

        return http.build();
    }

}
