package com.kasmooi.invoice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasmooi.invoice.model.dto.request.company.CompanyCreateRequestDto;
import com.kasmooi.invoice.model.dto.response.GenericResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyCreateResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyGetResponseDto;
import com.kasmooi.invoice.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCompany_success() throws Exception {
        CompanyCreateRequestDto request = new CompanyCreateRequestDto();
        request.setName("Kasmooi Tech");
        request.setAddress("Jl. Merdeka No.123, Jakarta, Indonesia");
        request.setPhone("+62-812-3456-7890");
        request.setEmail("info@kasmooi.com");
        request.setTaxNumber("NPWP-12.345.678.9-012.000");

        CompanyCreateResponseDto responseDto = new CompanyCreateResponseDto();
        responseDto.setId(UUID.randomUUID());
        responseDto.setName(request.getName());

        GenericResponseDto<CompanyCreateResponseDto> response = new GenericResponseDto<>();
        response.setResponseCode("201");
        response.setResponseMessage("Company created successfully");
        response.setData(responseDto);

        Mockito.when(companyService.createCompany(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/company/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseCode").value("201"))
                .andExpect(jsonPath("$.responseMessage").value("Company created successfully"))
                .andExpect(jsonPath("$.data.name").value("Kasmooi Tech"));
    }

    @Test
    void getAllCompanies_shouldReturnList() throws Exception {
        CompanyGetResponseDto company1 = new CompanyGetResponseDto();
        company1.setId(UUID.randomUUID());
        company1.setName("PT Kasmooi Digital");
        company1.setTaxNumber("12.345.678.9-012.345");

        CompanyGetResponseDto company2 = new CompanyGetResponseDto();
        company2.setId(UUID.randomUUID());
        company2.setName("PT Techno Jaya");
        company2.setTaxNumber("98.765.432.1-098.765");

        List<CompanyGetResponseDto> companyList = List.of(company1, company2);

        GenericResponseDto<List<CompanyGetResponseDto>> response = new GenericResponseDto<>();
        response.setResponseCode("200");
        response.setResponseMessage("Companies retrieved successfully");
        response.setData(companyList);

        Mockito.when(companyService.getAllCompanies()).thenReturn(response);

        mockMvc.perform(get("/api/v1/company/get-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value("200"))
                .andExpect(jsonPath("$.responseMessage").value("Companies retrieved successfully"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name").value("PT Kasmooi Digital"))
                .andExpect(jsonPath("$.data[1].name").value("PT Techno Jaya"));
    }

    @Test
    void testGetCompanyById_Success() throws Exception {
        UUID id = UUID.randomUUID();

        CompanyGetResponseDto companyGetResponseDto = new CompanyGetResponseDto();
        companyGetResponseDto.setId(id);
        companyGetResponseDto.setName("Kasmooi Tech");
        companyGetResponseDto.setAddress("Jl. Merdeka No.123, Jakarta");
        companyGetResponseDto.setPhone("+62-812-3456-7890");
        companyGetResponseDto.setEmail("info@kasmooi.com");
        companyGetResponseDto.setTaxNumber("NPWP-12.345.678.9-012.000");

        GenericResponseDto<CompanyGetResponseDto> genericResponse = new GenericResponseDto<>();
        genericResponse.setResponseCode("200");
        genericResponse.setResponseMessage("Success");
        genericResponse.setData(companyGetResponseDto);

        Mockito.when(companyService.getCompanyById(id)).thenReturn(genericResponse);

        mockMvc.perform(get("/api/v1/company/get/{id}", id.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode").value("200"))
                .andExpect(jsonPath("$.responseMessage").value("Success"))
                .andExpect(jsonPath("$.data.id").value(id.toString()))
                .andExpect(jsonPath("$.data.name").value("Kasmooi Tech"));
    }

    @Test
    void testGetCompanyById_NotFound() throws Exception {
        UUID id = UUID.randomUUID();

        GenericResponseDto<CompanyGetResponseDto> genericResponse = new GenericResponseDto<>();
        genericResponse.setResponseCode("404");
        genericResponse.setResponseMessage("Company not found");
        genericResponse.setData(null);

        Mockito.when(companyService.getCompanyById(id)).thenReturn(genericResponse);

        mockMvc.perform(get("/api/v1/company/get/{id}", id.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.responseCode").value("404"))
                .andExpect(jsonPath("$.responseMessage").value("Company not found"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}