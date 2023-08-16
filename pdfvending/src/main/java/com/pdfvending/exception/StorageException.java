package com.pdfvending.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorageException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(StorageException.class);

    public StorageException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }

}
