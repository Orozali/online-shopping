package com.project.onlineshopping.security;

import com.project.onlineshopping.service.LogoutHandler;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.spi.ExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/category/post/**","/product/post/**").hasRole("ADMIN")
                .requestMatchers("/product/get/**","/category/get/**","/auth/**","/search/**","/swagger-ui/**")
                .permitAll()
                .anyRequest()
                .hasAnyRole("ADMIN","USER")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
//        ;

        return http.build();
    }

}
