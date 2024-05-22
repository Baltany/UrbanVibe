package com.shop.iesvdc.shop.repos;

public interface MailRepo {
    void sendMail(String[] to,String subject,String message);
}
