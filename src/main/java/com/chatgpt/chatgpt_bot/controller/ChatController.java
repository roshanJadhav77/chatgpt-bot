package com.chatgpt.chatgpt_bot.controller;

import com.chatgpt.chatgpt_bot.dto.ChatRequest;
import com.chatgpt.chatgpt_bot.dto.ChatResponse;
import com.chatgpt.chatgpt_bot.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat API", description = "Ask questions to ChatGPT")
public class ChatController {

    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Ask ChatGPT", description = "Send a prompt to ChatGPT and get a response")
    public ChatResponse chat(@RequestBody ChatRequest request){

        return service.askQuestion(request);

    }
}
