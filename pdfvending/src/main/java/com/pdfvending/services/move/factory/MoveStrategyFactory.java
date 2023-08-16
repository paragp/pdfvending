package com.pdfvending.services.move.factory;

import com.pdfvending.exception.UnSupportedFileMoveStrategy;
import com.pdfvending.services.move.MoveStrategy;
import com.pdfvending.services.move.impl.FileSystemMoveStrategy;
import com.pdfvending.utils.MoveStrategyTypes;

public class MoveStrategyFactory {

    public static MoveStrategy getStorageStrategy(MoveStrategyTypes type) {
        switch (type) {
            case REMOTE_FILE_SYSTEM:
                return new FileSystemMoveStrategy();
            // Add other move strategy types as needed
            default:
                throw new UnSupportedFileMoveStrategy("Unsupported file move strategy type: " + type);
        }
    }

    /**
     * Inner static class responsible for holding the Singleton instance.
     * This gets loaded only when it's referenced, thus making the Singleton
     * instance creation lazy.
     */
    private static class Holder {
        private static final MoveStrategyFactory INSTANCE = new MoveStrategyFactory();
    }

    public static MoveStrategyFactory getInstance() {
        return Holder.INSTANCE;
    }

}
