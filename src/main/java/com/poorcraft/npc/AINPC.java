package com.poorcraft.npc;

import com.poorcraft.player.PlayerProgression;
import com.poorcraft.resources.ResourceType;
import com.poorcraft.utils.Logger;

import java.util.*;

/**
 * AI-powered NPC that can interact with players and external AI services.
 * Supports integration with Gemini, Ollama, and OpenRouter APIs.
 * 
 * @author Poorcraft Team
 */
public class AINPC {
    
    public enum Personality {
        FRIENDLY("Friendly", "Helpful and encouraging"),
        MERCHANT("Merchant", "Focuses on trading and business"),
        WARRIOR("Warrior", "Combat-focused and brave"),
        MAGE("Mage", "Magical and mysterious"),
        TRADER("Trader", "Good at finding rare items"),
        EXPLORER("Explorer", "Knowledgeable about the world"),
        GUARD("Guard", "Protective and dutiful"),
        VILLAGER("Villager", "Simple and hardworking");
        
        private final String name;
        private final String description;
        
        Personality(String name, String description) {
            this.name = name;
            this.description = description;
        }
        
        public String getName() { return name; }
        public String getDescription() { return description; }
    }
    
    private final String id;
    private final String name;
    private final Personality personality;
    private final Map<String, String> memories;
    private final List<String> conversationHistory;
    private final Map<ResourceType, Integer> inventory;
    private final Set<String> quests;
    private String aiService;
    private boolean aiEnabled;
    
    private float x, y, z;
    private String currentAction;
    private long lastInteractionTime;
    
    public AINPC(String id, String name, Personality personality) {
        this.id = id;
        this.name = name;
        this.personality = personality;
        this.memories = new HashMap<>();
        this.conversationHistory = new ArrayList<>();
        this.inventory = new HashMap<>();
        this.quests = new HashSet<>();
        this.aiService = "gemini"; // Default AI service
        this.aiEnabled = true;
        this.currentAction = "idle";
        this.lastInteractionTime = System.currentTimeMillis();
        
        Logger.info("Created AI NPC: " + name + " (Personality: " + personality.getName() + ")");
    }
    
    /**
     * Generate response based on player input and context
     * @param playerInput Player's message
     * @param playerData Player's progression data
     * @return NPC's response
     */
    public String generateResponse(String playerInput, PlayerProgression playerData) {
        lastInteractionTime = System.currentTimeMillis();
        
        if (!aiEnabled) {
            return generateFallbackResponse(playerInput, playerData);
        }
        
        try {
            String context = buildContext(playerData);
            String prompt = buildPrompt(playerInput, context);
            
            // This would integrate with actual AI service
            String aiResponse = callAIService(prompt);
            
            // Store interaction
            conversationHistory.add("Player: " + playerInput);
            conversationHistory.add(name + ": " + aiResponse);
            
            // Trim history to prevent memory bloat
            if (conversationHistory.size() > 20) {
                conversationHistory.subList(0, 10).clear();
            }
            
            return aiResponse;
            
        } catch (Exception e) {
            Logger.error("AI service error, using fallback", e);
            return generateFallbackResponse(playerInput, playerData);
        }
    }
    
    /**
     * Build context string for AI prompt
     * @param playerData Player's data
     * @return Context string
     */
    private String buildContext(PlayerProgression playerData) {
        StringBuilder context = new StringBuilder();
        context.append("NPC Info:\n");
        context.append("Name: ").append(name).append("\n");
        context.append("Personality: ").append(personality.getName()).append("\n");
        context.append("Current Action: ").append(currentAction).append("\n");
        context.append("Inventory: ").append(inventory.toString()).append("\n");
        context.append("Memories: ").append(memories.toString()).append("\n");
        
        context.append("\nPlayer Info:\n");
        context.append("Level: ").append(playerData.getLevel()).append("\n");
        context.append("Resources: ").append(playerData.getAllResources().size()).append(" types\n");
        
        return context.toString();
    }
    
