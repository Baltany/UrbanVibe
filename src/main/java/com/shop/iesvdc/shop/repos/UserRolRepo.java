package com.shop.iesvdc.shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.UserRol;

/**
 * Esta clase es la interfaz UserRolRepository encargada de los metodos necesarios para el rol de los usuarios
 * @author Balbino Moyano Lopez
 */
public interface UserRolRepo extends JpaRepository<UserRol,Long> {
    UserRol findByRol(String rol);

}
