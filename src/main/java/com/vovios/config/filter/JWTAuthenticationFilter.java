package com.vovios.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.filter.GenericFilterBean;

public class JWTAuthenticationFilter extends GenericFilterBean {
    
    AuthenticationManager authenticationManager;
    String tokenHeader = "Authorization";
    
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String jwtString = httpRequest.getHeader(tokenHeader);
        
        JWTAuthenticationToken authentication = new JWTAuthenticationToken(jwtString);
        
        authenticationManager.authenticate(authentication);
    }

}
