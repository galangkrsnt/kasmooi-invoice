package com.kasmooi.invoice.mapper;

import com.kasmooi.invoice.model.dto.request.company.CompanyCreateRequestDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyCreateResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyGetResponseDto;
import com.kasmooi.invoice.model.entity.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toEntity(CompanyCreateRequestDto dto);

    CompanyCreateResponseDto toResponseDto(Company entity);

    CompanyGetResponseDto toCompanyResponseDto(Company company);

    List<CompanyGetResponseDto> toCompanyResponseDtoList(List<Company> companies);
}
