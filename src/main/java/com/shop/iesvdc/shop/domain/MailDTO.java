package com.shop.iesvdc.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Esta clase es la encargada de manejar que el correo que se envia pase a ser un json
 * @author Balbino Moyano Lopez
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {
    private String[] to;
    private String subject;
    private String message;
}
