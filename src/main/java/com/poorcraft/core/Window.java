package com.poorcraft.core;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Window management class using GLFW for cross-platform window creation.
 * Handles window creation, input events, and OpenGL context management.
 * 
 * @author Poorcraft Team
 */
public class Window {
    
    private final long windowHandle;
    private int width;
    private int height;
    private String title;
    private boolean resized;
    private boolean fullscreen;
    
    /**
     * Create a new window
     * @param width Initial window width
     * @param height Initial window height
     * @param title Window title
     */
    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.resized = false;
        this.fullscreen = false;
        
        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        
        // Create the window
        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        
        // Setup resize callback
        glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.resized = true;
        });
        
        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            
            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowHandle, pWidth, pHeight);
            
            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            
            // Center the window
            glfwSetWindowPos(
                windowHandle,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            );
        }
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(windowHandle);
        
        // Initialize OpenGL
        GL.createCapabilities();
        
        // Set clear color
        glClearColor(0.5f, 0.7f, 1.0f, 1.0f);
        
        Logger.info("Window created successfully: " + width + "x" + height);
    }
    
    /**
     * Check if the window should close
     * @return true if window should close
     */
    public boolean shouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }
    
    /**
     * Poll for window events
     */
    public void pollEvents() {
        glfwPollEvents();
    }
    
    /**
     * Swap the front and back buffers
     */
    public void swapBuffers() {
        glfwSwapBuffers(windowHandle);
    }
    
    /**
     * Destroy the window and cleanup resources
     */
    public void destroy() {
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    
    /**
     * Set the window title
     * @param title New window title
     */
    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(windowHandle, title);
    }
    
    /**
     * Toggle fullscreen mode
     */
    public void toggleFullscreen() {
        fullscreen = !fullscreen;
        long monitor = fullscreen ? glfwGetPrimaryMonitor() : NULL;
        GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        if (fullscreen) {
            glfwSetWindowMonitor(windowHandle, monitor, 0, 0, mode.width(), mode.height(), mode.refreshRate());
        } else {
            glfwSetWindowMonitor(windowHandle, NULL, 100, 100, 1280, 720, GLFW_DONT_CARE);
        }
    }
    
    /**
     * Check if a key is pressed
     * @param keyCode GLFW key code
     * @return true if key is pressed
     */
    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }
    
    // Getters
    public long getHandle() { return windowHandle; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public String getTitle() { return title; }
    public boolean isResized() { return resized; }
    public boolean isFullscreen() { return fullscreen; }
    
    /**
     * Clear the resize flag
     */
    public void clearResized() {
        resized = false;
    }
}
