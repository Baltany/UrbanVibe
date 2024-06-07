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

import jakarta.transaction.Transactional;

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
        order.setOrderDate(LocalDate.now().toString());
        order.setTotalPrice(Double.parseDouble(orderData.get("total").toString()));
        //order.setStatus("PENDIENTE");
        orderRepo.save(order);
        return order;
    }

    public void createOrderTracking(PurchaseOrder order, Long clothesId, String size) {
        Clothes clothes = clothesRepo.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("Invalid clothes ID"));
        OrderTracking orderTracking = new OrderTracking();
        orderTracking.setPurchaseOrder(order);
        orderTracking.setClothes(clothes);
        orderTracking.setSize(size);
        orderTracking.setStatus("PENDIENTE");
        orderTracking.setOrderDate(LocalDate.now().toString());
        orderTracking.setPrice(clothes.getPrice());
        orderTrackingRepo.save(orderTracking);

        // Decrementar stock en 1 por cada item añadido
        decrementStock(clothesId);
    }

    public boolean isStockAvailable(Long clothesId) {
        Clothes clothes = clothesRepo.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("Invalid clothes ID"));
        return clothes.getStock() > 0;
    }

    @Transactional
    public void decrementStock(Long clothesId) {
        Clothes clothes = clothesRepo.findById(clothesId).orElseThrow(() -> new IllegalArgumentException("Invalid clothes ID"));
        if (clothes.getStock() > 0) {
            clothes.setStock(clothes.getStock() - 1);
            clothesRepo.save(clothes);
        } else {
            throw new IllegalStateException("Stock not available for item ID: " + clothesId);
        }
    }

}
