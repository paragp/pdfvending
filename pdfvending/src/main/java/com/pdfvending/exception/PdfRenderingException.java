package com.pdfvending.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Specific exception for rendering errors
public class PdfRenderingException extends PdfConversionException {
    private static final Logger logger = LoggerFactory.getLogger(PdfRenderingException.class);

    public PdfRenderingException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}
