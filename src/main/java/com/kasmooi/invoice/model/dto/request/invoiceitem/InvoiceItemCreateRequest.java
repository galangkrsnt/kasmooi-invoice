package com.kasmooi.invoice.model.dto.request.invoiceitem;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemCreateRequest {
    private Long invoiceId;
    private String name;
    private Integer quantity;
    private BigDecimal price;
}