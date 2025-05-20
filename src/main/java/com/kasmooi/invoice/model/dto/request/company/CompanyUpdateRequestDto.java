package com.kasmooi.invoice.model.dto.request.company;

import lombok.Data;

@Data
public class CompanyUpdateRequestDto {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String taxNumber;
}