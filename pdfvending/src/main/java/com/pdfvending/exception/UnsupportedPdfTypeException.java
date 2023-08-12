package com.pdfvending.exception;

public class UnsupportedPdfTypeException extends RuntimeException {
    public UnsupportedPdfTypeException(String pdfType) {
        super("Unsupported PDF type: " + pdfType);
    }
}