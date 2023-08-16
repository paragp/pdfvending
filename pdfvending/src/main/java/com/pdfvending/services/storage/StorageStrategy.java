package com.pdfvending.services.storage;

public interface StorageStrategy {
    void store(byte[] content, String fileName, String tempPath);
}
