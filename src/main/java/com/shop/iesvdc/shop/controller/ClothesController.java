package com.shop.iesvdc.shop.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.iesvdc.shop.model.Clothes;
import com.shop.iesvdc.shop.model.PurchaseOrder;
import com.shop.iesvdc.shop.model.Size;
import com.shop.iesvdc.shop.model.User;
import com.shop.iesvdc.shop.repos.CategoryRepo;
import com.shop.iesvdc.shop.repos.ClothesRepo;
import com.shop.iesvdc.shop.repos.PurchaseOrderRepo;
import com.shop.iesvdc.shop.repos.SexRepo;
import com.shop.iesvdc.shop.repos.SizeRepo;
import com.shop.iesvdc.shop.repos.UserRepo;

import lombok.NonNull;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Controller
@RequestMapping("/clothes")
public class ClothesController {
    @Autowired
    private SizeRepo sizeRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private SexRepo sexRepo;

    @Autowired
    private ClothesRepo clothesRepo;

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PurchaseOrderRepo orderRepo;

    @GetMapping("")
    public String findAll(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        // Verificar si el usuario está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            
            // Buscar el usuario por nombre de usuario en el repositorio
            Optional<User> userOptional = userRepo.findByUsername(username);
            
            // Verificar si se encontró el usuario
            if (userOptional.isPresent()) {
                // Obtener el usuario desde el Optional
                User user = userOptional.get();
                
                // Obtener el ID del usuario
                Long userId = user.getId();
                
                // Pasar el ID del usuario al modelo para que esté disponible en tu vista
                model.addAttribute("userId", userId);
            } else {
                // Manejar el caso en el que no se encuentra el usuario (podría lanzar una excepción, redirigir, etc.)
                model.addAttribute("message", "No such user");
                return "error";
            }
        } else {
            // Manejar el caso en el que el usuario no está autenticado
            model.addAttribute("message", "User not authenticated");
            return "error";
        }
    
        // Resto del código para cargar la página de ropa
        List<Clothes> lClothes = clothesRepo.findAll();
        model.addAttribute("clothes", lClothes);
        
