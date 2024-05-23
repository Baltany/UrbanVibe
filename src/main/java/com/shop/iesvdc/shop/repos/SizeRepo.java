package com.shop.iesvdc.shop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.Size;

public interface SizeRepo extends JpaRepository<Size,Long>{

    List<Size> findBySize(String size);
    
}
