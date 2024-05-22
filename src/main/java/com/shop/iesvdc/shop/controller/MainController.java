package com.shop.iesvdc.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
    
    /*Seguramente luego cambie el nombre de los endpoints... */
    @GetMapping("/help")
    public String showHelp(){
        return "help";
    }

    /*Cambiar nombre del endpoint */
    @GetMapping("/acerca")
    public String showAcerca() {
        return "acerca";
    }

    @GetMapping("/error")
    public String showError() {
        return "error";
    }

    @GetMapping("/index")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/inicio")
    public String showMain() {
        return "default";
    }

    @GetMapping("/menu")
    public String show() {
        return "menu";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
    
    @GetMapping("/signup")
    public String showSignUp() {
        return "signup";
    }



    // @GetMapping("/login")
    // public String showLogin() {
    //     return "login";
    // }
    
    // @GetMapping("/signup")
    // public String showSignup() {
    //     return "signup";
    // }


    /*Si el usuario no tiene los permisos necesarios,lo redirigimos aquí */
    /*Cambiar nombre del endpoint */
    @GetMapping("/denegado")
    public String showDenegado() {
        return "denegado";
    }
    
    
    
    
}
