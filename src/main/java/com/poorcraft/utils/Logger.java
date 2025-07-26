package com.poorcraft.utils;

import org.slf4j.LoggerFactory;

/**
 * Centralized logging utility for the game.
 * Provides consistent logging across all systems.
 * 
 * @author Poorcraft Team
 */
public class Logger {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("Poorcraft64");
    
    public static void info(String message) {
        logger.info(message);
    }
    
    public static void warn(String message) {
        logger.warn(message);
    }
    
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
    
    public static void debug(String message) {
        logger.debug(message);
    }
}
