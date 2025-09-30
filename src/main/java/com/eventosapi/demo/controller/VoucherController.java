package com.eventosapi.demo.controller;

import com.eventosapi.demo.domain.dto.InscricaoDTO;
import com.eventosapi.demo.service.VoucherService;
import net.sf.jasperreports.engine.JasperExportManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voucher")
public class VoucherController {

    private VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/baixar")
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
