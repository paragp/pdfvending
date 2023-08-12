package com.pdfvending.services;

import java.util.concurrent.CompletableFuture;

public interface PDFConverter {
    CompletableFuture<byte[]> convert(String htmlContent);
}