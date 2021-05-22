package com.example.scheduler.api.config.securiy.token;

import com.example.scheduler.api.config.securiy.JwtProperties;
import com.example.scheduler.api.domain.security.model.AuthorizationUserDetail;
import com.example.scheduler.api.util.JsonHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenFactory {
    @Autowired
    private JwtProperties properties;


    public AccessJwtToken createAccessJwtToken(AuthorizationUserDetail authorizationUserDetail) {

        if (StringUtils.isBlank(authorizationUserDetail.getLoginId())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        Claims claims = Jwts.claims().setSubject(authorizationUserDetail.getLoginId());
        claims.put("details", JsonHelper.toJson(authorizationUserDetail));

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(properties.getIssuer())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(properties.getExpiration())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, properties.getSecret())
                .compact();

        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(AuthorizationUserDetail authorizationUserDetail) {
        if (StringUtils.isBlank(authorizationUserDetail.getLoginId()))
            throw new IllegalArgumentException("Cannot create JWT Token without username");


        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(authorizationUserDetail.getLoginId());
        claims.put("details", JsonHelper.toJson(authorizationUserDetail));
        claims.put("refresh", true);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(properties.getIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(properties.getRefresh())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, properties.getSecret())
                .compact();

        return new AccessJwtToken(token, claims);
    }
}
