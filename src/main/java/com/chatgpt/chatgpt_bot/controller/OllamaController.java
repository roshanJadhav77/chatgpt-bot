package com.chatgpt.chatgpt_bot.controller;

import com.chatgpt.chatgpt_bot.dto.PromptRequest;
import com.chatgpt.chatgpt_bot.service.OllamaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ollamai")
public class OllamaController {

    private final OllamaService ollamaService;

    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }


    @PostMapping("/chat")
    public String chat(@RequestBody PromptRequest request) {
        return ollamaService.generateResponse(request.getPrompt(), request.getModel());
    }

}
