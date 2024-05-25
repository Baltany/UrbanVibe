package com.shop.iesvdc.shop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.Sex;

public interface SexRepo extends JpaRepository<Sex,Long>{

    List<Sex> findBySex(Sex Sex);
    
}
