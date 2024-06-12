package com.shop.iesvdc.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Esta clase es la Entidad de Size donde distingo la ropa por tallas,XS,L,XXL...
 * @author Balbino Moyano Lopez
 */
@Entity
@Data
@NoArgsConstructor
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 25)
    private String size;


    @Override
    public String toString() {
        return "Size{" +
                "id=" + id +
                ", size='" + size + '\'' +
                '}';
    }
}
