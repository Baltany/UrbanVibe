package com.shop.iesvdc.shop.controller;

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
import com.shop.iesvdc.shop.repos.CategoryRepo;
import com.shop.iesvdc.shop.repos.ClothesRepo;
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
    public String editForm(@PathVariable @NonNull Long id, Model model) {
        Optional<Clothes> clothe = clothesRepo.findById(id);
        List<Clothes> clothes = clothesRepo.findAll();

        if (clothe.isPresent()) {
            model.addAttribute("clothe", clothe.get());
            model.addAttribute("clothes", clothes);
            return "clothes/edit";
        } else {
            model.addAttribute("message", "Clothes not found");
            return "error";
        }
    }

    @PostMapping("/addToCart/{id}")
    public ResponseEntity<?> addToCart(@PathVariable Long id, @RequestParam(required = false) String size) {
        Optional<Clothes> optionalClothes = clothesRepo.findById(id);
        if (optionalClothes.isPresent()) {
            Clothes clothe = optionalClothes.get();
            // Add logic to add the clothe to the cart
            // You may need to add additional attributes to the clothe or create a CartItem object
            // Example response, adjust based on your CartItem and Cart implementation
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
}
