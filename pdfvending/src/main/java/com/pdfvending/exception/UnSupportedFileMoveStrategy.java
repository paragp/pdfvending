package com.pdfvending.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnSupportedFileMoveStrategy extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(UnSupportedFileMoveStrategy.class);

    public UnSupportedFileMoveStrategy(String moveStrategyType) {
        super("Unsupported File Movement Storage type: " + moveStrategyType);
        logger.error("Unsupported File Movement Storage type: " + moveStrategyType);
    }

}
