package com.pdfvending.services.storage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pdfvending.exception.StorageException;
import com.pdfvending.services.move.MoveStrategy;
import com.pdfvending.services.storage.StorageStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

@Service("LocalFileSystemStorage")
public class LocalFileSystemStorage implements StorageStrategy {
    private static final Logger logger = LoggerFactory.getLogger(LocalFileSystemStorage.class);

    // @Autowired
    // private MoveStrategy moveStrategy;

    @Override
    public void store(byte[] content, String fileName, String tempPath) {

        logger.info("Generating file at following location " + tempPath);

        File tempFile = Paths.get(tempPath, fileName).toFile();

        // Store the PDF temporarily
        writeToTempFile(content, tempFile);

        // Move the PDF from temp to final location
        // moveFile(tempFile, fileName);
    }

    private void writeToTempFile(byte[] content, File tempFile) {
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(content);
        } catch (Exception e) {
            throw new StorageException("Failed to store PDF temporarily", e);
        }
        logger.info("PDF file stored to temp location " + tempFile.getPath());
    }

    /*
     * private void moveFile(File source, String fileName) {
     * moveStrategy.move(source, fileName);
     * }
     */

}
