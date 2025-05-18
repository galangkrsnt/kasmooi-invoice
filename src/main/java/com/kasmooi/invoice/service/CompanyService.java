package com.kasmooi.invoice.service;

import com.kasmooi.invoice.model.dto.request.company.CompanyCreateRequestDto;
import com.kasmooi.invoice.model.dto.response.GenericResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyCreateResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyGetResponseDto;

import java.util.List;
import java.util.UUID;

public interface CompanyService {
    GenericResponseDto<CompanyCreateResponseDto> createCompany(CompanyCreateRequestDto companyCreateRequestDto);

    GenericResponseDto<List<CompanyGetResponseDto>> getAllCompanies();

    GenericResponseDto<CompanyGetResponseDto> getCompanyById(UUID id);
}
