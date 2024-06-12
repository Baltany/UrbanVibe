package com.shop.iesvdc.shop.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.iesvdc.shop.model.PurchaseOrder;
import com.shop.iesvdc.shop.model.User;
import com.shop.iesvdc.shop.model.UserRol;
import com.shop.iesvdc.shop.repos.PurchaseOrderRepo;
import com.shop.iesvdc.shop.repos.UserRepo;
import com.shop.iesvdc.shop.repos.UserRolRepo;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired private UserRepo userRepo;

    @Autowired private UserRolRepo userRolRepo;

    @Autowired private PurchaseOrderRepo orderRepo;


    @GetMapping(path = "/")
    public String findAll(Model model) {
        List<User> lUser = userRepo.findAll();
        model.addAttribute("users", lUser);
        return "users/users";

    }


    /*Devuelve  todos los usuarios que existan*/
    @GetMapping("")
    public String findAll2(Model model) {
        return findAll(model);
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userRol", userRolRepo.findAll());
        return "users/add";
    }
    
    
    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, BindingResult result,Model modelo) {
        System.out.println(user.toString());

        if (result.hasErrors()) {
            modelo.addAttribute("titulo","Error al añadir usuario" );
            modelo.addAttribute("mensaje", result.toString());
            // Si hay errores de validación, regresar al formulario de añadir usuario   
            return "error";
        }

        if (userRepo.findByMail(user.getMail()).isPresent()) {
            result.rejectValue("mail", "error.user", "Email en uso");
            return "users/add";
        }

        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            result.rejectValue("username", "error.user", "Nombre de usuario en uso");
            return "users/add";
        }

        // Guardar el usuario en la base de datos si cumple las condiciones anteriores
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepo.save(user);

        // Redirigir al listado de usuarios después de añadir uno nuevo
        return "redirect:/users";
    }


    @GetMapping("/signup")
    public String signupUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userRol", userRolRepo.findAll());
        return "users/signup";
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute("user") @Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }

        if (userRepo.findByMail(user.getMail()).isPresent()) {
            result.rejectValue("mail", "error.user", "Email en uso");
            return "users/signup";
        }

        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            result.rejectValue("username", "error.user", "Nombre de usuario en uso");
            return "users/signup";
        }
    
        /** Buscar el rol "Customer" en la base de datos */
        UserRol customerRole = userRolRepo.findByRol("Customer");
        if (customerRole == null) {
            /** Manejar el caso donde el rol "Customer" no fue encontrado */
            throw new RuntimeException("Default role 'Customer' not found");
        }
    
        /** Obtener la lista de roles del usuario o inicializarla si es null */
        List<UserRol> userRoles = user.getRolList();
        if (userRoles == null) {
            userRoles = new ArrayList<>();
        }
    
        /** Agregar el rol "Customer" a la lista de roles del usuario si no está presente */
        if (!userRoles.contains(customerRole)) {
            userRoles.add(customerRole);
        }
    
        /** Establecer la lista actualizada de roles en el usuario */
        user.setRolList(userRoles);
    
        /** Codificar la contraseña antes de guardarla */
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setMail(user.getMail());
        user.setEnable(true);


    
        try {
            // Guardar el usuario en la base de datos
            userRepo.save(user);
        } catch (Exception e) {
            // Manejar cualquier error durante el guardado del usuario
            return "error";
        }
    
        // Redirigir al usuario a la página de login (o a donde sea apropiado)
        return "redirect:/login";
    }



    @GetMapping("/delete/{id}")
    public String deleteUserForm(Model model, @PathVariable("id") @NonNull Long id) {
        Optional<User> oUser = userRepo.findById(id);
        if (oUser.isPresent()) {
            model.addAttribute("user", oUser.get());
            return "users/delete";
        } else {
            model.addAttribute("message", "No such user");
            return "error";
            
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        try{
            User user = userRepo.findById(id).orElse(null);
            if(user != null){
                userRepo.delete(user);
                return ResponseEntity.ok("Usuario eliminado correctamente");
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body("Error al eliminar el usuario " + e.getMessage());
        }
    }



    @GetMapping("/edit/{id}")
    public String editForm(Model model,@PathVariable("id") @NonNull Long id) {
        Optional<User> oUser = userRepo.findById(id);
        if(oUser.isPresent()){
            User user = oUser.get();
            model.addAttribute("user", user);
            model.addAttribute("userRolList", userRolRepo.findAll());
            return "users/edit";
        }else{
            model.addAttribute("mensaje", "El usuario seleccionado no se ha podido actualizar");
            return "error";
        }
    }
    

    @PostMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") @Validated User updatedUser,
                             BindingResult bindingResult,
                             @PathVariable("id") Long id,
                             Model model) {
    
        if (bindingResult.hasErrors()) {
            return "error";
        }
    
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
    
            if (!existingUser.getMail().equals(updatedUser.getMail()) && userRepo.findByMail(updatedUser.getMail()).isPresent()) {
                bindingResult.rejectValue("mail", "error.user", "Email en uso");
                return "users/edit";
            }
    
            if (!existingUser.getUsername().equals(updatedUser.getUsername()) && userRepo.findByUsername(updatedUser.getUsername()).isPresent()) {
                bindingResult.rejectValue("username", "error.user", "Nombre de usuario en uso");
                return "users/edit";
            }
    
            if (!existingUser.getDni().equals(updatedUser.getDni()) && userRepo.findByDni(updatedUser.getDni()).isPresent()) {
                bindingResult.rejectValue("dni", "error.user", "DNI en uso");
                return "users/edit";
            }
    
            if (updatedUser.getUsername() != null) {
                existingUser.setUsername(updatedUser.getUsername());
            }
    
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                String password = updatedUser.getPassword();
                if (!password.equals(existingUser.getPassword())) {
                    String encodingPass = new BCryptPasswordEncoder().encode(password);
                    existingUser.setPassword(encodingPass);
                } else {
                    existingUser.setPassword(updatedUser.getPassword());
                }
            }
    
            if (updatedUser.getName() != null) {
                existingUser.setName(updatedUser.getName());
            }
    
            if (updatedUser.getSurname() != null) {
                existingUser.setSurname(updatedUser.getSurname());
            }
    
            if (updatedUser.getMail() != null) {
                existingUser.setMail(updatedUser.getMail());
            }
    
            if (updatedUser.getDni() != null) {
                existingUser.setDni(updatedUser.getDni());
            }
    
            if (updatedUser.getAddress() != null) {
                existingUser.setAddress(updatedUser.getAddress());
            }
    
            existingUser.getRolList().clear();
    
            List<UserRol> updatedRoles = updatedUser.getRolList();
            if (updatedRoles != null && !updatedRoles.isEmpty()) {
                existingUser.getRolList().addAll(updatedRoles);
            }
    
            userRepo.save(existingUser);
    
            return "redirect:/users";
        } else {
            model.addAttribute("mensaje", "El usuario no se pudo actualizar.");
            return "error";
        }
    }
    
    

}
