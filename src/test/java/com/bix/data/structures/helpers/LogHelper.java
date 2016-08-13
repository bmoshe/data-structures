package com.bix.data.structures.helpers;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Created by bmoshe on 03/08/16.
 */
public class LogHelper {

    private static Logger log = Logger.getLogger(LogHelper.class.getSimpleName());

    public static void infoAsAbove(String message) {
        logAsAbove(Level.INFO, message);
    }

    private static void logAsAbove(Level level, String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        int idxThisClass = nextClassInStackTrace(stackTrace, 1);
        int idxImmediateCallingClass = nextClassInStackTrace(stackTrace, idxThisClass + 1);
        int idxAboveClass = nextClassInStackTrace(stackTrace, idxImmediateCallingClass + 1);

        StackTraceElement caller = stackTrace[idxAboveClass];

        LogRecord logRecord = new LogRecord(level, message);
        logRecord.setSourceClassName(caller.getClassName());
        logRecord.setSourceMethodName(caller.getMethodName() + ":" + caller.getLineNumber());

        log.log(logRecord);
    }

    private static int nextClassInStackTrace(StackTraceElement[] stackTrace, int idx) {

        while(idx < stackTrace.length - 1 &&
              stackTrace[idx].getClassName().equals(stackTrace[idx - 1].getClassName())) {

            idx++;
        }

        return idx;
    }
}