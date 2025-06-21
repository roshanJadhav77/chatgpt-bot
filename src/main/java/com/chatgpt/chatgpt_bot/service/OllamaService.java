package com.chatgpt.chatgpt_bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.io.Serializable;
import java.util.Map;

@Service
public class OllamaService {

    private final RestTemplate restTemplate ;
    private final WebClient webClient;
    private final ObjectMapper mapper;
    private final String OLLAMA_API_URL = "http://localhost:11434/api/generate";

    public OllamaService(RestTemplate restTemplate, WebClient webClient, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
        this.mapper = objectMapper;
    }

    public String generateResponse(String prompt, String model) {
        //return restCall(prompt, model);
        return webClientCall(prompt,model);
    }

    // Synchronous block call (for simplicity)
    private String webClientCall(String prompt, String model){

        Map<String, Object> requestBody = getRequestBody(prompt, model);

        String response = webClient.post()
                .uri(OLLAMA_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> jsonMap = mapper.readValue(response, Map.class);
            return  jsonMap.getOrDefault("response", "No Response").toString();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to call Ollama API: " + e.getMessage(), e);
        }
    }

    private static Map<String, Object> getRequestBody(String prompt, String model) {
        Map<String, Object> requestBody = Map.of(
                "model", model,
                "prompt", prompt,
                "stream", false
        );
        return requestBody;
    }

    private String restCall(String prompt, String model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = getRequestBody(prompt, model);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                OLLAMA_API_URL,
                HttpMethod.POST,
                request,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            String body = response.getBody();

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
