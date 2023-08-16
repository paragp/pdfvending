package com.pdfvending.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Exception for general conversion errors
public class PdfCreationException extends PdfConversionException {
    private static final Logger logger = LoggerFactory.getLogger(PdfCreationException.class);

    public PdfCreationException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}
