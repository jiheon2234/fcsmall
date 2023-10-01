package com.fcs.fcsmall.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id")
    private long id;

    @Column(unique=true, nullable = false)
    private String username; //아이디
    private String password; //비번
    private String phone; //전번

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER; //권한

}
