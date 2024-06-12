package com.shop.iesvdc.shop.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Esta clase es la Entidad de OrderTracking encargada de todo el seguimiento del pedido,la ropa que tiene cada pedido y el usuario que ha hecho la compra
 * @author Balbino Moyano Lopez
 */
@Entity
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "clothes_id")
    private Clothes clothes;

    private double price;

    private String status;

    private String orderDate;

    /**
     * Recojo el valor de la talla como String
     */
    private String size;



}
