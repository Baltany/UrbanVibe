package com.shop.iesvdc.shop.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.iesvdc.shop.model.Clothes;
import com.shop.iesvdc.shop.model.OrderTracking;
import com.shop.iesvdc.shop.model.PurchaseOrder;
import com.shop.iesvdc.shop.model.User;
import com.shop.iesvdc.shop.repos.ClothesRepo;
import com.shop.iesvdc.shop.repos.OrderTrackingRepo;
import com.shop.iesvdc.shop.repos.PurchaseOrderRepo;
import com.shop.iesvdc.shop.repos.UserRepo;

@Service
public class OrderService {

    @Autowired
    private PurchaseOrderRepo orderRepo;

    @Autowired
    private OrderTrackingRepo orderTrackingRepo;

    @Autowired
    private ClothesRepo clothesRepo;

    public PurchaseOrder createOrder(Map<String, Object> orderData, User user) {
        // Crear y guardar la orden
        PurchaseOrder order = new PurchaseOrder();
        order.setUser(user);
        order.setTotalPrice(Double.parseDouble(orderData.get("total").toString()));
        orderRepo.save(order);
        return order;
    }

    public void createOrderTracking(PurchaseOrder order, Long clothesId, String size, int quantity) {
        Clothes clothes = clothesRepo.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("Invalid clothes ID"));
        OrderTracking orderTracking = new OrderTracking();
        orderTracking.setPurchaseOrder(order);
        orderTracking.setClothes(clothes);
        orderTracking.setSize(size);
        orderTracking.setQuantity(quantity);
        orderTrackingRepo.save(orderTracking);
    }
}
