package com.shop.iesvdc.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.shop.iesvdc.shop.repos.MailRepo;
import com.shop.iesvdc.shop.repos.UserRepo;
import com.shop.iesvdc.shop.model.User; // Asegúrate de que esta es la clase correcta de tu modelo de usuario

@Service
public class MailService implements MailRepo {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepo userRepo;

    @Override
    public void sendMail(String[] to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public void sendMailToLoggedInUser(String subject, String message) {
        // Obtiene la autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Verifica que la autenticación no sea nula y el usuario esté autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            // Obtiene los detalles del usuario
            Object principal = authentication.getPrincipal();
            String email = null;

            if (principal instanceof UserDetails) {
                // Si el principal es una instancia de UserDetails, obtén el nombre de usuario (email)
                email = ((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                // Si el principal es simplemente una cadena, úsala como nombre de usuario
                email = principal.toString();
            }

            if (email != null) {
                // Envía el correo al email del usuario logueado
                sendMail(new String[]{email}, subject, message);
            }
        }
    }
}
