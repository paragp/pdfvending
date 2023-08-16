package com.pdfvending.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 *  Making use of a thread pool is a good approach to handle high concurrency, especially for CPU-intensive tasks like PDF generation. The ExecutorService from Java's concurrency package is a suitable tool for this.
 */

@Configuration
public class PdfConverterConfig {

    @Value("${pdfvending.threadpool.size}")
    private int threadpoolSize;

    // We're defining an ExecutorService with a fixed thread pool of size 10 (it
    // can be adjusted based on the requirements and system's capabilities).
    private final ExecutorService pdfConverterExecutorService;

    public PdfConverterConfig(@Value("${pdfvending.threadpool.size}") int threadpoolSize) {
        this.pdfConverterExecutorService = Executors.newFixedThreadPool(threadpoolSize);
    }

    @Bean
    public ExecutorService pdfConverterExecutorService() {
        return pdfConverterExecutorService;
    }

    /*
     * The @PreDestroy annotation ensures that the executor service is gracefully
     * shut down when the application context is closed or the application exits.
     */
    @PreDestroy
    public void destroy() {
        if (pdfConverterExecutorService != null && !pdfConverterExecutorService.isShutdown()) {
            pdfConverterExecutorService.shutdown();
        }
    }
}
