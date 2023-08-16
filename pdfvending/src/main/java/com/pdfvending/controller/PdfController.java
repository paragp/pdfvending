package com.pdfvending.controller;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pdfvending.services.pdfservices.PDFService;
import com.pdfvending.utils.GetCurrentTimestamp;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);

    @Autowired
    private PDFService pdfService;

    @PostMapping("/generate/{pdfType}")
    public CompletableFuture<ResponseEntity<byte[]>> generatePDF(@PathVariable String pdfType,
            @RequestBody Map<String, Object> data) {
        logger.info("generatePDF API Called");

        return pdfService.generatePDF(pdfType, data).thenApply(pdfBytes -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.builder("inline")
                            .filename(pdfType + GetCurrentTimestamp.getCurrentTimeStamp() + ".pdf").build());
            logger.info("Sending PDF as a response");
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        });
    }

}
