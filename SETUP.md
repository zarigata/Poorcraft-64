# Poorcraft 64 - Development Setup Guide

## System Requirements

### Windows
- **OS**: Windows 10 or 11 (64-bit)
- **RAM**: 8GB minimum, 16GB recommended
- **Storage**: 5GB free space for development
- **Graphics**: OpenGL 4.0+ compatible GPU

### Java Development Kit (JDK)
1. **Download JDK 17 or higher**
   - Visit: https://adoptium.net/
   - Select: OpenJDK 17 (LTS) or OpenJDK 21 (LTS)
   - Download the Windows x64 installer

2. **Install JDK**
   - Run the downloaded installer
   - Follow installation wizard
   - Default installation path: `C:\Program Files\Eclipse Adoptium\jdk-17.x.x`

3. **Verify Installation**
   ```cmd
   java -version
   javac -version
   ```
   You should see Java 17+ version information.

4. **Set Environment Variables**
   - Open System Properties → Advanced → Environment Variables
   - Add to PATH: `C:\Program Files\Eclipse Adoptium\jdk-17.x.x\bin`
   - Create JAVA_HOME: `C:\Program Files\Eclipse Adoptium\jdk-17.x.x`

### Build Tool - Gradle
1. **Download Gradle**
   - Visit: https://gradle.org/releases/
   - Download: gradle-8.x-bin.zip

2. **Install Gradle**
   - Extract to: `C:\Gradle\gradle-8.x`
   - Add to PATH: `C:\Gradle\gradle-8.x\bin`

3. **Verify Installation**
   ```cmd
   gradle -version
   ```

### IDE - IntelliJ IDEA (Recommended)
1. **Download**
   - Visit: https://www.jetbrains.com/idea/download/
   - Download: Community Edition (free)

2. **Install**
   - Run installer with default settings
   - During setup, select:
     - Create Desktop shortcut
     - Add "Open Folder as Project"
     - Associate .java files

### Git (Version Control)
1. **Download**
   - Visit: https://git-scm.com/download/win
   - Download the latest version

2. **Install**
   - Use recommended settings
   - Choose: "Git from the command line and also from 3rd-party software"

### Graphics Libraries
The project uses LWJGL (Lightweight Java Game Library) for OpenGL rendering. This will be automatically downloaded by Gradle.

## Project Setup

### 1. Clone Repository
```cmd
git clone [repository-url]
cd Poorcraft64
```

### 2. Build Project
```cmd
gradlew build
```

### 3. Run Development Version
```cmd
gradlew run
```

### 4. Generate Executable
```cmd
gradlew jpackage
```

## Development Workflow

### Daily Development
1. Open project in IntelliJ IDEA
2. Import as Gradle project
3. Run configurations are pre-configured:
   - `Run Game` - Development build
   - `Build Release` - Production build
   - `Run Tests` - Unit tests

### Debugging
- Use IntelliJ's built-in debugger
- Set breakpoints in code
- Use hot reload for quick iteration

### Testing
```cmd
gradlew test
```

## Troubleshooting

### Common Issues

**"JAVA_HOME not set"**
- Ensure JAVA_HOME environment variable points to JDK installation
- Restart terminal/IDE after setting

**"Gradle build failed"**
- Run `gradlew clean build --refresh-dependencies`
- Check internet connection for dependency downloads

**"OpenGL errors"**
- Update graphics drivers
- Ensure GPU supports OpenGL 4.0+
- Check if integrated graphics are being used instead of dedicated GPU

**"Out of memory"**
- Increase JVM heap size in `gradle.properties`:
  ```
  org.gradle.jvmargs=-Xmx4g
  ```

### Getting Help
- Check [docs/troubleshooting.md](docs/troubleshooting.md)
- Join our Discord: [discord.gg/poorcraft64]
- Create GitHub issue for bugs

## Next Steps
After setup, see [CONTRIBUTING.md](CONTRIBUTING.md) for development guidelines.
