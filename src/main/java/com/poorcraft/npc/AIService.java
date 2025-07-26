package com.poorcraft.npc;

import com.poorcraft.utils.Logger;
import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * AI service integration for NPCs.
 * Supports multiple AI providers: Gemini, Ollama, OpenRouter.
 * 
 * @author Poorcraft Team
 */
public class AIService {
    
    public enum Provider {
        GEMINI("Gemini", "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent"),
        OLLAMA("Ollama", "http://localhost:11434/api/generate"),
        OPENROUTER("OpenRouter", "https://openrouter.ai/api/v1/chat/completions");
        
        private final String name;
        private final String endpoint;
        
        Provider(String name, String endpoint) {
            this.name = name;
            this.endpoint = endpoint;
        }
        
        public String getName() { return name; }
        public String getEndpoint() { return endpoint; }
    }
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private String apiKey;
    private Provider currentProvider;
    
    public AIService() {
        this.httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
        this.objectMapper = new ObjectMapper();
        this.currentProvider = Provider.GEMINI;
    }
    
    /**
     * Set the AI provider and API key
     * @param provider AI provider
     * @param apiKey API key for the service
     */
    public void configure(Provider provider, String apiKey) {
        this.currentProvider = provider;
        this.apiKey = apiKey;
        Logger.info("AI Service configured: " + provider.getName());
    }
    
    /**
     * Generate response using configured AI service
     * @param prompt The prompt to send
     * @return Generated response
     */
    public String generateResponse(String prompt) {
        if (apiKey == null || apiKey.isEmpty()) {
            Logger.warn("AI service not configured, using fallback");
            return generateFallbackResponse(prompt);
        }
        
        try {
            String requestBody = buildRequestBody(prompt);
            Request request = new Request.Builder()
                .url(currentProvider.getEndpoint())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(RequestBody.create(requestBody, MediaType.parse("application/json")))
                .build();
            
            try (Response response = httpClient.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    return parseResponse(response.body().string());
                } else {
                    Logger.error("AI service error: " + response.code() + " " + response.message());
                    return generateFallbackResponse(prompt);
                }
            }
        } catch (IOException e) {
            Logger.error("AI service communication error", e);
            return generateFallbackResponse(prompt);
        }
    }
    
    /**
     * Build request body based on provider
     * @param prompt The prompt
     * @return JSON request body
     */
    private String buildRequestBody(String prompt) {
        try {
            switch (currentProvider) {
                case GEMINI:
                    return objectMapper.writeValueAsString(new GeminiRequest(prompt));
                case OLLAMA:
                    return objectMapper.writeValueAsString(new OllamaRequest(prompt));
                case OPENROUTER:
                    return objectMapper.writeValueAsString(new OpenRouterRequest(prompt));
                default:
                    return "";
            }
        } catch (Exception e) {
            Logger.error("Failed to build request body", e);
            return "";
        }
    }
    
    /**
     * Parse response based on provider
     * @param responseBody Response JSON
     * @return Extracted text
     */
    private String parseResponse(String responseBody) {
        try {
            switch (currentProvider) {
                case GEMINI:
                    GeminiResponse geminiResponse = objectMapper.readValue(responseBody, GeminiResponse.class);
                    return geminiResponse.getText();
                case OLLAMA:
                    OllamaResponse ollamaResponse = objectMapper.readValue(responseBody, OllamaResponse.class);
                    return ollamaResponse.getResponse();
                case OPENROUTER:
                    OpenRouterResponse openRouterResponse = objectMapper.readValue(responseBody, OpenRouterResponse.class);
                    return openRouterResponse.getText();
                default:
                    return "";
            }
        } catch (Exception e) {
            Logger.error("Failed to parse response", e);
            return generateFallbackResponse("");
        }
    }
    
    /**
     * Generate fallback response when AI service is unavailable
     * @param prompt Original prompt
     * @return Fallback response
     */
    private String generateFallbackResponse(String prompt) {
        return "I'm having trouble connecting to my AI services right now, but I'm still here to help!";
    }
    
    // Request/Response classes for different providers
    private static class GeminiRequest {
        public final GeminiContent[] contents;
        
        public GeminiRequest(String prompt) {
            this.contents = new GeminiContent[]{new GeminiContent(prompt)};
        }
    }
    
    private static class GeminiContent {
        public final GeminiPart[] parts;
        
        public GeminiContent(String text) {
            this.parts = new GeminiPart[]{new GeminiPart(text)};
        }
    }
    
    private static class GeminiPart {
        public final String text;
        
        public GeminiPart(String text) {
            this.text = text;
        }
    }
    
    private static class GeminiResponse {
        public GeminiCandidate[] candidates;
        
        public String getText() {
            if (candidates != null && candidates.length > 0 && candidates[0].content != null) {
                return candidates[0].content.parts[0].text;
            }
            return "";
        }
    }
    
    private static class GeminiCandidate {
        public GeminiContent content;
    }
    
    private static class OllamaRequest {
        public final String model = "llama2";
        public final String prompt;
        public final boolean stream = false;
        
        public OllamaRequest(String prompt) {
            this.prompt = prompt;
        }
    }
    
    private static class OllamaResponse {
        public String response;
        
        public String getResponse() {
            return response != null ? response : "";
        }
    }
    
    private static class OpenRouterRequest {
        public final String model = "openai/gpt-3.5-turbo";
        public final OpenRouterMessage[] messages;
        
        public OpenRouterRequest(String prompt) {
            this.messages = new OpenRouterMessage[]{new OpenRouterMessage("user", prompt)};
        }
    }
    
    private static class OpenRouterMessage {
        public final String role;
        public final String content;
        
        public OpenRouterMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
    
    private static class OpenRouterResponse {
        public OpenRouterChoice[] choices;
        
        public String getText() {
            if (choices != null && choices.length > 0 && choices[0].message != null) {
                return choices[0].message.content;
            }
            return "";
        }
    }
    
    private static class OpenRouterChoice {
        public OpenRouterMessage message;
    }
}
