package com.example.scheduler.api.config.securiy.token;

import com.example.scheduler.api.support.annotation.Exclude;
import io.jsonwebtoken.Claims;

public final class AccessJwtToken implements JwtToken{
    private final String rawToken;

    @Exclude
    private Claims claims;

    AccessJwtToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
