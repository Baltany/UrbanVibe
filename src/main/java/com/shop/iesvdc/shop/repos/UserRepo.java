package com.shop.iesvdc.shop.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.User;

/**
 * Esta clase es la interfaz UserRepository encargada de los metodos necesarios para las acciones de los usuarios en la app
 * @author Balbino Moyano Lopez
 */
public interface UserRepo extends JpaRepository<User,Long>{

    Optional<User> findByMail(String mail);
    Optional<User> findByUsername(String username);
    Optional<User> findByDni(String dni); 


    
}
