package com.shop.iesvdc.shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.UserRol;

public interface UserRolRepo extends JpaRepository<UserRol,Long> {
    UserRol findByRol(String rol);

}
