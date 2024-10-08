package com.example.demo.core.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.core.config.ValuesConfig;
import com.example.demo.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {


    @Autowired
    private final ValuesConfig valuesConfig;

    public JwtUtil(ValuesConfig valuesConfig) {
        this.valuesConfig = valuesConfig;
    }


    public String createToken(String email){
        return JWT.create().
                withSubject(email).
                withIssuer(this.valuesConfig.getAuthor()).
                withIssuedAt(new Date()).
                withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15))).sign(Algorithm.HMAC256(this.valuesConfig.getJwtSecret()));

    }


    public boolean isValid(String jwt){
        try{
            JWT.require(Algorithm.HMAC256(this.valuesConfig.getJwtSecret()))
                    .build()
                    .verify(jwt);
            return true;
        } catch (JWTVerificationException e){
            return false;
        }
    }

    public String getEmail(String jwt){
        return JWT.require(Algorithm.HMAC256(this.valuesConfig.getJwtSecret()))
                .build()
                .verify(jwt)
                .getSubject();
    }

    public String getAuthenticatedUserFromJwt(String jwt) {
        if (isValid(jwt)) {
            return getEmail(jwt);
        }
        return null;
    }
}
