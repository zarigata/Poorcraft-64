package com.poorcraft.player;

import com.poorcraft.core.GameEngine;
import com.poorcraft.utils.Logger;
import org.joml.Vector3f;

/**
 * Manages player state, input, and interactions.
 * 
 * @author Poorcraft Team
 */
public class PlayerManager {
    
    private final PlayerProgression progression;
    private final PlayerInput input;
    private final PlayerRenderer renderer;
    private Vector3f position;
    private Vector3f rotation;
    private boolean flying;
    private float health;
    private float maxHealth;
    
    public PlayerManager() {
        this.progression = new PlayerProgression();
        this.input = new PlayerInput();
        this.renderer = new PlayerRenderer();
        this.position = new Vector3f(0, 100, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.flying = false;
        this.health = 100.0f;
        this.maxHealth = 100.0f;
    }
    
    public void initialize() {
        Logger.info("Player manager initialized");
    }
    
    public void update(float deltaTime) {
        input.update(deltaTime);
        handleMovement(deltaTime);
        updateAbilities();
    }
    
    public void render(Renderer renderer) {
        renderer.renderPlayer(position, rotation);
    }
    
    public void shutdown() {
        Logger.info("Player manager shutdown");
    }
    
    private void handleMovement(float deltaTime) {
        // Basic movement logic
        Vector3f movement = input.getMovement();
        position.add(movement.mul(deltaTime * getMovementSpeed()));
    }
    
    private float getMovementSpeed() {
        return 5.0f * progression.getSkillBonus(PlayerProgression.Skill.EXPLORATION);
    }
    
    private void updateAbilities() {
        // Update player abilities based on skills
    }
    
    // Getters
    public PlayerProgression getProgression() { return progression; }
    public Vector3f getPosition() { return position; }
    public Vector3f getRotation() { return rotation; }
    public float getHealth() { return health; }
    public float getMaxHealth() { return maxHealth; }
}
