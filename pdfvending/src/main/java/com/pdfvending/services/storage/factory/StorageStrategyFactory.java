package com.pdfvending.services.storage.factory;

import com.pdfvending.exception.UnsupportedStorageTypeException;
import com.pdfvending.services.storage.StorageStrategy;
import com.pdfvending.services.storage.impl.LocalFileSystemStorage;
import com.pdfvending.services.storage.impl.EFSStorage;
import com.pdfvending.utils.StorageType;

public class StorageStrategyFactory {

    public static StorageStrategy getStorageStrategy(StorageType type) {
        switch (type) {
            case LOCAL_FILE_SYSTEM:
                return new LocalFileSystemStorage();
            case EFS:
                return new EFSStorage();
            // Add other storage types as needed
            default:
                throw new UnsupportedStorageTypeException("Unsupported storage type: " + type);
        }
    }

    /**
     * Inner static class responsible for holding the Singleton instance.
     * This gets loaded only when it's referenced, thus making the Singleton
     * instance creation lazy.
     */
    private static class Holder {
        private static final StorageStrategyFactory INSTANCE = new StorageStrategyFactory();
    }

    public static StorageStrategyFactory getInstance() {
        return Holder.INSTANCE;
    }

}
