package com.fcs.fcsmall.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcs.fcsmall.config.auth.JwtProvider;
import com.fcs.fcsmall.config.auth.dto.LoginDto;
import com.fcs.fcsmall.config.auth.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;

@Deprecated
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override   //인증을 시도하는 메서드
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info(" JwtAuthenticationFilter.attemptAuthentication() start");
        try {
            LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            PrincipalDetails principalDetails = (PrincipalDetails) authenticate.getPrincipal(); //로그인 로그 찍을려고
            log.info("로그인 완료됨 : username:{} password: {}", principalDetails.getUsername(), principalDetails.getPassword());

            return authenticate;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            log.info("attemptAuthentication end");
        }
    }

    @Override  //
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("JwtAuthenticationFilter.successfulAuthentication() start");

        PrincipalDetails principalDetails =(PrincipalDetails) authResult.getPrincipal();

        HashMap<String, String> claims = new HashMap<>();
        String userId =  String.valueOf(principalDetails.getUser().getId());
        claims.put("username",principalDetails.getUsername());
        claims.put("role",principalDetails.getUser().getRole().toString());

        String accessToken = jwtProvider.generateAccessToken(userId,claims);
        String refreshToken = jwtProvider.generateRefreshToken(claims);

        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addHeader("Refresh-Token", refreshToken);

        log.info("JwtAuthenticationFilter.successfulAuthentication() end");
    }
}
