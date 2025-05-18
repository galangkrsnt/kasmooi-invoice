package com.kasmooi.invoice.service.impl;

import com.kasmooi.invoice.constant.ResponseCode;
import com.kasmooi.invoice.constant.ResponseMessage;
import com.kasmooi.invoice.helper.Utility;
import com.kasmooi.invoice.mapper.CompanyMapper;
import com.kasmooi.invoice.mapper.GenericMapper;
import com.kasmooi.invoice.model.dto.request.company.CompanyCreateRequestDto;
import com.kasmooi.invoice.model.dto.response.GenericResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyCreateResponseDto;
import com.kasmooi.invoice.model.entity.Company;
import com.kasmooi.invoice.repository.CompanyRepository;
import com.kasmooi.invoice.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final GenericMapper genericMapper;

    @Override
    public GenericResponseDto<CompanyCreateResponseDto> createCompany(CompanyCreateRequestDto companyCreateRequestDto) {
        GenericResponseDto<CompanyCreateResponseDto> response;
        try {
            log.debug("Starting createCompany with request: {}", Utility.jsonString(companyCreateRequestDto));
            Company company = companyMapper.toEntity(companyCreateRequestDto);
            Company savedCompany = companyRepository.save(company);
            CompanyCreateResponseDto responseDto = companyMapper.toResponseDto(savedCompany);
            log.info("Company created successfully: {}", Utility.jsonString(responseDto));
            response = genericMapper.toGenericResponse(ResponseCode.CREATED, ResponseMessage.COMPANY_CREATED, responseDto);
        } catch (Exception e) {
            log.error("Failed to create company", e);
            response = genericMapper.toGenericResponse(ResponseCode.INTERNAL_ERROR, ResponseMessage.SERVER_ERROR, null);
        }
        return response;
    }

}
