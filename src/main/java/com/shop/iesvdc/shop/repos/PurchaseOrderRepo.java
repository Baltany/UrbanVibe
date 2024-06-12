package com.shop.iesvdc.shop.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.PurchaseOrder;

/**
 * Esta clase es la interfaz PurchaseOrderRepository encargada de los metodos la orden de compra
 * @author Balbino Moyano Lopez
 */
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder,Long> {
    Optional<PurchaseOrder> findById(Long id);

    
}
