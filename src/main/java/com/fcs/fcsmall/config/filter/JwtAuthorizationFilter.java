package com.fcs.fcsmall.config.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fcs.fcsmall.config.auth.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {


    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.info("JwtAuthorizationFilter.doFilterInternal() start");

        String jwtHeader = request.getHeader("Authorization");
        log.info("jwtHeader={}",jwtHeader);

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }

        String accessToken = jwtHeader.split(" ")[1].trim();

        if(jwtProvider.validateToken(accessToken)){
            DecodedJWT decodedJWT = JWT.decode(accessToken);

            String id = decodedJWT.getSubject();
            String username = decodedJWT.getClaim("username").asString();
            String role = "ROLE_" + decodedJWT.getClaim("role").asString();

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
            Authentication authentication = new PreAuthenticatedAuthenticationToken(null,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        chain.doFilter(request,response);

    }
}
