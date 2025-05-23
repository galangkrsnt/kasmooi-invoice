package com.kasmooi.invoice.model.dto.response.invoiceitem;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InvoiceItemResponse {
    private Long id;
    private Long invoiceId;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;
}