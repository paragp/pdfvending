package com.pdfvending.services.storage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.pdfvending.exception.StorageException;
import com.pdfvending.services.storage.StorageStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Service("LocalFileSystemStorage")
public class LocalFileSystemStorage implements StorageStrategy {
    private static final Logger logger = LoggerFactory.getLogger(LocalFileSystemStorage.class);

    /*
     * Store the PDF content temporarily in a local file system.
     */
    @Override
    public void store(byte[] content, String fileName, String tempPath) {

        logger.info("Generating file at following location " + tempPath);

        File tempFile = Paths.get(tempPath, fileName).toFile();

        // Store the PDF temporarily
        writeToTempFile(content, tempFile);

    }

    private void writeToTempFile(byte[] content, File tempFile) {
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(content);
        } catch (FileNotFoundException fnfe) {
            throw new StorageException("Target file not found or cannot be created: " + tempFile.getAbsolutePath(),
                    fnfe);
        } catch (SecurityException se) {
            throw new StorageException("Security exception while writing to file. Check permissions.", se);
        } catch (IOException ioe) {
            throw new StorageException("Failed to store PDF temporarily at " + tempFile.getPath(), ioe);
        }
    }

}
