# Poorcraft 64 - Development Guide

## Quick Start

### 1. Environment Setup
```bash
# Install Java 17+ (see SETUP.md for detailed instructions)
java -version  # Should show Java 17 or higher

# Install Git
git --version

# Clone the project
git clone [your-repo-url]
cd Poorcraft64
```

### 2. First Build
```bash
# Windows
gradlew.bat build

# Linux/macOS
./gradlew build
```

### 3. Run the Game
```bash
# Development build
gradlew.bat run

# Build executable
gradlew.bat createExe
```

## Project Architecture

### Core Systems
- **GameEngine**: Main game loop and system coordination
- **Window**: GLFW-based window management
- **Renderer**: OpenGL 4.0+ rendering system
- **WorldManager**: Chunk-based world generation
- **PlayerManager**: Player state and progression
- **UIManager**: Modern UI framework
- **NPCManager**: AI-powered NPC system
- **NetworkManager**: Multiplayer networking

### Key Features
- **RPG Mechanics**: Leveling, skills, resource management
- **AI Integration**: Gemini, Ollama, OpenRouter support
- **64x64 Textures**: High-resolution graphics
- **Modular Design**: Easy extension and modification
- **Cross-platform**: Windows, macOS, Linux

## Development Workflow

### Adding New Features
1. **Plan**: Document the feature in `docs/features/`
2. **Design**: Create UML diagrams if complex
3. **Implement**: Follow coding standards
4. **Test**: Write unit tests
5. **Document**: Update relevant documentation

### Code Structure
```
src/main/java/com/poorcraft/
├── core/           # Engine foundation
├── world/          # World generation
├── player/         # Player systems
├── npc/            # AI NPC framework
├── ui/             # User interface
├── network/        # Multiplayer
├── resources/      # Resource management
└── utils/          # Utilities
```

## AI Integration Setup

### 1. Configure AI Services
Create `config/ai.properties`:
```properties
# AI Service Configuration
ai.service=gemini
ai.gemini.api.key=your-gemini-api-key
ai.ollama.url=http://localhost:11434
ai.openrouter.api.key=your-openrouter-key
```

### 2. Test AI Integration
```java
// Example usage
AIService aiService = new AIService();
aiService.configure(AIService.Provider.GEMINI, "your-api-key");
String response = aiService.generateResponse("Hello NPC!");
```

### 3. Custom AI Personalities
Create new personalities in `AINPC.Personality` enum and define behavior patterns.

## Texture Creation Guide

### 64x64 Texture Requirements
- **Format**: PNG with transparency
- **Size**: Exactly 64x64 pixels
- **Color**: RGBA (32-bit)
- **Naming**: lowercase_with_underscores.png

### Creating Textures
1. **Tools**: Aseprite, GIMP, Photoshop
2. **Palette**: Use provided color palette
3. **Style**: Consistent with Minecraft aesthetic
4. **Testing**: Test in-game immediately

### Texture Categories
- **Blocks**: Stone, wood, ores, etc.
- **Items**: Tools, resources, food
- **Entities**: Mobs, players, NPCs
- **UI**: Buttons, panels, icons

## Debugging Tips

### Common Issues
1. **"OpenGL version not supported"**
   - Update graphics drivers
   - Check GPU compatibility (OpenGL 4.0+)

2. **"JAVA_HOME not set"**
   - Set environment variable (see SETUP.md)
   - Restart terminal/IDE

3. **Build failures**
   - Run `gradlew clean build --refresh-dependencies`
   - Check internet connection

### Logging
Enable debug logging in `src/main/resources/logback.xml`:
```xml
<logger name="com.poorcraft" level="DEBUG"/>
```

### Performance Profiling
- Use VisualVM for Java profiling
- Enable OpenGL debug mode in development
- Monitor memory usage with built-in tools

## Testing

### Unit Tests
```bash
gradlew test
```

### Integration Tests
```bash
gradlew integrationTest
```

### Manual Testing
1. **Single-player**: Basic functionality
2. **Multiplayer**: Network features
3. **AI NPCs**: Conversation testing
4. **Performance**: Stress testing

## Contributing

### Code Standards
- **Style**: Google Java Style Guide
- **Comments**: Javadoc for public APIs
- **Tests**: Unit tests for new features
- **Documentation**: Update relevant docs

### Pull Request Process
1. Fork the repository
2. Create feature branch
3. Implement changes
4. Write tests
5. Update documentation
6. Submit PR with description

## Future Enhancements

### Planned Features
- [ ] Advanced AI personalities
- [ ] Quest system
- [ ] Multiplayer servers
- [ ] Modding API
- [ ] Mobile support
- [ ] VR integration

### Extension Points
- **Custom blocks**: Implement Block interface
- **New resources**: Add to ResourceType enum
- **AI services**: Extend AIService.Provider
- **UI themes**: Create new UI styles

## Getting Help

### Resources
- **Documentation**: Check `docs/` directory
- **Issues**: GitHub Issues for bugs
- **Discord**: [discord.gg/poorcraft64]
- **Wiki**: Community-driven documentation

### Support
- **Installation**: See SETUP.md
- **Development**: This guide
- **API**: JavaDoc in code
- **Examples**: `examples/` directory
