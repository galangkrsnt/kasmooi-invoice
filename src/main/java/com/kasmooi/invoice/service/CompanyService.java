package com.kasmooi.invoice.service;

import com.kasmooi.invoice.model.dto.request.company.CompanyCreateRequestDto;
import com.kasmooi.invoice.model.dto.response.GenericResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyCreateResponseDto;

public interface CompanyService {
    public GenericResponseDto<CompanyCreateResponseDto> createCompany(CompanyCreateRequestDto companyCreateRequestDto);

}
