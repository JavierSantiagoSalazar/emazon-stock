package com.pragma.emazon_stock.infrastructure.configuration.security;

import com.pragma.emazon_stock.domain.utils.Constants;
import com.pragma.emazon_stock.infrastructure.configuration.security.exceptionhandler.CustomAuthenticationEntryPoint;
import com.pragma.emazon_stock.infrastructure.configuration.security.filter.JwtValidatorFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

import static com.pragma.emazon_stock.domain.utils.Constants.ARTICLE_URL;
import static com.pragma.emazon_stock.domain.utils.Constants.BRAND_URL;
import static com.pragma.emazon_stock.domain.utils.Constants.CATEGORY_URL;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtValidatorFilter jwtValidatorFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private static final List<String> WHITE_LIST = List.of(ARTICLE_URL, BRAND_URL, CATEGORY_URL);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(
                            Constants.OPEN_API_SWAGGER_UI_HTML,
                            Constants.OPEN_API_SWAGGER_UI,
                            Constants.OPEN_API_V3_API_DOCS
                    ).permitAll();
                    WHITE_LIST.forEach(endpoint ->
                            http.requestMatchers(HttpMethod.POST, endpoint).hasRole(Constants.ROLE_ADMIN)
                    );
                    WHITE_LIST.forEach(endpoint ->
                            http.requestMatchers(HttpMethod.GET, endpoint).permitAll()
                    );
                    http.requestMatchers(HttpMethod.PATCH, ARTICLE_URL)
                            .hasRole(Constants.ROLE_WAREHOUSE_ASSISTANT);
                    http.anyRequest().authenticated();
                })
                .addFilterBefore(jwtValidatorFilter, BasicAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                .build();
    }

}
