package com.kasmooi.invoice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasmooi.invoice.model.dto.request.company.CompanyCreateRequestDto;
import com.kasmooi.invoice.model.dto.response.GenericResponseDto;
import com.kasmooi.invoice.model.dto.response.company.CompanyCreateResponseDto;
import com.kasmooi.invoice.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

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
}