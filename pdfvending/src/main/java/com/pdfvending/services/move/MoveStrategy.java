package com.pdfvending.services.move;

import java.io.File;

public interface MoveStrategy {
    void move(File source, String fileName, String fileStoragePath);
}
