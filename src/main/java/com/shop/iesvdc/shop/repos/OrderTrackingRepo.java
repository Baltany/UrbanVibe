package com.shop.iesvdc.shop.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.OrderTracking;
import com.shop.iesvdc.shop.model.PurchaseOrder;

public interface OrderTrackingRepo extends JpaRepository<OrderTracking,Long> {
    List<OrderTracking> findByPurchaseOrder(PurchaseOrder purchaseOrder);

}
