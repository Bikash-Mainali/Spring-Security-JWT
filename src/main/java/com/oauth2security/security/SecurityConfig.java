package com.oauth2security.security;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/19/23
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailsService customUserDetailsService;
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                /**
                 * In Spring Security, the addFilterBefore() method is used to add a
                 * custom filter to the security filter chain before a specific existing
                 * filter. This method is typically used to insert a custom filter into
                 * the filter chain at a specific point, to perform some custom security
                 * logic before the default Spring Security filters are executed.
                 *
                 * The addFilterBefore() method takes two arguments: the custom filter
                 * object that you want to add to the filter chain, and the class of the
                 * existing filter that you want to insert the custom filter before.
                 *
                 * In this example, I am adding a custom filter called jwtAuthEntryPoint
                 * to the security filter chain before the UsernamePasswordAuthenticationFilter
                 */
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                //for exception handling by spring security itself before reaching out to our servlet
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthEntryPoint))
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated();
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
}
