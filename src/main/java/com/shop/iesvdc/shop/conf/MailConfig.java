package com.shop.iesvdc.shop.conf;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Uso esta clase para indicar los protocolos y credenciales que voy a necesitar para la lógica del envio de correo 
 * 
 * @author Balbino Moyano Lopez 
 */
@Configuration
public class MailConfig {
    
    /*
     * Recogemos el valor del application.properties para ver quien es el host,mail...
     */
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String mail;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.password}")
    private String password;


    /*
     * Configuramos una Bean para indicar el puerto,quién manda el correo,quien lo recibe,credenciales...
     */
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(mail);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        //Configuramos el protocolo smtp que es el que usa gmail de forma predeterminada
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
