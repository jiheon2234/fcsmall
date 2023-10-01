package com.fcs.fcsmall.config.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/**
 * JWT토큰을 제공해주는 클래스
 */
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final String SECRETKEY = "김연우ㅂㅅㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ"; //시크릿키
    private static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRETKEY); //알고리즘
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 15000000;  //만료시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 20000000;  //만료시간


    public String generateAccessToken(String id,HashMap<String, String> claims) {

        return JWT.create()
                .withSubject(id)
                .withPayload(claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .sign(ALGORITHM);
    }


    public String generateRefreshToken(HashMap<String, String> claims) {

        return JWT.create()
                .withPayload(claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .sign(ALGORITHM);
    }

    /** 토큰 검증 */
    public boolean validateToken(String token){
        try {
            JWT.require(ALGORITHM).build()
                    .verify(token);
            return true;
        }catch (JWTVerificationException e){
            return false;
        }
    }

}
