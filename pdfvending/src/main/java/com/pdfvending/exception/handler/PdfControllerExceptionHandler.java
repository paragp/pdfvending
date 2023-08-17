package com.pdfvending.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pdfvending.controller.PdfController;
import com.pdfvending.exception.MissingDataException;
import com.pdfvending.exception.UnsupportedPdfTypeException;

@ControllerAdvice(assignableTypes = PdfController.class)
public class PdfControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(PdfControllerExceptionHandler.class);

    // Handles UnsupportedPdfTypeException, MissingDataException and returns a
    // BAD_REQUEST (400) status.
    @ExceptionHandler({ UnsupportedPdfTypeException.class, MissingDataException.class })
    public ResponseEntity<String> handleBadRequestExceptions(Exception ex) {
        logger.error("Bad request error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handles general Exception types and returns an INTERNAL_SERVER_ERROR (500)
    // status.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        logger.error("Internal server error occurred: {}", ex.getMessage(), ex);

        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}