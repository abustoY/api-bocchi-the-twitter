package com.yotsuba.bocchi.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class MySecurityConfig {
    @Value("${client.origin:http://localhost:8081}")
    private String clientOrigin;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CustomOidcUserService customOidcUserService
    ) throws Exception {
        http
                .cors(customizer -> customizer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/api/authentication/login")
                        .usernameParameter("id")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            response.setContentType("application/json;charset=UTF-8");
                            response.setStatus(HttpServletResponse.SC_OK);

                            Object principal = authentication.getPrincipal();

                            if (principal instanceof com.yotsuba.bocchi.security.MyUserDetails userDetails) {
                                var safeUser = new com.yotsuba.bocchi.dto.AuthenticatedUserResponse(
                                        userDetails.getUsername(), // ← id を username として取得
                                        userDetails.getName()
                                );
                                new com.fasterxml.jackson.databind.ObjectMapper().writeValue(response.getWriter(), safeUser);
                            } else {
                                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                                response.getWriter().write("{\"error\":\"Unexpected principal type\"}");
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(u -> u.oidcUserService(customOidcUserService))
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_FOUND);
                            response.setHeader("Location", clientOrigin + "/");
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/api/authentication/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET, "/api/user/avatar/**").permitAll()
                        .requestMatchers(
                                "/api/authentication/status",
                                "/api/authentication/signup",
                                "/api/authentication/login",
                                "/oauth2/authorization/**",
                                "/login/oauth2/code/**",
                                "/api/tweets",
                                "/api/media/**",
                                "/error"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin(clientOrigin);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
