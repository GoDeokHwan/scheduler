package com.example.scheduler.api.domain.security.jwt;

import com.example.scheduler.api.config.securiy.token.RawAccessJwtToken;
import com.example.scheduler.api.domain.security.model.AuthorizationUserDetail;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 2877954820905567501L;

    private RawAccessJwtToken rawAccessToken;
    private AuthorizationUserDetail authorizationUserDetail;

    JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    JwtAuthenticationToken(AuthorizationUserDetail authorizationUserDetail, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.authorizationUserDetail = authorizationUserDetail;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.authorizationUserDetail;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessToken = null;
    }
}
