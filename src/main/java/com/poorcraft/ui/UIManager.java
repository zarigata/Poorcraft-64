package com.poorcraft.ui;

import com.poorcraft.core.Window;
import com.poorcraft.utils.Logger;

/**
 * Manages user interface rendering and interaction.
 * 
 * @author Poorcraft Team
 */
public class UIManager {
    
    private final Window window;
    private final HUDRenderer hud;
    private final MenuManager menus;
    
    public UIManager(Window window) {
        this.window = window;
        this.hud = new HUDRenderer();
        this.menus = new MenuManager();
    }
    
    public void initialize() {
        Logger.info("UI manager initialized");
    }
    
    public void update(float deltaTime) {
        hud.update(deltaTime);
        menus.update(deltaTime);
    }
    
    public void render(Renderer renderer) {
        hud.render(renderer);
        menus.render(renderer);
    }
    
    public void shutdown() {
        Logger.info("UI manager shutdown");
    }
}
