/*
 * This decorator will capture the MDC context from the main thread and set it in the new thread before the task is executed.
 */
package com.pdfvending.config;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MdcTaskDecorator implements TaskDecorator {

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
            } finally {
                MDC.clear();
            }
        };
    }

}
