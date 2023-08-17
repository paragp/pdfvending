
/*
 * 
   1. The class implements the PDFConverter   interface.
   2. The primary method is convert, which takes an HTML content and template name as inputs and returns a CompletableFuture of the PDF file in byte format.
   3. It uses the Flying Saucer PDF renderer (ITextRenderer) to convert HTML to PDF.
   4. The class has a nested private class PdfGenerationTask that implements Callable and performs the actual PDF generation.
   5. The class uses SLF4J logging and Mapped Diagnostic Context (MDC) for logging context.
 */
package com.pdfvending.services.pdfgenerator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.pdfvending.exception.PdfConversionException;
import com.pdfvending.exception.PdfCreationException;
import com.pdfvending.exception.PdfRenderingException;

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
                logger.info("File will be generated with the name " + fileName + " asynchronously");

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
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);

                renderer.layout();
                long startTime = System.currentTimeMillis();
                renderer.createPDF(outputStream);
                long endTime = System.currentTimeMillis();
                logger.info("PDF Generation took {} milliseconds", (endTime - startTime));
                return outputStream.toByteArray();

            } catch (DocumentException de) {
                throw new PdfRenderingException("Document formatting or structure issue during PDF rendering.", de);
            } catch (IOException ioe) {
                throw new PdfRenderingException("IO issue encountered during PDF rendering.", ioe);
            }

        }

    }
}
