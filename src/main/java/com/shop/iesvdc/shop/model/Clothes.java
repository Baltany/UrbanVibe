package com.shop.iesvdc.shop.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Esta clase es la Entidad de Clothes que contiene todos los atributos que yo he visto mas necesarios en la ropa de hoy en dia
 * @author Balbino Moyano Lopez
 */
@Entity
@Data
@NoArgsConstructor
public class Clothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String description;


    private String image;

    private double price;

    private String color;

    private int stock;


    /**
     * @Enum: XXS,XS,S,M,L,XL,XXL,MEASURE
     */
    @ManyToMany
    private List<Size> sizeList;


    /**
     * @Enum: SHIRT,JOGGERS...
     */
    @ManyToMany
    private List<Category> categoryList;

    @ManyToMany
    private List<Sex> sexList;

    @OneToMany
    private List<OrderTracking> orderTraking;



    @Override
    public String toString() {
        return "Clothes{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", stock=" + stock +
                ", sizes=" + sizeList + 
                ", categories=" + categoryList +
                '}';
    }

    public List<Size> getSizes() {
        return this.sizeList;
    }
    
    public void setSizes(List<Size> sizeList) {
        this.sizeList = sizeList;
    }

    public static Clothes findById(Long clothesId) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}
