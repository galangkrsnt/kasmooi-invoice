package com.kasmooi.invoice.model.dto.response;

import lombok.Data;

@Data
public class GenericResponseDto<T> {
    private String responseCode;
    private String responseMessage;
    private T data;
}