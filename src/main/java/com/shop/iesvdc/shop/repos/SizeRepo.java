package com.shop.iesvdc.shop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.Size;

/**
 * Esta clase es la interfaz SizeRepository encargada de los metodos necesarios para las tallas de ropa
 * @author Balbino Moyano Lopez
 */
public interface SizeRepo extends JpaRepository<Size,Long>{

    List<Size> findBySize(String size);

    
}
