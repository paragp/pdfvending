package com.pdfvending.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetCurrentTimestamp {

    // Step 1: Make the default constructor private
    private GetCurrentTimestamp() {}

    // Your method remains unchanged
    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss-SSS").format(new Date());
    }

    // Step 2: Inner static helper class
    private static class SingletonHelper {
        private static final GetCurrentTimestamp INSTANCE = new GetCurrentTimestamp();
    }

    // Step 3: Public static method to get the instance
    public static GetCurrentTimestamp getInstance() {
        return SingletonHelper.INSTANCE;
    }
}

