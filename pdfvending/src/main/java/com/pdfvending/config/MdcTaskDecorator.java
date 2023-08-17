/*
 * This decorator will capture the MDC context from the main thread and set it in the new thread before the task is executed.
 */
package com.pdfvending.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MdcTaskDecorator implements TaskDecorator {

    private static final Logger logger = LoggerFactory.getLogger(MdcTaskDecorator.class);

    /**
     * Captures the MDC context from the main thread and returns a new Runnable that
     * sets
     * the captured context in the new thread. After running the original task, it
     * clears the MDC.
     *
     * @param runnable The original task
     * @return A new runnable with the MDC context set
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        // Capture the MDC context from the main thread
        final Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return () -> {
            // Set the captured MDC context in the new thread
            if (contextMap != null) {
                MDC.setContextMap(contextMap);
            }
            try {
                runnable.run();
            } catch (Exception e) {
                logger.error("Error occurred during task execution", e);
            } finally {
                MDC.clear();
            }
        };
    }

}
