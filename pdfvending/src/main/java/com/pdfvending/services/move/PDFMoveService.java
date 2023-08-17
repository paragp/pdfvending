package com.pdfvending.services.move;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pdfvending.services.move.factory.MoveStrategyFactory;
import com.pdfvending.utils.MoveStrategyTypes;

@Service("PDFMoveService")
public class PDFMoveService {

    private static final Logger logger = LoggerFactory.getLogger(PDFMoveService.class);

    @Value("${PDF_DESTINATION}")
    private String pdfDestination;

    @Value("${pdf.storage.path}")
    private String fileStoragePath;

    @Async("taskExecutor")
    public void movePDF(File source, String fileName) {
        MoveStrategyTypes type = MoveStrategyTypes.valueOf(pdfDestination);

        MoveStrategy moveStrategy = MoveStrategyFactory.getStorageStrategy(type);
        logger.info("Moving strategy " + moveStrategy.getClass().getSimpleName() + " selected for " + type.name());
        moveStrategy.move(source, fileName, fileStoragePath);
    }

}
