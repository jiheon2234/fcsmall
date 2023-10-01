package com.fcs.fcsmall.repository;

import com.fcs.fcsmall.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    /** username(ID)로 유저 찾기 */
    public Optional<User> findByUsername(String userName);


}
