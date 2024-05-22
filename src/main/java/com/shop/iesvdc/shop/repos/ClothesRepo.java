package com.shop.iesvdc.shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.Clothes;

public interface ClothesRepo extends JpaRepository<Clothes,Long> {
    
}
