package com.shop.iesvdc.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.iesvdc.shop.model.OrderTracking;
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
        List<OrderTracking> orderTrackList = orderTrackingRepo.findAll();
        model.addAttribute("tracking", orderTrackList);
        model.addAttribute("order", );
        model.addAttribute("tracking", orderTrackList);
        model.addAttribute("tracking", orderTrackList);
        return "tracking/tracking";
    }


}
