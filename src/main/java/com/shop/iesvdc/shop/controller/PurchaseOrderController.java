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

/**
 * Esta clase es la encargada de manejar todos los endpoints que se encuentran en mi carpeta templates/orders
 * 
 * @author Balbino Moyano Lopez
 */
@Controller
@RequestMapping("/orders")
public class PurchaseOrderController {

    /**
     * Necesitamos la orden de compra
     */
    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    /**
     * Necesitamos la ropa que tiene el pedido
     */
    @Autowired
    private ClothesRepo clothesRepo;

    /**
     * Necesitamos el usuario que hace la compra
     */
    @Autowired
    private UserRepo userRepo;
    
    /**
     * Necesitamos el seguimiento del pedido para ver que ropa tiene cada pedido,estado,talla...
     */
    @Autowired
    private OrderTrackingRepo orderTrackingRepo;

    /**
     * Encuentra todos los seguimientos de pedido
     * @param model
     * @return 
     */
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
        purchaseOrderRepo.save(purchaseOrder);
        return "redirect:/orders";
    }


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
            purchaseOrderRepo.save(purchaseOrder);
            return ResponseEntity.ok("Detalles de ropa actualizados correctamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Metodo que hace que podamos cambiar el pedido de status a enviado
     * @param id
     * @return
     */
    @PostMapping("/ship/{id}")
    @ResponseBody
    public ResponseEntity<String> shipOrder(@PathVariable Long id) {
        try {
            Optional<PurchaseOrder> optionalOrder = purchaseOrderRepo.findById(id);
            if (optionalOrder.isPresent()) {
                PurchaseOrder order = optionalOrder.get();
                System.out.println("PurchaseOrder found: " + order);
    
                List<OrderTracking> existingTrackings = orderTrackingRepo.findByPurchaseOrder(order);
                OrderTracking tracking;
    
                if (!existingTrackings.isEmpty()) {
                    /** Aquí manejamos múltiples resultados, por ejemplo, actualizando todos */
                    for (OrderTracking existingTracking : existingTrackings) {
                        existingTracking.setStatus("ENVIADO");
                        existingTracking.setOrderDate(LocalDate.now().toString());
                        orderTrackingRepo.save(existingTracking);
                        System.out.println("OrderTracking updated: " + existingTracking);
                    }
                    tracking = existingTrackings.get(0);
                } else {
                    /** Si no existe, crea uno nuevo */                    tracking = new OrderTracking();
                    tracking.setStatus("ENVIADO");
                    tracking.setOrderDate(LocalDate.now().toString());
                    tracking.setPurchaseOrder(order);
                    orderTrackingRepo.save(tracking);
                    System.out.println("New OrderTracking created and saved: " + tracking);
                }
    
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
