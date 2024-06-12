package com.shop.iesvdc.shop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.Sex;

/**
 * Esta clase es la interfaz SexRepository encargada de los metodos necesarios para el sexo clasificado de la ropa
 * @author Balbino Moyano Lopez
 */
public interface SexRepo extends JpaRepository<Sex,Long>{

    List<Sex> findBySex(Sex Sex);
    
}
