package com.eventosapi.demo.controllers;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.eventosapi.demo.controller.VoucherController;
import com.eventosapi.demo.services.VoucherService;

class VoucherControllerTest {

    private VoucherService voucherService;
    private VoucherController voucherController;


    @BeforeEach
    void setUp() {
        voucherService = mock(VoucherService.class);
        voucherController = new VoucherController(voucherService);
    }

    @Test
    void baixarVoucher_deveRetornarPdf_quandoServicoRetornaByteArray() {
        byte[] pdfMock = new byte[]{1, 2, 3};
        when(voucherService.geraRelatorioPDF(1L)).thenReturn(pdfMock);

        ResponseEntity<byte[]> response = voucherController.baixarVoucher(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(pdfMock, response.getBody());
        assertEquals("application/pdf", response.getHeaders().getContentType().toString());
        assertTrue(response.getHeaders().getContentDisposition().getFilename().contains("Inscricao.pdf"));
        verify(voucherService).geraRelatorioPDF(1L);
    }
}
