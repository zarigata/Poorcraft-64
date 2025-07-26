package com.poorcraft;

import com.poorcraft.core.GameEngine;
import com.poorcraft.core.Window;
import com.poorcraft.utils.Logger;

/**
 * Main entry point for Poorcraft 64 game.
 * 
 * This class initializes the game engine and starts the main game loop.
 * 
 * @author Poorcraft Team
 * @version 1.0.0
 */
public class Poorcraft64 {
    
    public static final String VERSION = "1.0.0-SNAPSHOT";
    public static final String GAME_TITLE = "Poorcraft 64";
    
    private static GameEngine engine;
    
    public static void main(String[] args) {
        Logger.info("Starting " + GAME_TITLE + " v" + VERSION);
        
        try {
            // Initialize game engine
            engine = new GameEngine();
            
            // Start the game
            engine.start();
            
        } catch (Exception e) {
            Logger.error("Failed to start game", e);
            System.exit(1);
        }
    }
    
    /**
     * Gracefully shutdown the game
     */
    public static void shutdown() {
        if (engine != null) {
            engine.shutdown();
        }
        Logger.info("Game shutdown complete");
    }
    
    /**
     * Get the current game engine instance
     * @return GameEngine instance
     */
    public static GameEngine getEngine() {
        return engine;
    }
}
