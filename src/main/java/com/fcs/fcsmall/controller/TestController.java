package com.fcs.fcsmall.controller;

import com.fcs.fcsmall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/hello")
    public String hello(){
        return "HELLO";
    }

    @GetMapping("/home")
    public String home(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return "<h1> THIS IS HOME_PAGE -- NO LOGIN REQUIRED </h1> "+ authentication.getAuthorities();
    }

    @GetMapping("/user")
    public String user(){return "<h1> THIS IS USER_page -- YOU NEED ANY ROLE TO ENTER </h1>";}

    @GetMapping("/god")
    public String manager(){return "<h1> THIS IS GOD_page -- YOU NEED A GOD ROLE TO ENTER </h1>";}

    @GetMapping("/admin")
    public String admin(){return "<h1> THIS IS ADMIN_page -- YOU ONLY NEECD ADMIN ROLE TO ENTER </h1>";}





}
