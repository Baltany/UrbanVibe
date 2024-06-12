package com.shop.iesvdc.shop.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Esta clase es la Entidad de PurchaseOrder aqui se guardar el pedido que hace el usuario sin los id de la ropa,orden de compra
 * @author Balbino Moyano Lopez
 */
@Data
@Entity
@NoArgsConstructor
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalPrice;

    private String orderDate;


    /**
     * Varios pedidos pueden ser realizados por un mismo cliente    
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderTracking> orderTracking = new ArrayList<>();

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", orderDate='" + orderDate + '\'' +
                ", user=" + user +
                '}';
    }


    public List<OrderTracking> getOrderTrackings() {
        return orderTracking;
    }

    public void setOrderTrackings(List<OrderTracking> orderTrackings) {
        this.orderTracking = orderTracking;
    }


    public List<Clothes> getClothesList() {
        return orderTracking.stream()
                            .map(OrderTracking::getClothes)
                            .collect(Collectors.toList());
    }


    

}
