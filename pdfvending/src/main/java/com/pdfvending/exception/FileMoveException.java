package com.pdfvending.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileMoveException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(FileMoveException.class);

    public FileMoveException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }

}
