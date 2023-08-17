package com.pdfvending.services.move.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import com.pdfvending.exception.FileMoveException;
import com.pdfvending.services.move.MoveStrategy;
import com.pdfvending.utils.FileUtils;

@Service("FileSystemMoveStrategy")
public class FileSystemMoveStrategy implements MoveStrategy {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemMoveStrategy.class);

    // Responsible for moving files from one location to another
    @Override
    public void move(File source, String fileName, String fileStoragePath) {
        String dateFolderName = FileUtils.getCurrentDateFolder();
        logger.info(fileStoragePath + " " + dateFolderName + " " + fileName);
        File targetFile = Paths.get(fileStoragePath, dateFolderName, fileName).toFile();
        // Ensure the final storage folder exists
        FileUtils.ensureFolderExists(targetFile.getParentFile());
        // InputStream is closed automatically, whether the operation completes
        // successfully or an exception is thrown.
        try (InputStream in = new FileInputStream(source)) {
            Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("File successfully moved from source i.e. " + source.getAbsolutePath() + " to destination "
                    + targetFile.getAbsolutePath());
        } catch (FileNotFoundException fnfe) {
            throw new FileMoveException("Source file not found: " + source.getAbsolutePath(), fnfe);
        } catch (SecurityException se) {
            throw new FileMoveException("Security exception while moving file. Check permissions.", se);
        } catch (IOException ioe) {
            throw new FileMoveException("Failed to move PDF from temp to final location", ioe);
        } finally {
            source.delete(); // Clean up the temporary file

        }
    }
}
