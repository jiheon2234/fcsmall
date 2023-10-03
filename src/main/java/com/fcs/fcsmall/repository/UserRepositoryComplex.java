package com.fcs.fcsmall.repository;

import com.fcs.fcsmall.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryComplex {



    private final EntityManager em;

    private void save(User user){
        em.persist(user);
    }



}
