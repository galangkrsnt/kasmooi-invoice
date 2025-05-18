package com.kasmooi.invoice.service;

import com.kasmooi.invoice.constant.ResponseCode;
import com.kasmooi.invoice.constant.ResponseMessage;
import com.kasmooi.invoice.mapper.CompanyMapper;
import com.kasmooi.invoice.mapper.GenericMapper;
import com.kasmooi.invoice.model.dto.request.company.CompanyCreateRequestDto;
import com.kasmooi.invoice.model.dto.response.GenericResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyCreateResponseDto;
import com.kasmooi.invoice.model.entity.Company;
import com.kasmooi.invoice.repository.CompanyRepository;
import com.kasmooi.invoice.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private GenericMapper genericMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCompany_Success() {
        CompanyCreateRequestDto requestDto = new CompanyCreateRequestDto();
        requestDto.setName("Kasmooi Tech");

        Company company = new Company();
        company.setId(UUID.randomUUID());

        Company savedCompany = new Company();
        savedCompany.setId(UUID.randomUUID());

        CompanyCreateResponseDto responseDto = new CompanyCreateResponseDto();
        responseDto.setId(savedCompany.getId());

        GenericResponseDto<CompanyCreateResponseDto> expectedResponse = new GenericResponseDto<>();
        expectedResponse.setResponseCode(ResponseCode.CREATED);
        expectedResponse.setResponseMessage(ResponseMessage.COMPANY_CREATED);
        expectedResponse.setData(responseDto);

        when(companyMapper.toEntity(requestDto)).thenReturn(company);
        when(companyRepository.save(company)).thenReturn(savedCompany);
        when(companyMapper.toResponseDto(savedCompany)).thenReturn(responseDto);
        when(genericMapper.toGenericResponse(ResponseCode.CREATED, ResponseMessage.COMPANY_CREATED, responseDto)).thenReturn(expectedResponse);

        GenericResponseDto<CompanyCreateResponseDto> result = companyService.createCompany(requestDto);

        assertNotNull(result);
        assertEquals(ResponseCode.CREATED, result.getResponseCode());
        assertEquals(ResponseMessage.COMPANY_CREATED, result.getResponseMessage());
        assertEquals(responseDto.getId(), result.getData().getId());

        verify(companyRepository, times(1)).save(company);
    }
}
