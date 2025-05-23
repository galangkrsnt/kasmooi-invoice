package com.kasmooi.invoice.controller;

import com.kasmooi.invoice.model.dto.request.invoiceitem.InvoiceItemCreateRequest;
import com.kasmooi.invoice.model.dto.response.GenericResponseDto;
import com.kasmooi.invoice.model.dto.response.invoiceitem.InvoiceItemResponse;
import com.kasmooi.invoice.service.InvoiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice-items")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceItemService invoiceItemService;

    @PostMapping
    public ResponseEntity<GenericResponseDto<InvoiceItemResponse>> create(@RequestBody InvoiceItemCreateRequest request) {
        return ResponseEntity.ok(invoiceItemService.createInvoiceItem(request));
    }
}
