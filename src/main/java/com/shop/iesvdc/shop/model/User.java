package com.shop.iesvdc.shop.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;
    @Column(length = 50)
    private String surname;

    @Column(length = 25)
    private String username;

    @Column(length = 100)
    private String mail;
    @Column(length = 100)
    private String password;

    //@Pattern(regexp = "[0-9]{8}[A-Za-z]", message = "DNI debe tener 8 dígitos seguidos de una letra")
    private String dni;

    private boolean enable;
    
    /*
     * Primeramente lo he dejado como String,
     * luego posiblemente cree una clase(cp,potal,calle...) y use un @OneToMany para decir que un usuario puede tener varias direcciones
     */
    private String address;

    /*
     * ADMIN,SUPPLIER,CLIENT
     */

    /**
     * @ENUM
     */
    @ManyToMany
    private List<UserRol> rolList;

    /*
     * Un Usuario tiene una lista de Pedidos
     * @OneToMany
     */
    @OneToMany
    @JoinColumn(name = "user_purchase_id")
    private List<PurchaseOrder> purcharseOrderList;



    
    


    
}
