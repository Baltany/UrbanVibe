package com.shop.iesvdc.shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.Category;

/**
 * Esta clase es la interfaz CategoryRepository encargada de los metodos necesarios para las categorias de ropa
 * @author Balbino Moyano Lopez
 */
public interface CategoryRepo extends JpaRepository<Category,Long>{
    
}
