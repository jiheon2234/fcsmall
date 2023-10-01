package com.fcs.fcsmall.config.auth.dto;

import com.fcs.fcsmall.domain.User;
import lombok.Data;

@Data
public class JoinDto {
    private String username;
    private String password;
    private String phone;

    public User toUser(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        return user;
    }
}
