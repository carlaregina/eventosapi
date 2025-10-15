package com.eventosapi.demo.controller;

import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.services.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voucher")
@Tag(name = "Voucher")
public class VoucherController {

    private final VoucherService voucherService;

    @PostMapping("/{id}/baixar")
    @Operation(summary = "Baixar voucher em PDF")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Voucher baixado com sucesso", 
            content = { @Content(mediaType = "application/pdf", 
            schema = @Schema(implementation = Local.class)) }),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", 
            content = @Content)
    })
    public ResponseEntity<byte[]> baixarVoucher(@PathVariable Long id) {
        byte[] pdfBytes = voucherService.geraRelatorioPDF(id);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Inscricao.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes);
    }
}

