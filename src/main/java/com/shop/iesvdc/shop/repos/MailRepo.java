package com.shop.iesvdc.shop.repos;

/**
 * Esta clase es la interfaz MailRepository encargada de los metodos necesarios para enviar un email
 * @author Balbino Moyano Lopez
 */
public interface MailRepo {
    void sendMail(String[] to,String subject,String message);
}
