package com.kasmooi.invoice.service;


import com.kasmooi.invoice.model.dto.request.invoiceitem.InvoiceItemCreateRequest;
import com.kasmooi.invoice.model.dto.response.GenericResponseDto;
import com.kasmooi.invoice.model.dto.response.invoiceitem.InvoiceItemResponse;

public interface InvoiceItemService {
    GenericResponseDto<InvoiceItemResponse> createInvoiceItem(InvoiceItemCreateRequest request);
}