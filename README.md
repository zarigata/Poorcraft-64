# Poorcraft 64 - Java Minecraft RPG Clone

## Overview
A Java-based Minecraft clone with RPG elements, featuring AI-powered NPCs, modern UI, and 64x64 textures. Designed for fun, shareable online multiplayer gameplay with extensive modding capabilities.

## Features
- 🎮 **RPG Mechanics**: Leveling system, skill trees, resource management
- 🤖 **AI NPCs**: Integration with Gemini, Ollama, and OpenRouter APIs
- 🎨 **Modern UI**: Clean, responsive interface with 64x64 textures
- 🌐 **Multiplayer**: Easy online sharing and cooperative gameplay
- 🔧 **Extensible**: Modular design for future upgrades and features
- 📱 **Cross-platform**: Windows, macOS, and Linux support

## Quick Start

### Prerequisites
- Java 17 or higher
- Gradle 8.0+
- Git

### Installation
1. Clone the repository
2. Run `./gradlew build` (Linux/macOS) or `gradlew.bat build` (Windows)
3. Run `./gradlew run` to start the game

### Development Setup
See [SETUP.md](SETUP.md) for detailed installation instructions.

## Project Structure
```
Poorcraft 64/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/poorcraft/
│   │   │       ├── core/           # Core game engine
│   │   │       ├── world/          # World generation & management
│   │   │       ├── player/         # Player systems
│   │   │       ├── npc/            # AI NPC framework
│   │   │       ├── ui/             # User interface
│   │   │       ├── network/        # Multiplayer networking
│   │   │       └── resources/      # Resource management
│   │   └── resources/
│   │       ├── textures/           # 64x64 texture assets
│   │       ├── shaders/            # OpenGL shaders
│   │       └── config/             # Configuration files
├── docs/                           # Documentation
├── tools/                          # Build tools and scripts
└── scripts/                        # Development scripts
```

## Documentation
- [Architecture Overview](docs/architecture.md)
- [API Documentation](docs/api.md)
- [Modding Guide](docs/modding.md)
- [AI Integration](docs/ai-integration.md)

## Contributing
See [CONTRIBUTING.md](CONTRIBUTING.md) for development guidelines.

## License
MIT License - see [LICENSE](LICENSE) for details.
