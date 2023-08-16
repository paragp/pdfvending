package com.pdfvending.utils;

import java.io.File;

import com.pdfvending.exception.StorageException;

public class FileUtils {
    public static String getCurrentDateFolder() {
        return GetCurrentTimestamp.getCurrentDate();
    }

    public static void ensureFolderExists(File folder) {
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) {
                throw new StorageException("Failed to create directory for PDF storage: " + folder.getAbsolutePath(),
                        null);
            }
        }
    }

    // Step 2: Inner static helper class
    private static class SingletonHelper {
        private static final FileUtils INSTANCE = new FileUtils();
    }

    // Step 3: Public static method to get the instance
    public static FileUtils getInstance() {
        return SingletonHelper.INSTANCE;
    }

}
