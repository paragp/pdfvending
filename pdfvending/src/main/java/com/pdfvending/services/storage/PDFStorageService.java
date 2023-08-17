/*
 * PDFStorageService logs the message indicating it's storing the PDF file and specifies the storage type.
It determines the storage type and fetches a storage strategy from a factory.
It invokes the store method of the fetched strategy.
It logs a message indicating that the PDF file has been stored.
 */
package com.pdfvending.services.storage;

import java.io.File;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pdfvending.services.move.PDFMoveService;
import com.pdfvending.services.storage.factory.StorageStrategyFactory;
import com.pdfvending.utils.StorageType;

@Service("PDFStorageService")
public class PDFStorageService {

    private static final Logger logger = LoggerFactory.getLogger(PDFStorageService.class);

    @Autowired
    private PDFMoveService pdfMoveService;

    @Value("${FILE_SYSTEM}")
    private String storageType;

    @Value("${pdf.temp.path}")
    private String tempPath;

    @Async("taskExecutor")
    public void storePDF(byte[] pdfData, String fileName) {
        logger.info("Storing PDF file " + fileName + " using storage type " + storageType);
        StorageType type = StorageType.valueOf(storageType);

        StorageStrategy strategy = StorageStrategyFactory.getStorageStrategy(type);

        strategy.store(pdfData, fileName, tempPath);
        logger.info("PDF File " + fileName + " successfully stored at location " + tempPath);
        File source = Paths.get(tempPath, fileName).toFile();
        logger.info(fileName + " File will be moved to the target location ");
        pdfMoveService.movePDF(source, fileName);
    }

}
