package com.shop.iesvdc.shop.controller;

import com.shop.iesvdc.shop.model.OrderTracking;
import com.shop.iesvdc.shop.repos.OrderTrackingRepo;
import com.shop.iesvdc.shop.repos.ClothesRepo;
import com.shop.iesvdc.shop.repos.PurchaseOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Esta clase es la encargada de manejar todos los relacionados con el seguimiento del pedido
 * CRUD completo
 * @author Balbino Moyano Lopez
 */
@Controller
@RequestMapping("/order-tracking")
public class OrderTrackingController {

    @Autowired
    private OrderTrackingRepo orderTrackingRepo;

    @Autowired
    private ClothesRepo clothesRepo;

    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    @GetMapping("")
    public String findAll(Model model) {
        List<OrderTracking> orderTrackings = orderTrackingRepo.findAll();
        model.addAttribute("orderTrackings", orderTrackings);
        return "order-tracking/order-tracking";
    }

    @GetMapping("/add")
    public String addOrderTracking(Model model) {
        model.addAttribute("orderTracking", new OrderTracking());
        model.addAttribute("clothesList", clothesRepo.findAll());
        model.addAttribute("purchaseOrders", purchaseOrderRepo.findAll());
        return "order-tracking/add";
    }

    @PostMapping("/add")
    public String addForm(@ModelAttribute("orderTracking") OrderTracking orderTracking) {
        orderTrackingRepo.save(orderTracking);
        return "redirect:/order-tracking";
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(Model model, @PathVariable("id") @NonNull Long id) {
        Optional<OrderTracking> orderTracking = orderTrackingRepo.findById(id);
        if (orderTracking.isPresent()) {
            model.addAttribute("orderTracking", orderTracking.get());
            return "order-tracking/delete";
        } else {
            return "error";
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            OrderTracking orderTracking = orderTrackingRepo.findById(id).orElse(null);
            if (orderTracking != null) {
                orderTrackingRepo.delete(orderTracking);
                return ResponseEntity.ok("Seguimiento de pedido eliminado correctamente");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar el seguimiento de pedido: " + e.getMessage());
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<OrderTracking> orderTracking = orderTrackingRepo.findById(id);
        if (orderTracking.isPresent()) {
            model.addAttribute("orderTracking", orderTracking.get());
            model.addAttribute("clothesList", clothesRepo.findAll());
            model.addAttribute("purchaseOrders", purchaseOrderRepo.findAll());
            return "order-tracking/edit";
        } else {
            model.addAttribute("message", "Seguimiento de pedido no encontrado");
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute("orderTracking") OrderTracking orderTracking) {
        orderTrackingRepo.save(orderTracking);
        return "redirect:/order-tracking";
    }
}
