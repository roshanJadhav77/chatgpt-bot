package com.chatgpt.chatgpt_bot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ChatRequest {

    @Schema(description = "User prompt to send to ChatGPT", example = "Tell me a joke.")
    private String prompt;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
