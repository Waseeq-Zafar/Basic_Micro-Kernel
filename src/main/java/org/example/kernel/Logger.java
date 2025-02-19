package org.example.kernel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    public enum LogLevel {
        INFO, WARNING, ERROR
    }

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void log(LogLevel level, String message) {
        String timestamp = LocalTime.now().format(TIME_FORMATTER);
        System.out.println("[" + timestamp + "] [" + level + "] " + message);
    }

    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    public static void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }
}
