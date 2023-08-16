package com.pdfvending.services.pdfgenerator;

import java.util.concurrent.CompletableFuture;

public interface PDFConverter {
    CompletableFuture<byte[]> convert(String htmlContent,String templateName);
}