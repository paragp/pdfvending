package com.pdfvending.services.move.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pdfvending.exception.FileMoveException;
import com.pdfvending.services.move.MoveStrategy;
import com.pdfvending.utils.FileUtils;

@Service("FileSystemMoveStrategy")
public class FileSystemMoveStrategy implements MoveStrategy {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemMoveStrategy.class);

    // REPLACE_EXISTING: This option allows the copy operation to overwrite an
    // existing target file if it already exists. If this option is not specified
    // and the target file exists, the copy operation will fail.
    @Override
    public void move(File source, String fileName, String fileStoragePath) {
        String dateFolderName = FileUtils.getCurrentDateFolder();
        logger.info(fileStoragePath + " " + dateFolderName + " " + fileName);
        File targetFile = Paths.get(fileStoragePath, dateFolderName, fileName).toFile();
        // Ensure the final storage folder exists
        FileUtils.ensureFolderExists(targetFile.getParentFile());
        try (InputStream in = new FileInputStream(source)) {
            Files.copy(in, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("File moved from source i.e. " + source.getAbsolutePath() + " to destination "
                    + targetFile.getAbsolutePath());
        } catch (Exception e) {
            throw new FileMoveException("Failed to move PDF from temp to final location", e);
        } finally {
            source.delete(); // Clean up the temporary file

        }
    }
}
