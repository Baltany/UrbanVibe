package com.shop.iesvdc.shop.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.iesvdc.shop.model.OrderTracking;

public interface OrderTrackingRepo extends JpaRepository<OrderTracking,Long> {
    
}