    /**
     * Build AI prompt
     * @param playerInput Player's message
     * @param context NPC and player context
     * @return Complete prompt
     */
    private String buildPrompt(String playerInput, String context) {
        return String.format("""
            You are %s, an NPC in a Minecraft-like RPG game with personality: %s.
            
            Context:
            %s
            
            Recent conversation:
            %s
            
            Player says: "%s"
            
            Respond naturally as %s, staying in character and considering the context.
            Keep responses concise (1-3 sentences) and relevant to the game world.
            "",
            name, personality.getDescription(), context,
            getRecentConversation(3), playerInput, name);
    }
    
    /**
     * Call AI service (placeholder for actual implementation)
     * @param prompt The prompt to send
     * @return AI response
     */
    private String callAIService(String prompt) {
        // Placeholder implementation
        // In real implementation, this would call:
        // - Google Gemini API
        // - Ollama local model
        // - OpenRouter API
        
        switch (personality) {
            case FRIENDLY:
                return "Hello there! I'm happy to help you on your adventure!";
            case MERCHANT:
                return "Looking to trade? I have some interesting items available.";
            case WARRIOR:
                return "Greetings, adventurer! Ready for battle?";
            case MAGE:
                return "The arcane energies are strong today... How may I assist you?";
            default:
                return "Hello! What can I do for you?";
        }
    }
    
    /**
     * Generate fallback response when AI service is unavailable
     * @param playerInput Player's message
     * @param playerData Player's data
     * @return Fallback response
     */
    private String generateFallbackResponse(String playerInput, PlayerProgression playerData) {
        String lowerInput = playerInput.toLowerCase();
        
        if (lowerInput.contains("hello") || lowerInput.contains("hi")) {
            return "Hello! I'm " + name + ". Nice to meet you!";
        } else if (lowerInput.contains("trade") || lowerInput.contains("buy") || lowerInput.contains("sell")) {
            return "I have some items for trade. What are you looking for?";
        } else if (lowerInput.contains("quest") || lowerInput.contains("mission")) {
            return "I might have a task for someone of your level. Are you interested?";
        } else {
            return "I'm not sure how to respond to that. Is there something specific you need?";
        }
    }
    
    /**
     * Add memory about player interaction
     * @param key Memory key
     * @param value Memory value
     */
    public void addMemory(String key, String value) {
        memories.put(key, value);
        Logger.info("NPC " + name + " remembered: " + key + " = " + value);
    }
    
    /**
     * Add item to NPC inventory
     * @param resource Resource type
     * @param amount Amount to add
     */
    public void addToInventory(ResourceType resource, int amount) {
        inventory.merge(resource, amount, Integer::sum);
    }
    
    /**
     * Remove item from NPC inventory
     * @param resource Resource type
     * @param amount Amount to remove
     * @return true if successful
     */
    public boolean removeFromInventory(ResourceType resource, int amount) {
        int current = inventory.getOrDefault(resource, 0);
        if (current < amount) {
            return false;
        }
        
        inventory.put(resource, current - amount);
        return true;
    }
    
    /**
     * Add quest to NPC
     * @param questId Quest identifier
     */
    public void addQuest(String questId) {
        quests.add(questId);
    }
    
    /**
     * Get recent conversation history
     * @param count Number of recent exchanges
     * @return Recent conversation
     */
    private String getRecentConversation(int count) {
        int start = Math.max(0, conversationHistory.size() - count * 2);
        return String.join("\n", conversationHistory.subList(start, conversationHistory.size()));
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public Personality getPersonality() { return personality; }
    public Map<String, String> getMemories() { return new HashMap<>(memories); }
    public Map<ResourceType, Integer> getInventory() { return new HashMap<>(inventory); }
    public Set<String> getQuests() { return new HashSet<>(quests); }
    public String getAiService() { return aiService; }
    public void setAiService(String aiService) { this.aiService = aiService; }
    public boolean isAiEnabled() { return aiEnabled; }
    public void setAiEnabled(boolean aiEnabled) { this.aiEnabled = aiEnabled; }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public String getCurrentAction() { return currentAction; }
    public void setCurrentAction(String currentAction) { this.currentAction = currentAction; }
    
    public long getLastInteractionTime() { return lastInteractionTime; }
}
