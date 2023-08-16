package com.pdfvending.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnsupportedStorageTypeException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(UnsupportedStorageTypeException.class);

    public UnsupportedStorageTypeException(String storageType) {
        super("Unsupported Storage type: " + storageType);
        logger.error("Unsupported Storage type: " + storageType);
    }
}