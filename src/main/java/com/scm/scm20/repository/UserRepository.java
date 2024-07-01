package com.scm.scm20.repository;
import com.scm.scm20.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findByEmail(String email);
    
    Optional<User> findByEmailAndPassword(String email,String password);

}
