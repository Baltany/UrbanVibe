package com.shop.iesvdc.shop.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.OrderTracking;
import com.shop.iesvdc.shop.model.PurchaseOrder;

/**
 * Esta clase es la interfaz OrderTrackingRepository encargada de los metodos necesarios para el seguimiento de la ropa
 * @author Balbino Moyano Lopez
 */
public interface OrderTrackingRepo extends JpaRepository<OrderTracking,Long> {
    /**
     * Busca por orden de compra
     * @param purchaseOrder
     * @return
     */
    List<OrderTracking> findByPurchaseOrder(PurchaseOrder purchaseOrder);

}
