package com.poorcraft.core;

import com.poorcraft.utils.Logger;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * OpenGL renderer for 3D graphics.
 * Handles shader management, mesh rendering, and camera setup.
 * 
 * @author Poorcraft Team
 */
public class Renderer {
    
    private final Window window;
    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;
    private final Matrix4f modelMatrix;
    
    private int shaderProgram;
    private int vertexArrayObject;
    private int vertexBufferObject;
    private int elementBufferObject;
    
    /**
     * Create a new renderer
     * @param window The window to render to
     */
    public Renderer(Window window) {
        this.window = window;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.modelMatrix = new Matrix4f();
    }
    
    /**
     * Initialize OpenGL resources
     */
    public void initialize() {
        Logger.info("Initializing renderer...");
        
        // Enable depth testing
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        
        // Enable backface culling
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glFrontFace(GL_CCW);
        
        // Set viewport
        glViewport(0, 0, window.getWidth(), window.getHeight());
        
        // Create shader program
        createShaderProgram();
        
        // Setup projection matrix
        float aspectRatio = (float) window.getWidth() / window.getHeight();
        projectionMatrix.identity()
            .perspective((float) Math.toRadians(45.0f), aspectRatio, 0.1f, 1000.0f);
        
        Logger.info("Renderer initialized successfully");
    }
    
    /**
     * Create and compile shader program
     */
    private void createShaderProgram() {
        // Vertex shader source
        String vertexShaderSource = """
            #version 330 core
            layout (location = 0) in vec3 aPos;
            layout (location = 1) in vec2 aTexCoord;
            layout (location = 2) in vec3 aNormal;
            
            uniform mat4 model;
            uniform mat4 view;
            uniform mat4 projection;
            
            out vec2 TexCoord;
            out vec3 Normal;
            out vec3 FragPos;
            
            void main() {
                gl_Position = projection * view * model * vec4(aPos, 1.0);
                TexCoord = aTexCoord;
                Normal = mat3(transpose(inverse(model))) * aNormal;
                FragPos = vec3(model * vec4(aPos, 1.0));
            }
            """;
        
        // Fragment shader source
        String fragmentShaderSource = """
            #version 330 core
            in vec2 TexCoord;
            in vec3 Normal;
            in vec3 FragPos;
            
            uniform sampler2D texture1;
            uniform vec3 lightPos;
            uniform vec3 lightColor;
            uniform vec3 viewPos;
            
            out vec4 FragColor;
            
            void main() {
                // Ambient lighting
                float ambientStrength = 0.1;
                vec3 ambient = ambientStrength * lightColor;
                
                // Diffuse lighting
                vec3 norm = normalize(Normal);
                vec3 lightDir = normalize(lightPos - FragPos);
                float diff = max(dot(norm, lightDir), 0.0);
                vec3 diffuse = diff * lightColor;
                
                // Specular lighting
                float specularStrength = 0.5;
                vec3 viewDir = normalize(viewPos - FragPos);
                vec3 reflectDir = reflect(-lightDir, norm);
                float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
                vec3 specular = specularStrength * spec * lightColor;
                
                vec3 result = (ambient + diffuse + specular) * texture(texture1, TexCoord).rgb;
                FragColor = vec4(result, 1.0);
            }
            """;
        
        // Compile shaders
        int vertexShader = compileShader(GL_VERTEX_SHADER, vertexShaderSource);
        int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentShaderSource);
        
        // Create shader program
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        
        // Check for linking errors
        if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == 0) {
            String error = glGetProgramInfoLog(shaderProgram, 1024);
            Logger.error("Shader program linking failed: " + error);
            throw new RuntimeException("Failed to link shader program");
        }
        
        // Clean up shaders
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }
    
    /**
     * Compile a shader
     * @param type Shader type (GL_VERTEX_SHADER or GL_FRAGMENT_SHADER)
     * @param source Shader source code
     * @return Compiled shader ID
     */
    private int compileShader(int type, String source) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);
        
        // Check for compilation errors
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            String error = glGetShaderInfoLog(shader, 1024);
            Logger.error("Shader compilation failed: " + error);
            throw new RuntimeException("Failed to compile shader");
        }
        
        return shader;
    }
    
    /**
     * Begin a new frame
     */
    public void beginFrame() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        // Update projection matrix if window resized
        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            float aspectRatio = (float) window.getWidth() / window.getHeight();
            projectionMatrix.identity()
                .perspective((float) Math.toRadians(45.0f), aspectRatio, 0.1f, 1000.0f);
            window.clearResized();
        }
        
        // Use shader program
        glUseProgram(shaderProgram);
    }
    
    /**
     * End the current frame
     */
    public void endFrame() {
        glUseProgram(0);
    }
    
    /**
     * Set the view matrix (camera transform)
     * @param viewMatrix View matrix
     */
    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix.set(viewMatrix);
        int viewLoc = glGetUniformLocation(shaderProgram, "view");
        glUniformMatrix4fv(viewLoc, false, viewMatrix.get(new float[16]));
    }
    
    /**
     * Set the model matrix (object transform)
     * @param modelMatrix Model matrix
     */
    public void setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix.set(modelMatrix);
        int modelLoc = glGetUniformLocation(shaderProgram, "model");
        glUniformMatrix4fv(modelLoc, false, modelMatrix.get(new float[16]));
    }
    
    /**
     * Shutdown the renderer and cleanup resources
     */
    public void shutdown() {
        Logger.info("Shutting down renderer...");
        
        if (shaderProgram != 0) {
            glDeleteProgram(shaderProgram);
        }
        
        Logger.info("Renderer shutdown complete");
    }
    
    // Getters
    public Matrix4f getProjectionMatrix() { return projectionMatrix; }
    public Matrix4f getViewMatrix() { return viewMatrix; }
    public Matrix4f getModelMatrix() { return modelMatrix; }
}
