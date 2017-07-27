package com.vovios.config.filter;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 3641682935770512355L;
    
    private final Object principal;
    private final LocalDateTime expireDate;

    public JWTAuthenticationToken(Object principal,
            LocalDateTime expireDate, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.expireDate = expireDate;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
    
    public LocalDateTime getExpireDate() {
        return expireDate;
    }

}
