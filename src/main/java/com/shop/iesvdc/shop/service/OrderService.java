package com.shop.iesvdc.shop.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.iesvdc.shop.model.Clothes;
import com.shop.iesvdc.shop.model.PurchaseOrder;
import com.shop.iesvdc.shop.model.User;
import com.shop.iesvdc.shop.repos.ClothesRepo;
import com.shop.iesvdc.shop.repos.PurchaseOrderRepo;
import com.shop.iesvdc.shop.repos.UserRepo;

@Service
public class OrderService {

    @Autowired
    private PurchaseOrderRepo orderRepo;

    @Autowired
    private ClothesRepo clothesRepo;

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

        List<Clothes> clothesList = new ArrayList<>();
        for (Map<String, Object> item : cartItems) {
            Long clothesId = Long.parseLong(item.get("id").toString());
            Optional<Clothes> clothesOpt = clothesRepo.findById(clothesId);
            if (clothesOpt.isPresent()) {
                Clothes clothes = clothesOpt.get();
                clothes.setStock(clothes.getStock() - 1); // Resta 1 al stock
                clothesList.add(clothes);
            } else {
                throw new RuntimeException("Clothes not found: " + clothesId);
            }
        }
        order.setClothesList(clothesList);
        return orderRepo.save(order);
    }
}
