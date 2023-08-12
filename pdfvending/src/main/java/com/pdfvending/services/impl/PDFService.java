package com.pdfvending.services.impl;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdfvending.exception.UnsupportedPdfTypeException;
import com.pdfvending.services.PDFGenerator;

@Service
public class PDFService {
    private static final Logger logger = LoggerFactory.getLogger(PDFService.class);
    private final Map<String, PDFGenerator> pdfGeneratorMap;

    @Autowired
    public PDFService(Map<String, PDFGenerator> pdfGeneratorMap) {
        this.pdfGeneratorMap = pdfGeneratorMap;
    }

    public CompletableFuture<byte[]> generatePDF(String type, Map<String, Object> data) {
        logger.info("PDFService is called to generate PDF");
        logger.info(
                "Get the relevant generator object based on the type passed as a request parameter in the controller");
        PDFGenerator generator = pdfGeneratorMap.get(type);

        if (generator == null) {
            throw new UnsupportedPdfTypeException("Unsupported PDF type: " + type);
        }
        logger.info(generator.getClass() + " is selected for the type " + type);
        return generator.generatePDF(data);
    }
}
