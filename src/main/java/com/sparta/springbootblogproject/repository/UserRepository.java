package com.sparta.springbootblogproject.repository;

import com.sparta.springbootblogproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    //Optional<User> findByUsername(String username);
}
