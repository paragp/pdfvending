package com.pdfvending.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnsupportedPdfTypeException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(UnsupportedPdfTypeException.class);

    public UnsupportedPdfTypeException(String pdfType) {
        super("Unsupported PDF type: " + pdfType);
        logger.error("Unsupported PDF type: " + pdfType);
    }
}