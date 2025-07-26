package com.poorcraft.core;

import com.poorcraft.world.WorldManager;
import com.poorcraft.player.PlayerManager;
import com.poorcraft.ui.UIManager;
import com.poorcraft.network.NetworkManager;
import com.poorcraft.npc.NPCManager;
import com.poorcraft.utils.Logger;
import com.poorcraft.utils.Time;

/**
 * Core game engine that manages all subsystems.
 * 
 * This class coordinates between different game systems including:
 * - Rendering and window management
 * - World generation and management
 * - Player input and state management
 * - UI rendering and interaction
 * - Network communication
 * - AI NPC management
 * 
 * @author Poorcraft Team
 */
public class GameEngine {
    
    private final Window window;
    private final Renderer renderer;
    private final WorldManager worldManager;
    private final PlayerManager playerManager;
    private final UIManager uiManager;
    private final NetworkManager networkManager;
    private final NPCManager npcManager;
    
    private boolean running;
    private float targetFPS = 60.0f;
    private float targetUPS = 30.0f; // Updates per second
    
    /**
     * Initialize all game subsystems
     */
    public GameEngine() {
        Logger.info("Initializing game engine...");
        
        // Initialize window and rendering
        this.window = new Window(1280, 720, "Poorcraft 64");
        this.renderer = new Renderer(window);
        
        // Initialize game systems
        this.worldManager = new WorldManager();
        this.playerManager = new PlayerManager();
        this.uiManager = new UIManager(window);
        this.networkManager = new NetworkManager();
        this.npcManager = new NPCManager();
        
        Logger.info("Game engine initialized successfully");
    }
    
    /**
     * Start the main game loop
     */
    public void start() {
        Logger.info("Starting game loop...");
        running = true;
        
        // Initialize all systems
        initialize();
        
        // Main game loop
        gameLoop();
    }
    
    /**
     * Initialize all game systems
     */
    private void initialize() {
        Logger.info("Initializing game systems...");
        
        renderer.initialize();
        worldManager.initialize();
        playerManager.initialize();
        uiManager.initialize();
        networkManager.initialize();
        npcManager.initialize();
        
        Logger.info("All systems initialized");
    }
    
    /**
     * Main game loop with fixed timestep
     */
    private void gameLoop() {
        float accumulator = 0.0f;
        float interval = 1.0f / targetUPS;
        
        while (running && !window.shouldClose()) {
            float deltaTime = Time.getDeltaTime();
            accumulator += deltaTime;
            
            // Process input
            window.pollEvents();
            
            // Fixed timestep updates
            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }
            
            // Render
            render();
            
            // Sync to target FPS
            sync();
        }
        
        shutdown();
    }
    
    /**
     * Update game logic
     * @param deltaTime Time since last update in seconds
     */
    private void update(float deltaTime) {
        worldManager.update(deltaTime);
        playerManager.update(deltaTime);
        uiManager.update(deltaTime);
        networkManager.update(deltaTime);
        npcManager.update(deltaTime);
    }
    
    /**
     * Render the game
     */
    private void render() {
        renderer.beginFrame();
        
        worldManager.render(renderer);
        playerManager.render(renderer);
        npcManager.render(renderer);
        uiManager.render(renderer);
        
        renderer.endFrame();
        window.swapBuffers();
    }
    
    /**
     * Sync to target FPS
     */
    private void sync() {
        float frameTime = 1.0f / targetFPS;
        float currentTime = Time.getTime();
        float nextFrameTime = Time.getLastFrameTime() + frameTime;
        
        if (currentTime < nextFrameTime) {
            try {
                Thread.sleep((long) ((nextFrameTime - currentTime) * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Gracefully shutdown all systems
     */
    public void shutdown() {
        Logger.info("Shutting down game engine...");
        running = false;
        
        // Shutdown in reverse order of initialization
        npcManager.shutdown();
        networkManager.shutdown();
        uiManager.shutdown();
        playerManager.shutdown();
        worldManager.shutdown();
        renderer.shutdown();
        window.destroy();
        
        Logger.info("Game engine shutdown complete");
    }
    
    // Getters for game systems
    public Window getWindow() { return window; }
    public Renderer getRenderer() { return renderer; }
    public WorldManager getWorldManager() { return worldManager; }
    public PlayerManager getPlayerManager() { return playerManager; }
    public UIManager getUIManager() { return uiManager; }
    public NetworkManager getNetworkManager() { return networkManager; }
    public NPCManager getNPCManager() { return npcManager; }
}
