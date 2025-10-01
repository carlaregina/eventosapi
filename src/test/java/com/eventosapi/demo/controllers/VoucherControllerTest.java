package com.eventosapi.demo.controllers;
import com.eventosapi.demo.controller.VoucherController;
import com.eventosapi.demo.dtos.InscricaoRequestDTO;
import com.eventosapi.demo.services.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        InscricaoRequestDTO inscricao = new InscricaoRequestDTO(123L, 1L, 1L, null);
        byte[] pdfMock = new byte[]{1, 2, 3};

        when(voucherService.geraRelatorioPDF(inscricao)).thenReturn(pdfMock);

        ResponseEntity<byte[]> response = voucherController.baixarVoucher(inscricao);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(pdfMock, response.getBody());
        assertEquals("application/pdf", response.getHeaders().getContentType().toString());
        assertTrue(response.getHeaders().getContentDisposition().getFilename().contains("Inscricao.pdf"));

        verify(voucherService).geraRelatorioPDF(inscricao);
    }

    @Test
    void baixarVoucher_deveRetornar500_quandoServicoLancaExcecao() {
        InscricaoRequestDTO inscricao = new InscricaoRequestDTO(123L, 1L, 1L, null);

        when(voucherService.geraRelatorioPDF(inscricao)).thenThrow(new RuntimeException("Erro no serviço"));

        ResponseEntity<byte[]> response = voucherController.baixarVoucher(inscricao);

        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());

        verify(voucherService).geraRelatorioPDF(inscricao);
    }
}
