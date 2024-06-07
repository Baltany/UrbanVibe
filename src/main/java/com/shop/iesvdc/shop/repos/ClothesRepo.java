package com.shop.iesvdc.shop.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shop.iesvdc.shop.model.Clothes;

public interface ClothesRepo extends JpaRepository<Clothes,Long> {
    @Query("SELECT c FROM Clothes c JOIN c.sexList s WHERE s.sex = :sex")
    List<Clothes> findAllBySex(@Param("sex") String sex);


}
