package com.pdfvending.services;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface PDFGenerator {
    CompletableFuture<byte[]> generatePDF(Map<String, Object> data);

    default void validateData(Map<String, Object> data) {
        // Default validation logic or leave empty if there's no common validation
    }

}
