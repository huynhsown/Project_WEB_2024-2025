package com.adidark.config;

import com.adidark.enums.RoleType;
import com.adidark.filter.JwtTokenFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableWebSecurity
//@EnableWebMvc
public class WebSecurityConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(requests -> {
//                    requests
//                            .requestMatchers(
//                                    "/register",
//                                    "/login",
//                                    "/customer/products",
//                                    "/v1/api/login",
//                                    "/v1/api/register")
//                            .permitAll()
//                            .anyRequest().authenticated();
//                });
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(requests -> {
                    requests
                            .requestMatchers("/register", "/login", "/v1/api/login", "/v1/api/register")
                            .permitAll()
                            .requestMatchers("/customer/**", "/v1/api/customer/**" , "/vnpay-payment-return**").hasRole(RoleType.CUSTOMER.name())
                            .requestMatchers("/admin/**").hasRole(RoleType.ADMIN.name())
                            .anyRequest().denyAll();
                })
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                });
        return http.build();
    }



}