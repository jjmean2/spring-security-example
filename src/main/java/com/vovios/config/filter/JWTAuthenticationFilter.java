package com.vovios.config.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthenticationFilter extends GenericFilterBean {

    String secret = "Srz;d*Y5&H8dTQ^";

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

        Authentication authentication = getValidToken(jwtString);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(httpRequest, response);

    }

    private JWTAuthenticationToken getValidToken(String jwtString) {
        if (jwtString == null) {
            return null;
        }

        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(jwtString).getBody();
        } catch (Exception ex) {
            return null;
        }

        if (claims == null) {
            return null;
        }

        LocalDateTime expireDate = LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
        if (LocalDateTime.now().isAfter(expireDate)) {
            return null;
        }

        String id = claims.getSubject();
        String authoritiesString = (String) claims.get("ath");
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(authoritiesString);

        return new JWTAuthenticationToken(id, expireDate, authorities);
    }

}
