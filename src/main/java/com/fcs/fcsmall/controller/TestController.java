package com.fcs.fcsmall.controller;

import com.fcs.fcsmall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/home")
    public String home(){return "<h1> THIS IS HOME_PAGE -- NO LOGIN REQUIRED </h1>";}

    @GetMapping("/user")
    public String user(){return "<h1> THIS IS USER_page -- YOU NEED A ROLE TO ENTER </h1>";}

    @GetMapping("/manager")
    public String manager(){return "<h1> THIS IS MANAGER_page -- YOU NEED A MANAGER or ADMIN ROLE TO ENTER </h1>";}

    @GetMapping("/admin")
    public String admin(){return "<h1> THIS IS ADMIN_page -- YOU ONLY NEECD ADMIN ROLE TO ENTER </h1>";}





}
