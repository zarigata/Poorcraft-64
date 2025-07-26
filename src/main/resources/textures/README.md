# Texture Assets Guide

## Overview
This directory contains all 64x64 texture assets for Poorcraft 64. Placeholder textures are provided for development.

## Directory Structure
```
textures/
├── blocks/           # Block textures (64x64 PNG)
├── items/            # Item textures (64x64 PNG)
├── entities/         # Entity textures (64x64 PNG)
├── ui/               # UI elements (buttons, panels, etc.)
├── environment/      # Sky, water, particles
└── characters/       # Player and NPC skins
```

## Placeholder Textures
Temporary placeholder textures are provided in the `placeholders/` directory. These should be replaced with actual 64x64 textures.

## Creating Custom Textures

### Requirements
- Format: PNG with transparency
- Size: 64x64 pixels
- Color Mode: RGBA
- Naming: lowercase_with_underscores.png

### Color Palette
- Primary: #8B4513 (Brown for wood/dirt)
- Secondary: #696969 (Gray for stone/metal)
- Accent: #FFD700 (Gold for rare items)
- Magic: #9370DB (Purple for magical items)
- Nature: #228B22 (Green for plants)

### Tools
- Recommended: Aseprite, GIMP, or Photoshop
- Export Settings: PNG-24 with transparency
- Optimization: Use texture atlas for performance

## Texture Atlas
Textures are automatically combined into atlases for optimal rendering performance.

## Customization Guide
1. Replace placeholder textures in respective folders
2. Maintain consistent 64x64 resolution
3. Follow naming conventions
4. Test in-game for visual consistency

## Texture List
See `texture_list.md` for complete list of required textures.
