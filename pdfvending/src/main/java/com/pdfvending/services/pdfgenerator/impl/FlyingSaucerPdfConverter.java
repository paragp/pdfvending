package com.pdfvending.services.pdfgenerator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.pdfvending.exception.PdfConversionException;
import com.pdfvending.exception.PdfCreationException;
import com.pdfvending.exception.PdfRenderingException;
import com.pdfvending.services.move.PDFMoveService;
import com.pdfvending.services.pdfgenerator.PDFConverter;
import com.pdfvending.services.storage.PDFStorageService;
import com.pdfvending.utils.Constants;
import com.pdfvending.utils.GetCurrentTimestamp;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class FlyingSaucerPdfConverter implements PDFConverter {

    private static final Logger logger = LoggerFactory.getLogger(FlyingSaucerPdfConverter.class);

    @Autowired
    private ExecutorService pdfConverterExecutorService;

    @Autowired
    private PDFStorageService pdfStorageService;

   
    @Override
    public CompletableFuture<byte[]> convert(String htmlContent, String templateName) {
        // Copy the current MDC context
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return CompletableFuture.supplyAsync(() -> {
            // Set the MDC context in a new thread to retain the traceid
            MDC.setContextMap(contextMap);
            try {
                String fileName = templateName + GetCurrentTimestamp.getCurrentTimeStamp();
                logger.info("File will be generated with the name " + fileName);

                byte[] fileByte = new PdfGenerationTask(htmlContent, fileName).call();

                logger.info(fileName + " File will be stored in the temp location ");
                pdfStorageService.storePDF(fileByte, fileName + Constants.PDF_EXTENSION);

                return fileByte;
            } catch (PdfConversionException e) {
                logger.error("Error during PDF conversion.", e);
                throw e; // Re-throw the custom exception
            } catch (Exception e) {
                logger.error("Unexpected error during PDF creation.", e);
                throw new PdfCreationException("Unexpected error during PDF creation.", e);
            }
        }, pdfConverterExecutorService);
    }

    private class PdfGenerationTask implements Callable<byte[]> {

        private final String htmlContent;
        private final String fileName;

        PdfGenerationTask(String htmlContent, String fileName) {
            this.htmlContent = htmlContent;
            this.fileName = fileName;
        }

        @Override
        public byte[] call() {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            /*
             * renderer.getSharedContext().setReplacedElementFactory(
             * new HeaderFooterReplacedElementFactory(renderer.getOutputDevice()));
             */
            renderer.layout();
            try {
                long startTime = System.currentTimeMillis();
                renderer.createPDF(outputStream);
                long endTime = System.currentTimeMillis();
                logger.info("PDF Generation took {} milliseconds", (endTime - startTime));

            } catch (Exception e) {
                throw new PdfRenderingException("Error during PDF rendering.", e);
            }
            ;

            return outputStream.toByteArray();
        }

    }
}
