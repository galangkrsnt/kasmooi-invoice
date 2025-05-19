package com.kasmooi.invoice.service.impl;

import com.kasmooi.invoice.constant.ResponseCode;
import com.kasmooi.invoice.constant.ResponseMessage;
import com.kasmooi.invoice.helper.Utility;
import com.kasmooi.invoice.mapper.CompanyMapper;
import com.kasmooi.invoice.mapper.GenericMapper;
import com.kasmooi.invoice.model.dto.request.company.CompanyCreateRequestDto;
import com.kasmooi.invoice.model.dto.request.company.CompanyUpdateRequestDto;
import com.kasmooi.invoice.model.dto.response.GenericResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyCreateResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyGetResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyUpdateResponseDto;
import com.kasmooi.invoice.model.entity.Company;
import com.kasmooi.invoice.repository.CompanyRepository;
import com.kasmooi.invoice.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public GenericResponseDto<List<CompanyGetResponseDto>> getAllCompanies() {
        GenericResponseDto<List<CompanyGetResponseDto>> response;
        try {
            log.debug("Fetching all companies");
            List<Company> companies = companyRepository.findAll();
            List<CompanyGetResponseDto> companyGetResponseDtoList = companyMapper.toCompanyResponseDtoList(companies);
            log.info("Found {} companies", companies.size());
            response = genericMapper.toGenericResponse(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, companyGetResponseDtoList);
        } catch (Exception e) {
            log.error("Failed to fetch companies", e);
            response = genericMapper.toGenericResponse(ResponseCode.INTERNAL_ERROR, ResponseMessage.SERVER_ERROR, null);
        }
        return response;
    }

    @Override
    public GenericResponseDto<CompanyGetResponseDto> getCompanyById(UUID id) {
        GenericResponseDto<CompanyGetResponseDto> response;
        try {
            log.debug("Fetching company with ID: {}", id);
            Company company = companyRepository.findById(id).orElse(null);
            if (null == company) {
                log.warn("Company not found with ID: {}", id);
                return genericMapper.toGenericResponse(ResponseCode.NOT_FOUND, ResponseMessage.COMPANY_NOT_FOUND, null);
            }
            CompanyGetResponseDto companyResponseDto = companyMapper.toCompanyResponseDto(company);
            log.info("Found company: {}", Utility.jsonString(companyResponseDto));
            response = genericMapper.toGenericResponse(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, companyResponseDto);
        } catch (Exception e) {
            log.error("Failed to fetch company by ID", e);
            response = genericMapper.toGenericResponse(ResponseCode.INTERNAL_ERROR, ResponseMessage.SERVER_ERROR, null);
        }
        return response;
    }

    @Override
    public GenericResponseDto<CompanyUpdateResponseDto> updateCompany(UUID id, CompanyUpdateRequestDto requestDto) {
        GenericResponseDto<CompanyUpdateResponseDto> response;
        try {
            log.debug("Updating company with ID: {}, request: {}", id, Utility.jsonString(requestDto));
            Company company = companyRepository.findById(id).orElse(null);
            if (company == null) {
                log.warn("Company not found with ID: {}", id);
                return genericMapper.toGenericResponse(ResponseCode.NOT_FOUND, ResponseMessage.COMPANY_NOT_FOUND, null);
            }

            companyMapper.updateCompanyFromDto(requestDto, company); // update using MapStruct

            Company updatedCompany = companyRepository.save(company);
            CompanyUpdateResponseDto responseDto = companyMapper.toUpdateResponseDto(updatedCompany); // <-- use updated DTO

            log.info("Company updated: {}", Utility.jsonString(responseDto));
            response = genericMapper.toGenericResponse(ResponseCode.SUCCESS, ResponseMessage.COMPANY_UPDATED, responseDto);
        } catch (Exception e) {
            log.error("Failed to update company", e);
            response = genericMapper.toGenericResponse(ResponseCode.INTERNAL_ERROR, ResponseMessage.SERVER_ERROR, null);
        }
        return response;
    }

}
