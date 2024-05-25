package com.shop.iesvdc.shop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.iesvdc.shop.model.Clothes;
import com.shop.iesvdc.shop.model.Size;
import com.shop.iesvdc.shop.repos.CategoryRepo;
import com.shop.iesvdc.shop.repos.ClothesRepo;
import com.shop.iesvdc.shop.repos.SexRepo;
import com.shop.iesvdc.shop.repos.SizeRepo;

import lombok.NonNull;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("")
    public String findAll(Model model) {
        List<Clothes> lClothes = clothesRepo.findAll();
        model.addAttribute("clothes", lClothes);
        return "clothes/clothes";
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
    
            // Obtener availableSizes desde tu repositorio o donde sea que lo estés obteniendo
            List<Size> availableSizes = sizeRepo.findAll();
            response.put("availableSizes", availableSizes);
    
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


    @GetMapping("/details/{id}")
    public String seeClothesDetails(@PathVariable Long id, Model model) {
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
            // Actualiza los detalles de la ropa
            existingClothes.setDescription(updatedClothes.getDescription());
            existingClothes.setPrice(updatedClothes.getPrice());
            existingClothes.setSizeList(updatedClothes.getSizeList());
            clothesRepo.save(existingClothes);
            return "redirect:/details/" + id;
        } else {
            return "redirect:/clothes";
        }
    }
    
}
