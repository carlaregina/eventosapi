package com.eventosapi.demo.controller;

import com.eventosapi.demo.dtos.InscricaoDTO;
import com.eventosapi.demo.models.Local;
import com.eventosapi.demo.services.VoucherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JasperExportManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voucher")
@Tag(name = "Voucher")
public class VoucherController {

    private VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/baixar")
    @Operation(summary = "Baixar voucher em PDF")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Voucher baixado com sucesso", 
            content = { @Content(mediaType = "application/pdf", 
            schema = @Schema(implementation = Local.class)) }),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", 
            content = @Content)
    })
    public ResponseEntity<byte[]> baixarVoucher(@RequestBody InscricaoDTO inscricao) {
        try {
            var jasperPrint = voucherService.geraRelatorio(inscricao);

            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Inscricao.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
