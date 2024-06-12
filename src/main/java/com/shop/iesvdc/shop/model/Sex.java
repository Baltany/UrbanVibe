package com.shop.iesvdc.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Esta clase es la Entidad de Sex donde distingo la ropa en por el sexo atribuido,hombre o mujer
 * @author Balbino Moyano Lopez
 */
@Entity
@Data
@NoArgsConstructor
public class Sex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 25)
    private String sex;
}
