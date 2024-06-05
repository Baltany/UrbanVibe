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
    private UserRepo userRepo;

    public PurchaseOrder createOrder(Map<String, Object> orderData, User user) {
        user.setName(orderData.get("name").toString());
        user.setSurname(orderData.get("surname").toString());
        user.setDni(orderData.get("dni").toString());
        user.setAddress(orderData.get("address").toString());
        userRepo.save(user);

        List<Map<String, Object>> cartItems = (List<Map<String, Object>>) orderData.get("cart");
        Double total = Double.parseDouble(orderData.get("total").toString());

        PurchaseOrder order = new PurchaseOrder();
        order.setTotalPrice(total);
        order.setUser(user);
        order.setOrderDate(LocalDate.now().toString());

        for (Map<String, Object> item : cartItems) {
            Long clothesId = Long.parseLong(item.get("id").toString());
            String size = item.get("size").toString();
            int quantity = Integer.parseInt(item.get("quantity").toString());

            OrderTracking orderTracking = new OrderTracking();
            orderTracking.setPurchaseOrder(order);
            orderTracking.setClothes(new Clothes(clothesId)); // Asumimos que solo necesitas el ID para referenciar
            orderTracking.setSize(size);
            orderTracking.setQuantity(quantity);

            orderTrackingRepo.save(orderTracking);
        }

        return orderRepo.save(order);
    }
}
