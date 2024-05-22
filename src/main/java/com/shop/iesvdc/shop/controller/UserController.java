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

    /*
     * Register and Login
     * Logica en el UserSevice
     */
    // @GetMapping("/register")
    // public String registerForm(Model model) {
    //     model.addAttribute("user", new User());
    //     return "register";
    // }

    // @PostMapping("/register")
    // public String register(@ModelAttribute("user") User user) {
    //     userService.register(user);
    //     return "redirect:/login";
    // }

    // @GetMapping("/login")
    // public String loginForm() {
    //     return "login";
    // }

    // @PostMapping("/login")
    // public String login(@RequestParam String username, @RequestParam String password) {
    //     if (userService.login(username, password)) {
    //         /*deberia de redirigir al inicio que no se exactamente que será posiblemnte sería pedidos */
    //         return "redirect:/dashboard";
    //     } else {
    //         return "login";
    //     }
    // }

    /*
      * ----------------------------------------------
      */

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
        // Verificar si hay errores de validación en el formulario
        if (result.hasErrors()) {
            // Manejar errores de validación de forma adecuada (puedes personalizar el mensaje de error)
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
    
        // Buscar el rol "Customer" en la base de datos
        UserRol customerRole = userRolRepo.findByRol("Customer");
        if (customerRole == null) {
            // Manejar el caso donde el rol "Customer" no fue encontrado
            throw new RuntimeException("Default role 'Customer' not found");
        }
    
        // Obtener la lista de roles del usuario o inicializarla si es null
        List<UserRol> userRoles = user.getRolList();
        if (userRoles == null) {
            userRoles = new ArrayList<>();
        }
    
        // Agregar el rol "Customer" a la lista de roles del usuario si no está presente
        if (!userRoles.contains(customerRole)) {
            userRoles.add(customerRole);
        }
    
        // Establecer la lista actualizada de roles en el usuario
        user.setRolList(userRoles);
    
        // Codificar la contraseña antes de guardarla
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
    
    /*Busca y borra el usuario por id */
    // @PostMapping("/delete/{id}")
    // public String deleteUser(@PathVariable("id") @NonNull Long id) {
    //     userRepo.deleteById(id);
    //     return "redirect:/users";
    // }

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

    
            // Actualizar el username solo si se proporciona un valor no nulo
            if (updatedUser.getUsername() != null) {
                existingUser.setUsername(updatedUser.getUsername());
            }

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                String password = updatedUser.getPassword();
                if(!password.equals(existingUser.getPassword())){
                    String encodingPass = new BCryptPasswordEncoder().encode(password);
                    existingUser.setPassword(encodingPass);
                }

                existingUser.setPassword(updatedUser.getPassword());
            }
    
            // Actualizar el name solo si se proporciona un valor no nulo
            if (updatedUser.getName() != null) {
                existingUser.setName(updatedUser.getName());
            }
    
            // Actualizar el surname solo si se proporciona un valor no nulo
            if (updatedUser.getSurname() != null) {
                existingUser.setSurname(updatedUser.getSurname());
            }
    
            // Actualizar el mail solo si se proporciona un valor no nulo
            if (updatedUser.getMail() != null) {
                existingUser.setMail(updatedUser.getMail());
            }
    
            // Actualizar el dni solo si se proporciona un valor no nulo
            if (updatedUser.getDni() != null) {
                existingUser.setDni(updatedUser.getDni());
            }
    
            // Actualizar la address solo si se proporciona un valor no nulo
            if (updatedUser.getAddress() != null) {
                existingUser.setAddress(updatedUser.getAddress());
            }
    
            // Actualizar la habilitación solo si se proporciona un valor no nulo
            // if (updatedUser.getEnable() != null) {
            //     existingUser.setEnable((boolean) updatedUser.getEnable());
            // }
    
            // Limpiar y actualizar los roles del usuario
            existingUser.getRolList().clear(); // Limpiar la lista de roles existente
    
            List<UserRol> updatedRoles = updatedUser.getRolList();
            if (updatedRoles != null && !updatedRoles.isEmpty()) {
                existingUser.getRolList().addAll(updatedRoles); // Agregar los nuevos roles seleccionados
            } else {
                // Si no se selecciona ningún rol en el formulario, mantener los roles existentes
                // Aquí no hacemos nada, ya que ya se limpió la lista de roles existente
            }
    
            // Guardar el usuario actualizado en la base de datos
            userRepo.save(existingUser);
    
            return "redirect:/users";
        } else {
            model.addAttribute("mensaje", "El usuario no se pudo actualizar.");
            return "error";
        }
    }
    

    /*
     * Ahora hay que hacer el crud de pedidos,
     * un usuario puede crear un pedido...
     */

    /* El mismo cliente crea
    su pedido(Luego en pedido,el mismo pedido añade la ropa que el usuario ha elegido) */
    /*
    @GetMapping("/{id}/orders/add")
    public String orderAddForm(@PathVariable @NonNull Long id, Model model) {
        Optional<User> oUser = userRepo.findById(id);
        if (!oUser.isPresent()) {
            model.addAttribute("message", "No such user");
            return "error";
        }
        model.addAttribute("order", new PurchaseOrder());

        return "users/orders/add";
    }

    @PostMapping("/{id}/orders/add")
    public String orderAdd(
        @PathVariable @NonNull Long id,
        @ModelAttribute("order")PurchaseOrder order,
        Model model
    ) {
        Optional<User> oUser = userRepo.findById(id);

        if (!oUser.isPresent()) {
            model.addAttribute("message", "No such user");
            return "error";
        }

        order.setUser(oUser.get());
        orderRepo.save(order);

        return "redirect:/orders";
    }



    @GetMapping("/{id}/orders")
    public String showOrders(@PathVariable @NonNull long id, Model model) {
        Optional<User> oUser = userRepo.findById(id);
        if (!oUser.isPresent()) {
            model.addAttribute("message", "No such user");
            return "error";
        }

        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("actualUser", oUser.get());
        model.addAttribute("orders", oUser.get().getPurcharseOrderList());
        model.addAttribute("order", new PurchaseOrder());
        return "users/orders/orders";
    }

    @GetMapping("/{idUser}/orders/{orderId}/edit")
    public String orderEditForm(
        @PathVariable @NonNull Long userId,
        @PathVariable @NonNull Long orderId,
        Model model
    ) {
        Optional<User> oUser = userRepo.findById(userId);
        Optional<PurchaseOrder> oOrder = orderRepo.findById(orderId);
        if (!oUser.isPresent() || !oOrder.isPresent()) {
            model.addAttribute("message", "No such user or order");
            return "error";
        }

        model.addAttribute("user", oUser.get());
        model.addAttribute("order", oOrder.get());

        return "users/orders/edit";
    }
*/
}
