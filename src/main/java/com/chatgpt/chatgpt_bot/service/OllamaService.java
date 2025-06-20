package com.chatgpt.chatgpt_bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Map;

@Service
public class OllamaService {

    private final RestTemplate restTemplate ;
    private final String OLLAMA_API_URL = "http://localhost:11434/api/generate";

    public OllamaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateResponse(String prompt, String model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "prompt", prompt,
                "stream", false // Important! to get a single JSON object, not NDJSON
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                OLLAMA_API_URL,
                HttpMethod.POST,
                request,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            String body = response.getBody();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String, Object> jsonMap = mapper.readValue(body, Map.class);
                return jsonMap.getOrDefault("response", "No response").toString();
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse Ollama response: " + e.getMessage(), e);
            }
            // return body.get("response").toString();
        } else {
            throw new RuntimeException("Failed to call Ollama API: " + response.getStatusCode());
        }
    }
}
