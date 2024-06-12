package com.shop.iesvdc.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Esta clase es la Entidad de UserRol donde distingo el Usuario segun su rol que desempenia ya sea cliente o admin
 * @author Balbino Moyano Lopez
 */
@Entity
@Data
@NoArgsConstructor
public class UserRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 25)
    private String rol;
    
}
