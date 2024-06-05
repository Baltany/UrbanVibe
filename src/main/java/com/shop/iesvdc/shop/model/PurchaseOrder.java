package com.shop.iesvdc.shop.model;


import java.util.List;
import java.util.Set;

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



@Data
@Entity
@NoArgsConstructor
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*posible cambio a float */
    private double totalPrice;

    private String orderDate;

    /*
     * Lista de articulos que ha comprado el cliente
     * @JsonManagedRefence:se aplica al lado directo de la referencia cuando usamos relaciones de uno a muchos
     */
    // @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // //@JoinColumn(name = "purcharse_order_clothes_id")
    // @JsonManagedReference
    // @ToString.Exclude // Evita la recursividad en toString

    // private List<Clothes> clothesList;

    /*
     * Varios pedidos pueden ser realizados por un mismo cliente    
     */
    @ManyToOne
    private User user;


    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private Set<OrderTracking> orderTracking;


    // Si tienes un método toString personalizado, asegúrate de excluir clothesList o hacerlo de manera segura
    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", orderDate='" + orderDate + '\'' +
                ", user=" + user +
                // No incluyas clothesList aquí
                '}';
    }


    public Set<OrderTracking> getOrderTrackings() {
        return orderTracking;
    }

    public void setOrderTrackings(Set<OrderTracking> orderTrackings) {
        this.orderTracking = orderTracking;
    }



}
