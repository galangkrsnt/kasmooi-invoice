package com.kasmooi.invoice.service;

import com.kasmooi.invoice.constant.ResponseCode;
import com.kasmooi.invoice.constant.ResponseMessage;
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
import com.kasmooi.invoice.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
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

    @Test
    void getAllCompanies_shouldReturnSuccessResponse() {
        Company company1 = new Company();
        company1.setId(UUID.randomUUID());
        company1.setName("PT Kasmooi Digital");

        Company company2 = new Company();
        company2.setId(UUID.randomUUID());
        company2.setName("PT Techno Jaya");

        List<Company> companyList = List.of(company1, company2);

        CompanyGetResponseDto dto1 = new CompanyGetResponseDto();
        dto1.setId(company1.getId());
        dto1.setName(company1.getName());

        CompanyGetResponseDto dto2 = new CompanyGetResponseDto();
        dto2.setId(company2.getId());
        dto2.setName(company2.getName());

        List<CompanyGetResponseDto> dtoList = List.of(dto1, dto2);

        GenericResponseDto<List<CompanyGetResponseDto>> expectedResponse = new GenericResponseDto<>();
        expectedResponse.setResponseCode("200");
        expectedResponse.setResponseMessage("Success");
        expectedResponse.setData(dtoList);

        Mockito.when(companyRepository.findAll()).thenReturn(companyList);
        Mockito.when(companyMapper.toCompanyResponseDtoList(companyList)).thenReturn(dtoList);
        Mockito.when(genericMapper.toGenericResponse(
                ResponseCode.SUCCESS,
                ResponseMessage.SUCCESS,
                dtoList
        )).thenReturn(expectedResponse);
        GenericResponseDto<List<CompanyGetResponseDto>> actualResponse = companyService.getAllCompanies();

        assertEquals(ResponseCode.SUCCESS, actualResponse.getResponseCode());
        assertEquals(ResponseMessage.SUCCESS, actualResponse.getResponseMessage());
        assertNotNull(actualResponse.getData());
        assertEquals(2, actualResponse.getData().size());
        assertEquals("PT Kasmooi Digital", actualResponse.getData().get(0).getName());
    }

    @Test
    void testGetCompanyById_Success() {
        UUID id = UUID.randomUUID();

        Company company = new Company();
        company.setId(id);
        company.setName("Kasmooi Tech");
        company.setAddress("Jl. Merdeka No.123, Jakarta");
        company.setPhone("+62-812-3456-7890");
        company.setEmail("info@kasmooi.com");
        company.setTaxNumber("NPWP-12.345.678.9-012.000");

        CompanyGetResponseDto responseDto = new CompanyGetResponseDto();
        responseDto.setId(id);
        responseDto.setName(company.getName());
        responseDto.setAddress(company.getAddress());
        responseDto.setPhone(company.getPhone());
        responseDto.setEmail(company.getEmail());
        responseDto.setTaxNumber(company.getTaxNumber());

        GenericResponseDto<CompanyGetResponseDto> expectedResponse = new GenericResponseDto<>();
        expectedResponse.setResponseCode(ResponseCode.SUCCESS);
        expectedResponse.setResponseMessage(ResponseMessage.SUCCESS);
        expectedResponse.setData(responseDto);

        when(companyRepository.findById(id)).thenReturn(java.util.Optional.of(company));
        when(companyMapper.toCompanyResponseDto(company)).thenReturn(responseDto);
        when(genericMapper.toGenericResponse(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto)).thenReturn(expectedResponse);

        GenericResponseDto<CompanyGetResponseDto> result = companyService.getCompanyById(id);

        assertNotNull(result);
        assertEquals(ResponseCode.SUCCESS, result.getResponseCode());
        assertEquals(ResponseMessage.SUCCESS, result.getResponseMessage());
        assertEquals(responseDto.getId(), result.getData().getId());

        verify(companyRepository, times(1)).findById(id);
    }

    @Test
    void testGetCompanyById_NotFound() {
        UUID id = UUID.randomUUID();

        when(companyRepository.findById(id)).thenReturn(java.util.Optional.empty());

        GenericResponseDto<CompanyGetResponseDto> expectedResponse = new GenericResponseDto<>();
        expectedResponse.setResponseCode(ResponseCode.NOT_FOUND);
        expectedResponse.setResponseMessage(ResponseMessage.COMPANY_NOT_FOUND);
        expectedResponse.setData(null);

        Mockito.<GenericResponseDto<CompanyGetResponseDto>>when(
                genericMapper.toGenericResponse(ResponseCode.NOT_FOUND, ResponseMessage.COMPANY_NOT_FOUND, null))
                .thenReturn(expectedResponse);

        GenericResponseDto<CompanyGetResponseDto> result = companyService.getCompanyById(id);

        assertNotNull(result);
        assertEquals(ResponseCode.NOT_FOUND, result.getResponseCode());
        assertEquals(ResponseMessage.COMPANY_NOT_FOUND, result.getResponseMessage());
        assertNull(result.getData());

        verify(companyRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateCompany_Success() {
        UUID id = UUID.randomUUID();

        CompanyUpdateRequestDto requestDto = new CompanyUpdateRequestDto();
        requestDto.setName("Updated Co");
        requestDto.setAddress("New Address");
        requestDto.setPhone("+62-811-1111-2222");
        requestDto.setEmail("new@email.com");
        requestDto.setTaxNumber("NPWP-00.111.222.3-444.000");

        Company existingCompany = new Company();
        existingCompany.setId(id);
        existingCompany.setName("Old Name");

        Company updatedCompany = new Company();
        updatedCompany.setId(id);
        updatedCompany.setName(requestDto.getName());
        updatedCompany.setAddress(requestDto.getAddress());
        updatedCompany.setPhone(requestDto.getPhone());
        updatedCompany.setEmail(requestDto.getEmail());
        updatedCompany.setTaxNumber(requestDto.getTaxNumber());

        CompanyUpdateResponseDto responseDto = new CompanyUpdateResponseDto();
        responseDto.setId(id);
        responseDto.setName(requestDto.getName());
        responseDto.setAddress(requestDto.getAddress());
        responseDto.setPhone(requestDto.getPhone());
        responseDto.setEmail(requestDto.getEmail());
        responseDto.setTaxNumber(requestDto.getTaxNumber());

        GenericResponseDto<CompanyUpdateResponseDto> expectedResponse = new GenericResponseDto<>();
        expectedResponse.setResponseCode(ResponseCode.SUCCESS);
        expectedResponse.setResponseMessage("Company data updated successfully");
        expectedResponse.setData(responseDto);

        when(companyRepository.findById(id)).thenReturn(Optional.of(existingCompany));
        doAnswer(invocation -> {
            CompanyUpdateRequestDto dto = invocation.getArgument(0);
            Company entity = invocation.getArgument(1);
            entity.setName(dto.getName());
            entity.setAddress(dto.getAddress());
            entity.setPhone(dto.getPhone());
            entity.setEmail(dto.getEmail());
            entity.setTaxNumber(dto.getTaxNumber());
            return null;
        }).when(companyMapper).updateCompanyFromDto(any(), any());

        when(companyRepository.save(any())).thenReturn(updatedCompany);
        when(companyMapper.toUpdateResponseDto(updatedCompany)).thenReturn(responseDto);

        when(genericMapper.toGenericResponse(
                eq(ResponseCode.SUCCESS),
                eq("Company data updated successfully"),
                eq(responseDto)))
                .thenReturn(expectedResponse);

        GenericResponseDto<CompanyUpdateResponseDto> result = companyService.updateCompany(id, requestDto);

        assertEquals(ResponseCode.SUCCESS, result.getResponseCode());
        assertEquals("Company data updated successfully", result.getResponseMessage());
        assertNotNull(result.getData());
        assertEquals("Updated Co", result.getData().getName());

        verify(companyRepository, times(1)).findById(id);
        verify(companyRepository, times(1)).save(existingCompany);
    }

    @Test
    void deleteCompanyById_Success() {
        UUID id = UUID.randomUUID();

        when(companyRepository.existsById(id)).thenReturn(true);
        doNothing().when(companyRepository).deleteById(id);

        GenericResponseDto<Void> successResponse = new GenericResponseDto<>();
        successResponse.setResponseCode(ResponseCode.SUCCESS);
        successResponse.setResponseMessage("Company data deleted");
        successResponse.setData(null);

        when(genericMapper.<Void>toGenericResponse(
                eq(ResponseCode.SUCCESS),
                eq("Company data deleted"),
                isNull()))
                .thenReturn(successResponse);

        GenericResponseDto<Void> actualResponse = companyService.deleteCompanyById(id);

        assertNotNull(actualResponse);
        assertEquals(ResponseCode.SUCCESS, actualResponse.getResponseCode());
        assertEquals("Company data deleted", actualResponse.getResponseMessage());
        assertNull(actualResponse.getData());

        verify(companyRepository).existsById(id);
        verify(companyRepository).deleteById(id);
    }
}
