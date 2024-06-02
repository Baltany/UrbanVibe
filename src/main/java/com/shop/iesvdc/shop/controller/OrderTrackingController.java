package com.shop.iesvdc.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.iesvdc.shop.model.PurchaseOrder;
import com.shop.iesvdc.shop.repos.ClothesRepo;
import com.shop.iesvdc.shop.repos.OrderTrackingRepo;
import com.shop.iesvdc.shop.repos.PurchaseOrderRepo;

@Controller
@RequestMapping("/tracking")
public class OrderTrackingController {
    @Autowired
    private OrderTrackingRepo orderTrackingRepo;

    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    private ClothesRepo clothesRepo;


    @GetMapping("")
    public String findAll(Model model) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepo.findAll();
        for (PurchaseOrder order : purchaseOrders) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("User: " + order.getUser().getUsername());
            System.out.println("Total Price: " + order.getTotalPrice());
            System.out.println("Order Date: " + order.getOrderDate());
        }
        model.addAttribute("orders", purchaseOrders);
        return "orders/orders";
    }


}
