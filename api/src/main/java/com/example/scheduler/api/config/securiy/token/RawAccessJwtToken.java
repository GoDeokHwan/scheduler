package com.example.scheduler.api.config.securiy.token;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;

@Slf4j
public class RawAccessJwtToken  implements JwtToken {

    private String token;

    public RawAccessJwtToken(String token) {
        this.token = token;
    }

    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            log.info("Invalid JWT Token");
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            log.info("JWT Token is expired");
            throw new JwtExpiredTokenException(this, "JWT Token expired", expiredEx);
        }
    }

    @Override
    public String getToken() {
        return token;
    }
}
