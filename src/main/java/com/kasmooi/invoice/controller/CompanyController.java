package com.kasmooi.invoice.controller;

import com.kasmooi.invoice.constant.ResponseCode;
import com.kasmooi.invoice.model.dto.request.company.CompanyCreateRequestDto;
import com.kasmooi.invoice.model.dto.request.company.CompanyUpdateRequestDto;
import com.kasmooi.invoice.model.dto.response.GenericResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyCreateResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyGetResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyUpdateResponseDto;
import com.kasmooi.invoice.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/create")
    public ResponseEntity<GenericResponseDto<CompanyCreateResponseDto>> createCompany(
            @Valid @RequestBody CompanyCreateRequestDto request) {
        GenericResponseDto<CompanyCreateResponseDto> response = companyService.createCompany(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<GenericResponseDto<List<CompanyGetResponseDto>>> getAllCompanies() {
        GenericResponseDto<List<CompanyGetResponseDto>> companies = companyService.getAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }

    @GetMapping("{id}")
    public ResponseEntity<GenericResponseDto<CompanyGetResponseDto>> getCompanyById(@PathVariable UUID id) {
        GenericResponseDto<CompanyGetResponseDto> company = companyService.getCompanyById(id);
        if (ResponseCode.NOT_FOUND.equals(company.getResponseCode())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(company);
        }
        return ResponseEntity.status(HttpStatus.OK).body(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponseDto<CompanyUpdateResponseDto>> updateCompany(
            @PathVariable UUID id,
            @RequestBody CompanyUpdateRequestDto requestDto) {
        GenericResponseDto<CompanyUpdateResponseDto> response = companyService.updateCompany(id, requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponseDto<Void>> deleteCompany(@PathVariable UUID id) {
        GenericResponseDto<Void> response = companyService.deleteCompanyById(id);
        if (ResponseCode.NOT_FOUND.equals(response.getResponseCode())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
