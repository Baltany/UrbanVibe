package com.shop.iesvdc.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;

import com.shop.iesvdc.shop.repos.ClothesRepo;
import com.shop.iesvdc.shop.repos.OrderTrackingRepo;
import com.shop.iesvdc.shop.repos.PurchaseOrderRepo;
import com.shop.iesvdc.shop.repos.UserRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.shop.iesvdc.shop.model.Clothes;
import com.shop.iesvdc.shop.model.OrderTracking;
import com.shop.iesvdc.shop.model.PurchaseOrder;
import com.shop.iesvdc.shop.model.User;
import com.shop.iesvdc.shop.repos.ClothesRepo;
import com.shop.iesvdc.shop.repos.UserRepo;
import com.shop.iesvdc.shop.repos.PurchaseOrderRepo;

@Controller
@RequestMapping("/orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    private ClothesRepo clothesRepo;

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private OrderTrackingRepo orderTrackingRepo;

    @GetMapping("")
    public String findAll(Model model) {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepo.findAll();
        for (PurchaseOrder order : purchaseOrders) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("User: " + order.getUser().getUsername());
            System.out.println("Total Price: " + order.getTotalPrice());
            System.out.println("Order Date: " + order.getOrderDate());
            System.out.println("Clothes List: " + order);
        }
        model.addAttribute("orders", purchaseOrders);
        return "orders/orders";
    }



    @GetMapping("/add")
    public String addPurchaseOrder(Model model) {
        model.addAttribute("order", new PurchaseOrder());
        model.addAttribute("clothesList", clothesRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        return "orders/add";
    }

    @PostMapping("/add")
    public String addForm(@ModelAttribute("order") PurchaseOrder purchaseOrder) {
        // TODO: Validar y procesar la solicitud POST
        purchaseOrderRepo.save(purchaseOrder);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(Model model, @PathVariable("id") @NonNull Long id) {

        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepo.findById(id);
        if (purchaseOrder.isPresent()) {
            model.addAttribute("order", purchaseOrder.get());
            return "orders/delete";
        } else {
            return "error";
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try{
            PurchaseOrder order = purchaseOrderRepo.findById(id).orElse(null);
            if(order != null){
                purchaseOrderRepo.delete(order);
                return ResponseEntity.ok("Pedido eliminado correctamente");
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body("Error al eliminar el pedido " + e.getMessage());
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepo.findById(id);
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepo.findAll();
        List<User> users = userRepo.findAll();
        
        if (purchaseOrder.isPresent()) {
            model.addAttribute("order", purchaseOrder.get());
            model.addAttribute("orders", purchaseOrders);
            model.addAttribute("users", users);
            return "orders/edit";
        } else {
            model.addAttribute("message", "Purchase Order not found");
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute("order") PurchaseOrder purchaseOrder) {
        // TODO: Validar y procesar la solicitud POST
        purchaseOrderRepo.save(purchaseOrder);
        return "redirect:/orders";
    }


    /*
     * Para hacer la lógica de una ventana flotante que me muestre los datos del pedido
     */


    /*
    @GetMapping("/clothes/{id}")
    @ResponseBody
    public ResponseEntity<List<Clothes>> getClothesByOrderId(@PathVariable Long id) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepo.findById(id);
        if (purchaseOrder.isPresent()) {
            List<Clothes> clothesList = purchaseOrder.get().getClothesList();
            return ResponseEntity.ok(clothesList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
     * 
     */

    @GetMapping("/clothes/{id}")
    @ResponseBody
    public ResponseEntity<List<Clothes>> getClothesByOrderId(@PathVariable Long id) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepo.findById(id);
        if (purchaseOrder.isPresent()) {
            List<Clothes> clothesList = purchaseOrder.get().getClothesList();
            return ResponseEntity.ok(clothesList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/clothes/{id}")
    @ResponseBody
    public ResponseEntity<String> updateClothesByOrderId(@PathVariable Long id, @RequestBody List<Clothes> updatedClothes) {
        Optional<PurchaseOrder> purchaseOrderOpt = purchaseOrderRepo.findById(id);
        if (purchaseOrderOpt.isPresent()) {
            PurchaseOrder purchaseOrder = purchaseOrderOpt.get();
            //purchaseOrder.setClothesList(updatedClothes);
            purchaseOrderRepo.save(purchaseOrder);
            return ResponseEntity.ok("Detalles de ropa actualizados correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/ship/{id}")
    @ResponseBody
    public ResponseEntity<String> shipOrder(@PathVariable Long id) {
        try {
            Optional<PurchaseOrder> optionalOrder = purchaseOrderRepo.findById(id);
            if (optionalOrder.isPresent()) {
                PurchaseOrder order = optionalOrder.get();
                System.out.println("PurchaseOrder found: " + order);

                // Buscar si ya existe un OrderTracking para este PurchaseOrder
                Optional<OrderTracking> existingTrackingOptional = orderTrackingRepo.findByPurchaseOrder(order);
                OrderTracking tracking;

                if (existingTrackingOptional.isPresent()) {
                    // Si existe, actualiza solo los campos necesarios
                    tracking = existingTrackingOptional.get();
                    tracking.setStatus("ENVIADO");
                    tracking.setOrderDate(LocalDate.now().toString());
                    System.out.println("OrderTracking updated: " + tracking);
                } else {
                    // Si no existe, crea uno nuevo
                    tracking = new OrderTracking();
                    tracking.setStatus("ENVIADO");
                    tracking.setOrderDate(LocalDate.now().toString());
                    tracking.setPurchaseOrder(order);
                    System.out.println("New OrderTracking created: " + tracking);
                }

                // Guardar el OrderTracking actualizado o nuevo
                orderTrackingRepo.save(tracking);
                System.out.println("OrderTracking saved: " + tracking);

                return ResponseEntity.ok("Order shipped successfully");
            } else {
                System.out.println("PurchaseOrder not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }
    
    


}
