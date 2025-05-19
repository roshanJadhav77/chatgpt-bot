package com.chatgpt.chatgpt_bot.service;

import com.chatgpt.chatgpt_bot.dto.ChatRequest;
import com.chatgpt.chatgpt_bot.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Value("${openai.api-key}")
    private String apikey;

    @Value("${openai.api-url}")
    private String url;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate ;

    public ChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ChatResponse askQuestion(ChatRequest request){

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apikey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HashMap<String, Object> body = formRequestBody(request);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ChatResponse chatResponse = new ChatResponse();

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        Map responseBody = response.getBody();
        if(responseBody != null && responseBody.containsKey("choices")){
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> firstChoice = choices.get(0);
                Map<String, Object> messageResp = (Map<String, Object>) firstChoice.get("message");
                if(messageResp != null && messageResp.containsKey("content")){
                    String content = (String )messageResp.get("content");
                    chatResponse.setReply(content);
                }
            }
        }
        return chatResponse;
    }

    private HashMap<String, Object> formRequestBody(ChatRequest request) {
        Map<String, Object> message = Map.of("role", "user", "content", request.getPrompt());

        HashMap<String, Object> body = new HashMap<>();
        body.put("model",model);
        body.put("messages", List.of(message));
        return body;
    }
}
