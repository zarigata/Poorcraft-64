package com.poorcraft.player;

import com.poorcraft.resources.ResourceType;
import com.poorcraft.utils.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Player progression system with RPG mechanics.
 * Handles leveling, skill trees, and resource management.
 * 
 * @author Poorcraft Team
 */
public class PlayerProgression {
    
    public enum Skill {
        MINING("Mining", "Increases mining speed and unlocks rare ores"),
        BUILDING("Building", "Improves construction speed and unlocks advanced structures"),
        COMBAT("Combat", "Increases damage and unlocks special attacks"),
        MAGIC("Magic", "Unlocks spells and magical abilities"),
        CRAFTING("Crafting", "Improves crafting efficiency and unlocks advanced recipes"),
        EXPLORATION("Exploration", "Increases movement speed and reveals hidden treasures"),
        FARMING("Farming", "Improves crop yields and unlocks rare plants"),
        TRADING("Trading", "Better prices and unlocks rare items in shops");
        
        private final String name;
        private final String description;
        
        Skill(String name, String description) {
            this.name = name;
            this.description = description;
        }
        
        public String getName() { return name; }
        public String getDescription() { return description; }
    }
    
    private int level;
    private int experience;
    private int experienceToNextLevel;
    private int skillPoints;
    private final Map<Skill, Integer> skillLevels;
    private final Map<ResourceType, Integer> resources;
    
    public PlayerProgression() {
        this.level = 1;
        this.experience = 0;
        this.experienceToNextLevel = calculateExperienceRequired(1);
        this.skillPoints = 0;
        this.skillLevels = new HashMap<>();
        this.resources = new HashMap<>();
        
        // Initialize skill levels
        for (Skill skill : Skill.values()) {
            skillLevels.put(skill, 1);
        }
        
        Logger.info("Player progression initialized");
    }
    
    /**
     * Add experience to the player
     * @param amount Experience amount to add
     * @param source Source of experience (for logging)
     */
    public void addExperience(int amount, String source) {
        experience += amount;
        Logger.info("Gained " + amount + " experience from " + source);
        
        // Check for level up
        while (experience >= experienceToNextLevel) {
            levelUp();
        }
    }
    
    /**
     * Level up the player
     */
    private void levelUp() {
        experience -= experienceToNextLevel;
        level++;
        skillPoints += 3; // Grant 3 skill points per level
        experienceToNextLevel = calculateExperienceRequired(level);
        
        Logger.info("Level up! New level: " + level);
        Logger.info("Skill points gained: 3 (Total: " + skillPoints + ")");
    }
    
    /**
     * Calculate experience required for next level
     * @param level Current level
     * @return Experience required
     */
    private int calculateExperienceRequired(int level) {
        return 100 * level + (level * level * 10);
    }
    
    /**
     * Upgrade a skill using skill points
     * @param skill Skill to upgrade
     * @return true if upgrade successful, false if not enough points
     */
    public boolean upgradeSkill(Skill skill) {
        if (skillPoints <= 0) {
            Logger.warn("Not enough skill points to upgrade " + skill.getName());
            return false;
        }
        
        int currentLevel = skillLevels.get(skill);
        skillLevels.put(skill, currentLevel + 1);
        skillPoints--;
        
        Logger.info("Upgraded " + skill.getName() + " to level " + (currentLevel + 1));
        return true;
    }
    
    /**
     * Add resources to inventory
     * @param type Resource type
     * @param amount Amount to add
     */
    public void addResource(ResourceType type, int amount) {
        resources.merge(type, amount, Integer::sum);
        Logger.info("Added " + amount + " " + type.getDisplayName() + " to inventory");
    }
    
    /**
     * Remove resources from inventory
     * @param type Resource type
     * @param amount Amount to remove
     * @return true if successful, false if not enough resources
     */
    public boolean removeResource(ResourceType type, int amount) {
        int current = resources.getOrDefault(type, 0);
        if (current < amount) {
            return false;
        }
        
        resources.put(type, current - amount);
        Logger.info("Removed " + amount + " " + type.getDisplayName() + " from inventory");
        return true;
    }
    
    /**
     * Get skill level
     * @param skill Skill to check
     * @return Current skill level
     */
    public int getSkillLevel(Skill skill) {
        return skillLevels.getOrDefault(skill, 1);
    }
    
    /**
     * Get skill bonus multiplier
     * @param skill Skill to check
     * @return Multiplier based on skill level (1.0 = 100%)
     */
    public float getSkillBonus(Skill skill) {
        int level = getSkillLevel(skill);
        return 1.0f + (level - 1) * 0.1f; // 10% bonus per level
    }
    
    /**
     * Check if player has required resources
     * @param requirements Map of required resources and amounts
     * @return true if player has all required resources
     */
    public boolean hasResources(Map<ResourceType, Integer> requirements) {
        for (Map.Entry<ResourceType, Integer> entry : requirements.entrySet()) {
            int current = resources.getOrDefault(entry.getKey(), 0);
            if (current < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Get total resource count
     * @param type Resource type
     * @return Current amount
     */
    public int getResourceCount(ResourceType type) {
        return resources.getOrDefault(type, 0);
    }
    
    /**
     * Get all resources as a map
     * @return Unmodifiable map of resources
     */
    public Map<ResourceType, Integer> getAllResources() {
        return new HashMap<>(resources);
    }
    
    /**
     * Get skill points available
     * @return Available skill points
     */
    public int getSkillPoints() {
        return skillPoints;
    }
    
    /**
     * Get current level
     * @return Player level
     */
    public int getLevel() {
        return level;
    }
    
    /**
     * Get current experience
     * @return Current experience points
     */
    public int getExperience() {
        return experience;
    }
    
    /**
     * Get experience required for next level
     * @return Experience to next level
     */
    public int getExperienceToNextLevel() {
        return experienceToNextLevel;
    }
    
    /**
     * Get progress to next level (0.0 - 1.0)
     * @return Progress percentage
     */
    public float getLevelProgress() {
        return (float) experience / experienceToNextLevel;
    }
    
    /**
     * Get total skill levels across all skills
     * @return Total skill levels
     */
    public int getTotalSkillLevels() {
        return skillLevels.values().stream().mapToInt(Integer::intValue).sum();
    }
}
