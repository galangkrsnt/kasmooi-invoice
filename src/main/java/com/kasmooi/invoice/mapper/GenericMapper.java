package com.kasmooi.invoice.mapper;

import com.kasmooi.invoice.model.dto.response.GenericResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenericMapper {

    default <T> GenericResponseDto<T> toGenericResponse(String code, String message, T data) {
        GenericResponseDto<T> response = new GenericResponseDto<>();
        response.setResponseCode(code);
        response.setResponseMessage(message);
        response.setData(data);
        return response;
    }

}
