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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Clothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String description;


    /*Seguramente esto también sea una lista de imagenes ya que por ejemplo unas zapatillas pueden tener varias imagenes en distintas posiciones */
    private String image;

    private double price;

    private String color;

    /*Para tener un control del stock creo que sería necesario */
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

    @ManyToOne
    @JoinColumn(name = "clothes_purchase_order_id")
    @JsonBackReference
    @ToString.Exclude // Evita la recursividad en toString

    private PurchaseOrder purchaseOrder;


    // Si tienes un método toString personalizado, asegúrate de excluir purchaseOrder o hacerlo de manera segura
    @Override
    public String toString() {
        return "Clothes{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", stock=" + stock +
                ", sizes=" + sizeList + // Añadido para mostrar las tallas
                ", categories=" + categoryList + // Añadido para mostrar las categorías
                '}';
    }

    public void setSizeList(List<Size> sizeList) {
        this.sizeList.clear();
        this.sizeList.addAll(sizeList);
    }
    

}
