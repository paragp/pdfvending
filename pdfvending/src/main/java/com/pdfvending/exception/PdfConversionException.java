package com.pdfvending.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Base custom exception for PDF conversion errors
public class PdfConversionException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(PdfConversionException.class);

    public PdfConversionException(String message) {
        super(message);
        logger.error(message);

    }

    public PdfConversionException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);

    }
}