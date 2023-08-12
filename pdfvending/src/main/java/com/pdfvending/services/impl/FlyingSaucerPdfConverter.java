package com.pdfvending.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.pdfvending.services.PDFConverter;
import com.pdfvending.utils.GetCurrentTimestamp;
import com.pdfvending.utils.HeaderFooterReplacedElementFactory;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class FlyingSaucerPdfConverter implements PDFConverter {

    private static final Logger logger = LoggerFactory.getLogger(FlyingSaucerPdfConverter.class);

    @Autowired
    private ExecutorService pdfConverterExecutorService;

    @Override
    public CompletableFuture<byte[]> convert(String htmlContent) {
        // Copy the current MDC context
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return CompletableFuture.supplyAsync(() -> {
            try {
                // Set the MDC context in a new thread to retain the traceid
                MDC.setContextMap(contextMap);
                return new PdfGenerationTask(htmlContent).call();
            } catch (Exception e) {
                logger.error("Error during PDF creation.", e);
                throw new RuntimeException("Error during PDF creation.", e);
            }
        }, pdfConverterExecutorService);
    }

    private class PdfGenerationTask implements Callable<byte[]> {

        private final String htmlContent;

        PdfGenerationTask(String htmlContent) {
            this.htmlContent = htmlContent;
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
                logger.info("PDF Generation process started at " + GetCurrentTimestamp.getCurrentTimeStamp());
                renderer.createPDF(outputStream);
                logger.info("PDF Generation process completed at " + GetCurrentTimestamp.getCurrentTimeStamp());
            } catch (Exception e) {
                throw new RuntimeException("Error during PDF rendering.", e);
            }
            return outputStream.toByteArray();
        }

    }
}
