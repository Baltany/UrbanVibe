package com.shop.iesvdc.shop.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.User;

public interface UserRepo extends JpaRepository<User,Long>{

    Optional<User> findByMail(String mail);
    Optional<User> findByUsername(String username);

    
}
