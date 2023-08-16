package com.pdfvending.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MissingDataException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(MissingDataException.class);

    public MissingDataException(String message, Throwable cause) {
        super(message);
        logger.error(message, cause);
    }

}
