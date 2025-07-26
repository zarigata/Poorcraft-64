package com.poorcraft.resources;

/**
 * Enumeration of all resource types in the game.
 * These resources are used for crafting, building, and RPG progression.
 * 
 * @author Poorcraft Team
 */
public enum ResourceType {
    // Basic materials
    WOOD("Wood", "Basic building material from trees", 1, "textures/items/wood.png"),
    STONE("Stone", "Solid mineral material", 1, "textures/items/stone.png"),
    IRON("Iron", "Strong metallic material", 2, "textures/items/iron.png"),
    GOLD("Gold", "Valuable precious metal", 3, "textures/items/gold.png"),
    DIAMOND("Diamond", "Extremely hard crystal", 4, "textures/items/diamond.png"),
    
    // Advanced materials
    CRYSTAL("Crystal", "Magical energy source", 5, "textures/items/crystal.png"),
    MYTHRIL("Mythril", "Lightweight magical alloy", 6, "textures/items/mythril.png"),
    DRAGON_SCALE("Dragon Scale", "Rare dragon material", 8, "textures/items/dragon_scale.png"),
    
    // Organic materials
    LEATHER("Leather", "Flexible animal hide", 2, "textures/items/leather.png"),
    FEATHER("Feather", "Light bird material", 1, "textures/items/feather.png"),
    BONE("Bone", "Hard skeletal material", 2, "textures/items/bone.png"),
    
    // Food and consumables
    APPLE("Apple", "Basic food source", 1, "textures/items/apple.png"),
    BREAD("Bread", "Sustaining food item", 2, "textures/items/bread.png"),
    MEAT("Meat", "Protein-rich food", 3, "textures/items/meat.png"),
    GOLDEN_APPLE("Golden Apple", "Magical healing food", 5, "textures/items/golden_apple.png"),
    
    // Magical resources
    MANA_CRYSTAL("Mana Crystal", "Pure magical energy", 4, "textures/items/mana_crystal.png"),
    FIRE_ESSENCE("Fire Essence", "Elemental fire energy", 5, "textures/items/fire_essence.png"),
    ICE_ESSENCE("Ice Essence", "Elemental ice energy", 5, "textures/items/ice_essence.png"),
    LIGHTNING_ESSENCE("Lightning Essence", "Elemental lightning energy", 6, "textures/items/lightning_essence.png"),
    
    // Refined materials
    IRON_INGOT("Iron Ingot", "Refined iron material", 3, "textures/items/iron_ingot.png"),
    GOLD_INGOT("Gold Ingot", "Refined gold material", 4, "textures/items/gold_ingot.png"),
    STEEL_INGOT("Steel Ingot", "Strong alloy material", 5, "textures/items/steel_ingot.png"),
    
    // Tools and equipment materials
    STRING("String", "Flexible fiber material", 1, "textures/items/string.png"),
    STICK("Stick", "Basic tool handle", 1, "textures/items/stick.png"),
    FLINT("Flint", "Sharp stone material", 2, "textures/items/flint.png"),
    
    // Rare resources
    EMERALD("Emerald", "Rare green gem", 6, "textures/items/emerald.png"),
    RUBY("Ruby", "Rare red gem", 7, "textures/items/ruby.png"),
    SAPPHIRE("Sapphire", "Rare blue gem", 7, "textures/items/sapphire.png"),
    
    // Boss materials
    ENDER_PEARL("Ender Pearl", "Teleportation material", 5, "textures/items/ender_pearl.png"),
    NETHER_STAR("Nether Star", "Boss drop material", 10, "textures/items/nether_star.png"),
    DRAGON_EGG("Dragon Egg", "Ultimate rare item", 15, "textures/items/dragon_egg.png");
    
    private final String displayName;
    private final String description;
    private final int rarity;
    private final String texturePath;
    
    ResourceType(String displayName, String description, int rarity, String texturePath) {
        this.displayName = displayName;
        this.description = description;
        this.rarity = rarity;
        this.texturePath = texturePath;
    }
    
    /**
     * Get the display name of the resource
     * @return Human-readable name
     */
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Get the description of the resource
     * @return Resource description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Get the rarity level (1-15)
     * @return Rarity level
     */
    public int getRarity() {
        return rarity;
    }
    
    /**
     * Get the texture file path
     * @return Path to texture file
     */
    public String getTexturePath() {
        return texturePath;
    }
    
    /**
     * Check if this is a basic resource (rarity 1-3)
     * @return true if basic resource
     */
    public boolean isBasic() {
        return rarity <= 3;
    }
    
    /**
     * Check if this is a rare resource (rarity 4-7)
     * @return true if rare resource
     */
    public boolean isRare() {
        return rarity >= 4 && rarity <= 7;
    }
    
    /**
     * Check if this is an epic resource (rarity 8-12)
     * @return true if epic resource
     */
    public boolean isEpic() {
        return rarity >= 8 && rarity <= 12;
    }
    
    /**
     * Check if this is a legendary resource (rarity 13+)
     * @return true if legendary resource
     */
    public boolean isLegendary() {
        return rarity >= 13;
    }
}
