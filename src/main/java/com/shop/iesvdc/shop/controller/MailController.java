package com.shop.iesvdc.shop.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.iesvdc.shop.domain.MailDTO;
import com.shop.iesvdc.shop.service.MailService;

/**
 * Esta clase es la encargada de manejar todos los correos que se envian a través de la app
 * 
 * @author Balbino Moyano Lopez
 */
@RestController
/** Tiene ese nombre porque lo utilice para hacer la simulación de incidencias de usuario */
@RequestMapping("/help")
public class MailController {

    @Autowired
    private MailService emailService;

    /**
     * Manda el correo pasando a json por eso la necesidad del mailDTO
     * @param mailDTO
     * @return
     */
    @PostMapping("/send")
    public ResponseEntity<?> receiveRequestEmail(@RequestBody MailDTO mailDTO){

        System.out.println("Mensaje recibido: "+ mailDTO);

        emailService.sendMail(mailDTO.getTo(), mailDTO.getSubject(), mailDTO.getMessage());
        Map<String,String> response = new HashMap<>();
        response.put("estado","Enviado");

        return (ResponseEntity<?>) ResponseEntity.ok(response);
    }
    

}
