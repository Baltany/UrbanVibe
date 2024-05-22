package com.shop.iesvdc.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {
    private String[] to;
    private String subject;
    private String message;
}
