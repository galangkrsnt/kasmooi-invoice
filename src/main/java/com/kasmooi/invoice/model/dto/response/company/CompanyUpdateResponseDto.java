package com.kasmooi.invoice.model.dto.response.company;

import lombok.Data;

import java.util.UUID;

@Data
public class CompanyUpdateResponseDto {
    private UUID id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String taxNumber;
}