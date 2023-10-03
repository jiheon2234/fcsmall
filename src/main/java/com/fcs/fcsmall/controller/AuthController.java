package com.fcs.fcsmall.controller;

import com.fcs.fcsmall.config.auth.JwtProvider;
import com.fcs.fcsmall.config.auth.PrincipalDetails;
import com.fcs.fcsmall.config.auth.dto.JoinDto;
import com.fcs.fcsmall.config.auth.dto.LoginDto;
import com.fcs.fcsmall.config.auth.dto.TokenResponse;
import com.fcs.fcsmall.domain.User;
import com.fcs.fcsmall.domain.UserRole;
import com.fcs.fcsmall.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @PostConstruct
    public void init(){
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(passwordEncoder.encode("admin"));
        user1.setPhone("010-0000-0000");
        user1.setRole(UserRole.ADMIN);
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("god");
        user2.setPassword(passwordEncoder.encode("god"));
        user2.setPhone("010-0000-0000");
        user2.setRole(UserRole.GOD);
        userRepository.save(user2);

    }

    @PostMapping("/join")
    public String join(@RequestBody JoinDto joinDto){
        User user = joinDto.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "JOIN_COMPLETE";
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto){
        log.info(" AuthController.authenticateUser() start");

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("로그인 완료됨 : username:{} password: {}", principalDetails.getUsername(), principalDetails.getPassword());

            HashMap<String,String> claims = new HashMap<>();
            String userId =  String.valueOf(principalDetails.getUser().getId());
            claims.put("username", principalDetails.getUsername());
            claims.put("role", principalDetails.getUser().getRole().toString());


            String accessToken = jwtProvider.generateAccessToken(userId, claims);
            String refreshToken = jwtProvider.generateRefreshToken(claims);

            TokenResponse tokenResponse = new TokenResponse("Bearer " + accessToken, refreshToken);


            return ResponseEntity.ok(tokenResponse);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }finally {
            log.info("authenticateUser end");
        }

    }
}
