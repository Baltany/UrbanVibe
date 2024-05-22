package com.shop.iesvdc.shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.Category;

public interface CategoryRepo extends JpaRepository<Category,Long>{
    
}
