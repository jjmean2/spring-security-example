package com.vovios.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vovios.config.filter.AppAccessDeniedHandler;
import com.vovios.config.filter.JWTAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterAfter(new JWTAuthenticationFilter(null), UsernamePasswordAuthenticationFilter.class);
            http.antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()//allow CORS option calls
                    .anyRequest().permitAll();
            http.exceptionHandling().authenticationEntryPoint(new AppAccessDeniedHandler());
        }
    }

    @Configuration
    public static class AuthWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

        }
    }
}
