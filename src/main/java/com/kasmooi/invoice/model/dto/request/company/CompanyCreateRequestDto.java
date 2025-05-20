package com.kasmooi.invoice.model.dto.request.company;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyCreateRequestDto {

    @NotBlank(message = "Company name is required")
    private String name;

    private String address;

    @Size(max = 50)
    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 50)
    private String taxNumber;
}