        return "clothes/clothes";
    }
    
    
    
    @GetMapping("/men")
    public String findAllMen(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        // Verificar si el usuario está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            
            // Buscar el usuario por nombre de usuario en el repositorio
            Optional<User> userOptional = userRepo.findByUsername(username);
            
            // Verificar si se encontró el usuario
            if (userOptional.isPresent()) {
                // Obtener el usuario desde el Optional
                User user = userOptional.get();
                
                // Obtener el ID del usuario
                Long userId = user.getId();
                
                // Pasar el ID del usuario al modelo para que esté disponible en tu vista
                model.addAttribute("userId", userId);
            } else {
                // Manejar el caso en el que no se encuentra el usuario (podría lanzar una excepción, redirigir, etc.)
                model.addAttribute("message", "No such user");
                return "error";
            }
        } else {
            // Manejar el caso en el que el usuario no está autenticado
            model.addAttribute("message", "User not authenticated");
            return "error";
        }
    
        // Resto del código para cargar la página de ropa
        List<Clothes> lClothes = clothesRepo.findAllBySex("men");
        model.addAttribute("clothes", lClothes);
        
        return "clothes/men";
    }

    @GetMapping("/women")
    public String findAllWomen(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        // Verificar si el usuario está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            
            // Buscar el usuario por nombre de usuario en el repositorio
            Optional<User> userOptional = userRepo.findByUsername(username);
            
            // Verificar si se encontró el usuario
            if (userOptional.isPresent()) {
                // Obtener el usuario desde el Optional
                User user = userOptional.get();
                
                // Obtener el ID del usuario
                Long userId = user.getId();
                
                // Pasar el ID del usuario al modelo para que esté disponible en tu vista
                model.addAttribute("userId", userId);
            } else {
                // Manejar el caso en el que no se encuentra el usuario (podría lanzar una excepción, redirigir, etc.)
                model.addAttribute("message", "No such user");
                return "error";
            }
        } else {
            // Manejar el caso en el que el usuario no está autenticado
            model.addAttribute("message", "User not authenticated");
            return "error";
        }
    
        // Resto del código para cargar la página de ropa
        List<Clothes> lClothes = clothesRepo.findAllBySex("women");
        model.addAttribute("clothes", lClothes);
        
        return "clothes/women";
    }

    @GetMapping("/add")
    public String addClothes(Model model) {
        model.addAttribute("clothes", new Clothes());
        model.addAttribute("size", sizeRepo.findAll());
        model.addAttribute("category", categoryRepo.findAll());
        return "clothes/add";
    }

    @PostMapping("/add")
    public String addForm(@ModelAttribute("clothe") @NonNull Clothes clothes) {
        clothesRepo.save(clothes);
        return "redirect:/clothes";
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable(name = "id") @NonNull Long id, Model model) {
        try {
            Optional<Clothes> clothes = clothesRepo.findById(id);
            if (clothes.isPresent()) {
                model.addAttribute("clothe", clothes.get());
                return "clothes/delete";
            } else {
                return "error";
            }
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") @NonNull Long id) {
        try {
            clothesRepo.deleteById(id);
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/clothes";
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<Map<String, Object>> editForm(@PathVariable @NonNull Long id) {
        Optional<Clothes> clothe = clothesRepo.findById(id);
        if (clothe.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            Clothes clothesData = clothe.get();
    
            response.put("id", clothesData.getId());
            response.put("description", clothesData.getDescription());
            response.put("image", clothesData.getImage());
            response.put("price", clothesData.getPrice());
            response.put("size", clothesData.getSizeList().isEmpty() ? null : clothesData.getSizeList().get(0).getSize());
    
            // Obtener todas las tallas desde el repositorio
            List<Size> allSizes = sizeRepo.findAll();
            List<String> allSizeStrings = allSizes.stream()
                    .map(Size::getSize)
                    .collect(Collectors.toList());
            response.put("availableSizes", allSizeStrings);
    
            // Log detallado para depuración
            System.out.println("Response (detailed): " + response);
            System.out.println("All Sizes from Repo: " + allSizeStrings);
    
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Clothes not found"));
        }
    }
    
    
    
    @PostMapping("/addToCart/{id}")
    public ResponseEntity<?> addToCart(@PathVariable Long id, @RequestParam(required = false) String size) {
        Optional<Clothes> optionalClothes = clothesRepo.findById(id);
        if (optionalClothes.isPresent()) {
            Clothes clothe = optionalClothes.get();
            return ResponseEntity.ok().body(Map.of(
                "id", clothe.getId(),
                "description", clothe.getDescription(),
                "price", clothe.getPrice(),
                "image", clothe.getImage(),
                "size", size
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clothes not found");
        }
    }


    //posible cambio a Size size
    @PostMapping("/updateCart/{id}")
    public ResponseEntity<Map<String, Object>> updateCart(@PathVariable Long id, @RequestParam String size) {
        Optional<Clothes> optionalClothes = clothesRepo.findById(id);
        if (optionalClothes.isPresent()) {
            Clothes clothe = optionalClothes.get();
            List<Size> newSizeList = sizeRepo.findBySize(size); // Asumiendo que tienes un método en SizeRepo para encontrar una talla por su nombre
            clothe.setSizeList(newSizeList); // Actualizar la lista de tallas
            clothesRepo.save(clothe); // Guardar cambios en la base de datos
            return ResponseEntity.ok(Map.of(
                "id", clothe.getId(),
                "description", clothe.getDescription(),
                "price", clothe.getPrice(),
                "image", clothe.getImage(),
                "size", clothe.getSizeList()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Clothes not found"));
        }
    }



    /*
     * Endpoint clothes
     */
    @GetMapping("/details/{id}")
    public String seeClothesDetails(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            
            // Buscar el usuario por nombre de usuario en el repositorio
            Optional<User> userOptional = userRepo.findByUsername(username);
            
            // Verificar si se encontró el usuario
            if (userOptional.isPresent()) {
                // Obtener el usuario desde el Optional
                User user = userOptional.get();
                
                // Obtener el ID del usuario
                Long userId = user.getId();
                
                // Pasar el ID del usuario al modelo para que esté disponible en tu vista
                model.addAttribute("userId", userId);
            } else {
                // Manejar el caso en el que el usuario no está autenticado
                model.addAttribute("message", "User not authenticated");
                return "error";
            }
        }
    
        Optional<Clothes> clothe = clothesRepo.findById(id);
        if (clothe.isPresent()) {
            model.addAttribute("clothe", clothe.get());
            return "clothes/details";
        } else {
            return "redirect:/clothes";
        }
    }
    
    
    @PostMapping("/details/{id}")
    public String updateClothesDetails(@PathVariable Long id, Clothes updatedClothes) {
        Optional<Clothes> clothe = clothesRepo.findById(id);
        if (clothe.isPresent()) {
            Clothes existingClothes = clothe.get();
            existingClothes.setDescription(updatedClothes.getDescription());
            existingClothes.setPrice(updatedClothes.getPrice());
            existingClothes.setSizeList(updatedClothes.getSizeList());
            existingClothes.setImage(updatedClothes.getImage());
            clothesRepo.save(existingClothes);
            return "redirect:/details/" + id;
        } else {
            return "redirect:/clothes";
        }
    }    
    

    /*
     * Endpoint men
     */
    @GetMapping("/details/men/{id}")
    public String seeMenClothesDetails(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            
            // Buscar el usuario por nombre de usuario en el repositorio
            Optional<User> userOptional = userRepo.findByUsername(username);
            
            // Verificar si se encontró el usuario
            if (userOptional.isPresent()) {
                // Obtener el usuario desde el Optional
                User user = userOptional.get();
                
                // Obtener el ID del usuario
                Long userId = user.getId();
                
                // Pasar el ID del usuario al modelo para que esté disponible en tu vista
                model.addAttribute("userId", userId);
            } else {
                // Manejar el caso en el que el usuario no está autenticado
                model.addAttribute("message", "User not authenticated");
                return "redirect:/clothes/men";
            }
        }
    
        Optional<Clothes> clothe = clothesRepo.findById(id);
        if (clothe.isPresent()) {
            model.addAttribute("clothe", clothe.get());
            return "clothes/details";
        } else {
            return "redirect:/clothes/men";
        }

    }


    @PostMapping("/details/men/{id}")
    public String updateMenClothesDetails(@PathVariable Long id, Clothes updatedClothes) {
        Optional<Clothes> clothe = clothesRepo.findById(id);
        if (clothe.isPresent()) {
            Clothes existingClothes = clothe.get();
            existingClothes.setDescription(updatedClothes.getDescription());
            existingClothes.setPrice(updatedClothes.getPrice());
            existingClothes.setSizeList(updatedClothes.getSizeList());
            existingClothes.setImage(updatedClothes.getImage());
            clothesRepo.save(existingClothes);
            return "redirect:/clothes/details/men/" + id;
        } else {
            return "redirect:/clothes/men";
        }
    }

    /*
     * Endpoint women
     */
    @GetMapping("/details/women/{id}")
    public String seeWomenClothesDetails(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            
            // Buscar el usuario por nombre de usuario en el repositorio
            Optional<User> userOptional = userRepo.findByUsername(username);
            
            // Verificar si se encontró el usuario
            if (userOptional.isPresent()) {
                // Obtener el usuario desde el Optional
                User user = userOptional.get();
                
                // Obtener el ID del usuario
                Long userId = user.getId();
                
                // Pasar el ID del usuario al modelo para que esté disponible en tu vista
                model.addAttribute("userId", userId);
            } else {
                // Manejar el caso en el que el usuario no está autenticado
                model.addAttribute("message", "User not authenticated");
                return "redirect:/clothes/women";
            }
        }
    
        Optional<Clothes> clothe = clothesRepo.findById(id);
        if (clothe.isPresent()) {
            model.addAttribute("clothe", clothe.get());
            return "clothes/details";
        } else {
            return "redirect:/clothes/women";
        }
    }


    @PostMapping("/details/women/{id}")
    public String updateWomenClothesDetails(@PathVariable Long id, Clothes updatedClothes) {
        Optional<Clothes> clothe = clothesRepo.findById(id);
        if (clothe.isPresent()) {
            Clothes existingClothes = clothe.get();
            existingClothes.setDescription(updatedClothes.getDescription());
            existingClothes.setPrice(updatedClothes.getPrice());
            existingClothes.setSizeList(updatedClothes.getSizeList());
            existingClothes.setImage(updatedClothes.getImage());
            clothesRepo.save(existingClothes);
            return "redirect:/clothes/details/women/" + id;
        } else {
            return "redirect:/clothes/women";
        }
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> userOptional = userRepo.findByUsername(username);
    
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Long userId = user.getId();
                model.addAttribute("userId", userId);
            } else {
                model.addAttribute("message", "User not found");
                return "error";
            }
        } else {
            model.addAttribute("message", "User not authenticated");
            return "error";
        }
    
        return "clothes/orders";
    }
    

    /*
     * Redirigir al endpoint thnku
     */
    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> orderData) {
        try {
            Long userId = Long.parseLong(orderData.get("userId").toString());
            List<Map<String, Object>> cartItems = (List<Map<String, Object>>) orderData.get("cart");
            Double total = Double.parseDouble(orderData.get("total").toString());
    
            // Obtener el usuario
            Optional<User> userOpt = userRepo.findById(userId);
            if (!userOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            User user = userOpt.get();
    
            // Crear una nueva orden de compra
            PurchaseOrder order = new PurchaseOrder();
            order.setTotalPrice(total);
            order.setUser(user);
            order.setOrderDate(LocalDate.now().toString()); // o cualquier lógica que uses para la fecha
    
            // Mapear los items del carrito a los items de la orden
            List<Clothes> clothesList = new ArrayList<>();
            for (Map<String, Object> item : cartItems) {
                Long clothesId = Long.parseLong(item.get("id").toString());
                Optional<Clothes> clothesOpt = clothesRepo.findById(clothesId);
                if (clothesOpt.isPresent()) {
                    Clothes clothes = clothesOpt.get();
                    clothes.setPurchaseOrder(order); // Asigna la orden de compra a la ropa
                    clothes.setStock(clothes.getStock() - 1); // Resta 1 al stock
                    clothesList.add(clothes);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clothes not found: " + clothesId);
                }
            }
            order.setClothesList(clothesList);
    
            PurchaseOrder savedOrder = orderRepo.save(order);
            
            // Retornar una respuesta exitosa con el objeto de la orden creada
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order: " + e.getMessage());
        }
    }
    
    
    
    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            if (user.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID must not be null");
            }
            Optional<User> existingUser = userRepo.findById(user.getId());
            if (existingUser.isPresent()) {
                User updatedUser = existingUser.get();
                updatedUser.setName(user.getName());
                updatedUser.setSurname(user.getSurname());
                updatedUser.setDni(user.getDni());
                updatedUser.setAddress(user.getAddress());
                userRepo.save(updatedUser);
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
        }
    }
    
    
}
